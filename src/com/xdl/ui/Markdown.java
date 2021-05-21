package com.xdl.ui;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import com.intellij.ide.fileTemplates.impl.UrlUtil;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.Notifications;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.vfs.VirtualFile;
import com.xdl.model.DataCenter;
import lombok.Data;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
@Data
public class Markdown {

    private JTextField titleMDName;
    private JTable table1;
    private JButton createButton;
    private JButton clearButton;
    private JButton clearRowButton;
    private JPanel markdown;

    private final Project project;

    void init(){
        table1.setModel(DataCenter.tableModel);
        table1.setEnabled(true);
    }

    public Markdown(Project project) {
        this.project = project;
        //创建文档
        createButton.addActionListener(e -> createMD());
        //清空文档
        clearButton.addActionListener(e -> DataCenter.clear());
        //刪除一行
        clearRowButton.addActionListener(e -> {
            int selectedRow = table1.getSelectedRow();
            DataCenter.tableModel.removeRow(selectedRow);
            DataCenter.LIST.remove(selectedRow);
        });
        init();
    }


    /**
     * 創建MD文檔
     */
    public void createMD() {
        String text = titleMDName.getText();
        String fileName = text + ".md";
        NotificationGroup notificationGroup = new NotificationGroup("MarkBootNotification", NotificationDisplayType.BALLOON, false);
        if (ObjectUtil.isEmpty(text)) {
            Notification notification = notificationGroup.createNotification("请输入文档标题!", MessageType.WARNING);
            Notifications.Bus.notify(notification, this.project);
            return;
        }
        VirtualFile virtualFile = FileChooser.chooseFile(FileChooserDescriptorFactory.createSingleFolderDescriptor(), project, project.getProjectFile());
        if (!ObjectUtil.isEmpty(virtualFile)) {
            assert virtualFile != null;
            String path = virtualFile.getPath() + "/" + fileName;
            TemplateEngine engine = TemplateUtil.createEngine();
            String template1 = getTemplate();
            Template template = engine.getTemplate(template1);
            Dict dict = Dict.create();
            dict.set("parentTitle", text);
            dict.set("rowList", DataCenter.LIST);
            String result = template.render(dict);
            File file = FileUtil.writeUtf8String(result, path);
            if (ObjectUtil.isEmpty(file)) {
                Notification notification = notificationGroup.createNotification("生成文档失败", MessageType.ERROR);
                Notifications.Bus.notify(notification, this.project);
            }
            Notification notification = notificationGroup.createNotification("文档生成成功", MessageType.INFO);
            Notifications.Bus.notify(notification, this.project);
        }
    }

    private static String getTemplate() {
        String loadText;
        try {
            loadText = UrlUtil.loadText(XHttpUi.class.getResource("/templates/markBoot.ftl"));
        } catch (IOException e) {
            return null;
        }
        return loadText;
    }
}

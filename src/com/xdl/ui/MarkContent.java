package com.xdl.ui;

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
import com.intellij.openapi.wm.ToolWindow;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class MarkContent extends Component {
    public JTable table1;
    public JButton createButton;
    public JButton clearButton;
    public JButton closeButton;
    public JTextField titleName;
    public JPanel parentPanel;
    public JPanel titlePanel;
    public JPanel buttomPanel;
    public JLabel title;

    private void init(){
        getTemplate();
        table1.setEnabled(true);
    }



    public MarkContent(Project project, ToolWindow toolWindow) {
        init();
        NotificationGroup notificationGroup = new NotificationGroup("MarkBook", NotificationDisplayType.BALLOON,false);
        /*createButton.addActionListener(e -> {
            String text = titleName.getText();
            String fileName=text+".md";
            if(!ObjectUtil.isEmpty(text)){
                VirtualFile virtualFile = FileChooser.chooseFile(FileChooserDescriptorFactory.createSingleFolderDescriptor(), project, project.getBaseDir());
                if(!ObjectUtil.isEmpty(virtualFile)){
                    String path=virtualFile.getPath()+"/"+fileName;
                    TemplateEngine engine = TemplateUtil.createEngine();
                    String template1 =   getTemplate();
                    Template template = engine.getTemplate(template1);
                    Dict dict = Dict.create();
                    dict.set("parentTitle",text );
                    dict.set("rowList",DataCenter.LIST );
                    String result = template.render(dict);
                    FileUtil.writeUtf8String(result, path);
                    Notification notification = notificationGroup.createNotification("文档生成成功", MessageType.INFO);
                    Notifications.Bus.notify(notification);
                }
            }else{
                Notification notification = notificationGroup.createNotification("请输入文档标题", MessageType.WARNING);
                Notifications.Bus.notify(notification);
            }
        });
        clearButton.addActionListener(e -> DataCenter.clear());
        closeButton.addActionListener(e -> toolWindow.hide(null));*/
    }


   private static  String getTemplate(){
       String loadText=null;
       try {
          loadText = UrlUtil.loadText(MarkContent.class.getResource("/templates/markBoot.ftl"));
       } catch (IOException e) {
           return null;
       }
       return loadText;
   }




}

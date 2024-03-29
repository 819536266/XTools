package com.xdl.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.EditorTextField;
import com.xdl.action.XToolsAction;
import com.xdl.model.DataCenter;
import com.xdl.model.Row;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.List;


/**
 * @author Bx_Hu
 */
public class OutContent extends DialogWrapper {


    private EditorTextField title;

    private EditorTextField content;

    private final Project project;

    public OutContent(Project project) {
        super(true);
        setTitle("Create");
        init();
        this.project = project;
    }


    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel jPanel = new JPanel();
        title = new EditorTextField("  Title  ");
        JLabel titleLabel = new JLabel("  Title  ");
        JLabel contentLabel = new JLabel("Content");
        title = new EditorTextField("  Title  ");
        content = new EditorTextField("Content");
        title.setPreferredSize(new Dimension(200, 70));
        content.setPreferredSize(new Dimension(200, 100));
        title.setOneLineMode(false);
        content.setOneLineMode(false);
        JPanel jPanel1 = new JPanel();
        jPanel1.add(titleLabel);
        jPanel1.add(title);
        JPanel jPanel2 = new JPanel();
        jPanel2.add(contentLabel);
        jPanel2.add(content);
        GridLayout gridLayout = new GridLayout();
        gridLayout.setRows(2);
        jPanel.add(jPanel1);
        jPanel.add(jPanel2);
        jPanel.setLayout(gridLayout);
        return jPanel;
    }

    @Override
    protected JComponent createSouthPanel() {
        JPanel jPanel = new JPanel();
        JButton jButton = new JButton("Add");
        jButton.addActionListener(e -> {
            String title1 = title.getText();
            String comment = content.getText();
            String fileName = DataCenter.FILE_NAME;
            String substring = fileName.substring(fileName.lastIndexOf(".") + 1);
            Row java = new Row(title1, comment, fileName, substring, DataCenter.TEXT);
            List<Row> list = DataCenter.LIST;
            list.add(java);
            DataCenter.tableModel.addRow(DataCenter.convert(java));
            XTools ui = XToolsAction.getUi(project, XTools.class);
            ui.openParent(1);
            this.close(1);
        });
        jPanel.add(jButton);
        return jPanel;
    }


}

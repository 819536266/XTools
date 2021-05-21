package com.xdl.ui;

import com.intellij.openapi.project.Project;
import com.intellij.ui.content.Content;
import com.xdl.action.XToolsAction;
import lombok.Data;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

@Data
public class XTools {

    private JTabbedPane toolTabbedPane;
    private ClosePort closePort;
    private Markdown markdown;
    private Convert convert;
    private final Project project;


    public XTools(Project project) {
        this.project = project;
    }


    private void createUIComponents() {
        this.markdown = new Markdown(project);
    }

    public void openParent(int i) {
        toolTabbedPane.setSelectedIndex(i);
        XHttpWindowFactory.toolWindow.show(null);
        Content content = XHttpWindowFactory.toolWindow.getContentManager().getContent(1);
        XHttpWindowFactory.toolWindow.getContentManager().setSelectedContent(content);
    }


}

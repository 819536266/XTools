package com.xdl.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.content.Content;
import com.xdl.action.XToolsAction;
import lombok.Data;

import javax.swing.*;

@Data
public class XTools {

    private JTabbedPane toolTabbedPane;
    private ClosePort closePort;
    private Markdown markdown;
    private Convert convert;
    private final Project project;
    private ToolWindow toolWindow;


    public XTools(Project project) {
        this.project = project;
        toolWindow = XToolsAction.ToolWindows.get(project);
    }


    private void createUIComponents() {
        this.markdown = new Markdown(project);
    }

    public void openParent(int i) {
        toolTabbedPane.setSelectedIndex(i);
        toolWindow.show(null);
        Content content = toolWindow.getContentManager().getContent(1);
        if (content != null) {
            toolWindow.getContentManager().setSelectedContent(content);
        }
    }


}

package com.xdl.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

/*
 *@program:Mark
 *
 *@description:
 *
 *@author:胡博欣
 *
 *@create:2020-05-00-28
 */
public class OutFactory implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        MarkContent markContent = new MarkContent(project, toolWindow);
        ContentFactory instance = ContentFactory.SERVICE.getInstance();
        Content content = instance.createContent(markContent.parentPanel, "", false);
        toolWindow.getContentManager().addContent(content);

    }
}

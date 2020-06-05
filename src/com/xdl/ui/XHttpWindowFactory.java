package com.xdl.ui;

import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.xdl.components.XHttpUiService;
import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
/**
 * @author huboxin
 * @title: XHttpWindowFactory
 * @projectName XHttp
 * @description:
 * @date 2020/5/2614:29
 */
public class XHttpWindowFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        XHttpUiService.getInstance(project).setupImpl(toolWindow);
    }
}

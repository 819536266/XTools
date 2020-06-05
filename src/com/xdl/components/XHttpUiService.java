package com.xdl.components;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import org.jetbrains.annotations.NotNull;

public interface XHttpUiService {
    static XHttpUiService getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, XHttpUiService.class);
    }

    /**
     * setupImpl
     *
     * @param toolWindow toolWindow
     */
    void setupImpl(@NotNull ToolWindow toolWindow);
}

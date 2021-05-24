package com.xdl.ui;

import com.intellij.ui.content.ContentManager;
import com.xdl.action.XToolsAction;
import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;

import java.util.Map;

/**
 * @author huboxin
 * @title: XHttpWindowFactory
 * @projectName XHttp
 * @description:
 * @date 2020/5/2614:29
 */
public class XHttpWindowFactory implements ToolWindowFactory {

    /**
     * 全局工具窗口
     */
    public  ToolWindow toolWindow;

    private Project project;



    /**
     * 首次打开窗口时调用
     *
     * @param project    项目
     * @param toolWindow 工具窗口
     */
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ContentManager contentManager = toolWindow.getContentManager();
        Map<Class<?>, Object> classMap = XToolsAction.xHttpUiMap.get(project);
        if(classMap == null ){
            XToolsAction.init(project,contentManager);
        }
    }

    @Override
    public boolean isApplicable(@NotNull Project project) {
        this.project=project;
        return true;
    }


    /**
     * IDEA初始化时调用
     *
     * @param toolWindow 工具窗口
     */
    @Override
    public void init(@NotNull ToolWindow toolWindow) {
        this.toolWindow= toolWindow;
        XToolsAction.ToolWindows.put(project,toolWindow);
    }
}

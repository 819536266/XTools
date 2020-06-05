package com.xdl.components.impl;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.xdl.action.XHttpAction;
import com.xdl.components.XHttpUiService;
import com.intellij.openapi.project.Project;
import com.xdl.ui.XHttpUi;
import org.jetbrains.annotations.NotNull;

public class XHttpUiServiceImpl implements XHttpUiService {

    private final Project project;

    public XHttpUiServiceImpl(Project project) {
        this.project = project;
    }

    @Override
    public void setupImpl(@NotNull ToolWindow toolWindow) {
        //创建出NoteListWindow对象
        XHttpUi xHttpUi = new XHttpUi(project,toolWindow);
        //获取内容工厂的实例
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        //获取用于toolWindow显示的内容
        Content content = contentFactory.createContent(xHttpUi.getParentPanel(), "", false);
        toolWindow.getContentManager().addContent(content);
        XHttpAction.xHttpUiMap.put(project,xHttpUi);
    }
}

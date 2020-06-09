package com.xdl.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.xdl.model.DataCenter;
import com.xdl.ui.OutContent;

public class MarkBookAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        Editor requiredData = e.getRequiredData(LangDataKeys.EDITOR);

        SelectionModel selectionModel = requiredData.getSelectionModel();
        String selectedText = selectionModel.getSelectedText();

        String name = e.getRequiredData(LangDataKeys.PSI_FILE).getViewProvider().getVirtualFile().getName();
        DataCenter.TEXT=selectedText;
        DataCenter.FILE_NAME=name;
        OutContent outContent = new OutContent(project);
        outContent.show();
    }
}

package com.xdl.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.psi.PsiCodeBlock;
import com.intellij.psi.PsiElement;
import com.xdl.model.DataCenter;
import com.xdl.ui.OutContent;

import javax.swing.*;

public class XHttpAction extends AnAction {

    public XHttpAction() {
        super();
    }

    public XHttpAction(PsiElement body, String getMapping, String get请求, Icon icon) {


    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        Editor requiredData = e.getRequiredData(DataKeys.EDITOR);
        SelectionModel selectionModel = requiredData.getSelectionModel();
        String selectedText = selectionModel.getSelectedText();

        String name = e.getRequiredData(DataKeys.PSI_FILE).getViewProvider().getVirtualFile().getName();
        DataCenter.TEXT=selectedText;
        DataCenter.FILE_NAME=name;
        OutContent outContent = new OutContent();
        outContent.show();
        // TODO: insert action logic here
    }
}

package com.xdl.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.psi.PsiElement;
import com.xdl.ui.OutContent;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author 15290
 */
public class XHttpAction extends AnAction {

    private PsiElement element;

    @Override
    public void actionPerformed(AnActionEvent e) {
        Editor requiredData = e.getRequiredData(DataKeys.EDITOR);
        SelectionModel selectionModel = requiredData.getSelectionModel();
        String selectedText = selectionModel.getSelectedText();
        String name = e.getRequiredData(DataKeys.PSI_FILE)
                .getViewProvider()
                .getVirtualFile()
                .getName();
        OutContent outContent = new OutContent();
        outContent.show();
        // TODO: insert action logic here
    }


    public XHttpAction() {
        super();
    }

    public XHttpAction(@Nullable PsiElement element, @Nls(capitalization = Nls.Capitalization.Title) @Nullable String text,
                       @Nls(capitalization = Nls.Capitalization.Sentence) @Nullable String description,
                       @Nullable Icon icon) {
        super(text, description, icon);
        this.element=element;
    }

}

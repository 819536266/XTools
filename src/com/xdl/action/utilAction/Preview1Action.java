package com.xdl.action.utilAction;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.xdl.ui.PreviewContent;
import org.jetbrains.annotations.NotNull;

/**
 * @author huboxin
 * @title: PreviewAction
 * @projectName XTools
 * @description: 预览文件
 * @date 2021-7-2714:27
 */
public class Preview1Action extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        PsiElement requiredData = anActionEvent.getRequiredData(LangDataKeys.PSI_ELEMENT);
        PsiClass localVarialbeContainingClass = PreviewAction.getPsiClass(requiredData);
        if (localVarialbeContainingClass == null) {
            return;
        }
        PreviewContent.text= localVarialbeContainingClass.getText();
        PreviewContent outContent = new PreviewContent();
        outContent.show();
    }
}

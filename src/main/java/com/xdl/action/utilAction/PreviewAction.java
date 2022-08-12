package com.xdl.action.utilAction;

import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.codeInspection.util.IntentionName;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiTypesUtil;
import com.intellij.util.IncorrectOperationException;
import com.xdl.ui.PreviewContent;
import org.jetbrains.annotations.NotNull;

/**
 * @author huboxin
 * @title: PreviewAction
 * @projectName XTools
 * @description: 预览文件
 * @date 2021-7-2714:27
 */
public class PreviewAction extends PsiElementBaseIntentionAction {

    @Override
    public void invoke(@NotNull Project project, Editor editor,
                       @NotNull PsiElement psiElement) throws IncorrectOperationException {
        PsiClass localVarialbeContainingClass = getLocalVarialbeContainingClass(psiElement);
        if (localVarialbeContainingClass == null) {
            return;
        }
        PreviewContent.text = localVarialbeContainingClass.getText();
        PreviewContent outContent = new PreviewContent();
        outContent.show();
    }

    public static PsiClass getLocalVarialbeContainingClass(@NotNull PsiElement element) {
        PsiElement psiParent = PsiTreeUtil.getContextOfType(element,PsiClass.class, PsiLocalVariable.class, PsiParameter.class);
        if (psiParent == null ) {
            return null;
        }
        PsiClass psiClass = null;
        if (element instanceof PsiClass) {
            psiClass =(PsiClass) element;
        }
        if (psiParent instanceof PsiLocalVariable) {
            PsiLocalVariable psiLocal = (PsiLocalVariable) psiParent;
            psiClass = PsiTypesUtil.getPsiClass(psiLocal.getType());
        }
        if (psiParent instanceof PsiParameter) {
            PsiParameter psiParameter = (PsiParameter) psiParent;
            psiClass = PsiTypesUtil.getPsiClass(psiParameter.getType());
        }
        return psiClass;
    }

    public static PsiClass getPsiClass(@NotNull PsiElement element) {
        PsiClass psiClass = null;
        if (element instanceof PsiClass) {
            psiClass =(PsiClass) element;
        }
        if (element instanceof PsiLocalVariable) {
            PsiLocalVariable psiLocal = (PsiLocalVariable) element;
            psiClass = PsiTypesUtil.getPsiClass(psiLocal.getType());
        }
        if (element instanceof PsiParameter) {
            PsiParameter psiParameter = (PsiParameter) element;
            psiClass = PsiTypesUtil.getPsiClass(psiParameter.getType());
        }
        return psiClass;
    }

    public static PsiMethod getPsiMethod(@NotNull PsiElement element) {
        PsiMethod psiClass = null;
        if (element instanceof PsiMethod) {
            psiClass =(PsiMethod) element;
        }
        return psiClass;
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement psiElement) {
        PsiClass localVarialbeContainingClass = getLocalVarialbeContainingClass(psiElement);
        return localVarialbeContainingClass != null;
    }

    @Override
    public @NotNull String getFamilyName() {
        return "previewFile1";
    }

    @Override
    public
    @NotNull
    String getText() {
        return "previewFile";
    }
}

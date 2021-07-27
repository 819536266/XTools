package com.xdl.action.utilAction;

import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.codeInspection.util.IntentionName;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLocalVariable;
import com.intellij.psi.PsiParameter;
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
        PsiElement context = localVarialbeContainingClass.getContext();
        PreviewContent.text= localVarialbeContainingClass.getText();
        PreviewContent outContent = new PreviewContent();
        outContent.show();
    }

    public static PsiClass getLocalVarialbeContainingClass(@NotNull PsiElement element) {
        PsiElement psiParent = PsiTreeUtil.getContextOfType(element, PsiLocalVariable.class, PsiParameter.class);
        if (psiParent == null) {
            return null;
        }
        PsiClass psiClass = null;
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

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement psiElement) {
        PsiClass localVarialbeContainingClass = getLocalVarialbeContainingClass(psiElement);
        return localVarialbeContainingClass != null;
    }

    @Override
    public @NotNull
    @IntentionFamilyName String getFamilyName() {
        return "previewFile1";
    }

    @Override
    public @IntentionName
    @NotNull
    String getText() {
        return "previewFile";
    }
}

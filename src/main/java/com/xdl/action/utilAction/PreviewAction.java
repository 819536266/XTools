package com.xdl.action.utilAction;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.ui.popup.ActiveIcon;
import com.intellij.openapi.ui.popup.IconButton;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiTypesUtil;
import com.intellij.ui.*;
import com.intellij.ui.components.*;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.components.BorderLayoutPanel;
import com.xdl.util.Icons;
import com.xdl.util.MyLanguageTextField;
import org.jetbrains.annotations.NotNull;

/**
 * @author huboxin
 * @title: PreviewAction
 * @projectName XTools
 * @description: 预览文件
 * @date 2021-7-2714:27
 */
public class PreviewAction extends AnAction {


    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        PsiElement requiredData;
        try {
            requiredData = anActionEvent.getRequiredData(LangDataKeys.PSI_ELEMENT);
        } catch (Exception e) {
            return;
        }
        PsiElement psiClass = PreviewAction.getPsiClass(requiredData);
        PsiElement psiMethod = PreviewAction.getPsiMethod(requiredData);
        Editor editor = anActionEvent.getRequiredData(LangDataKeys.EDITOR);
        PsiElement psiElement = psiClass;
        if (psiClass == null) {
            if (psiMethod == null) {
                return;
            }
            psiElement = psiMethod;
        }
        LanguageTextField languageTextField = new MyLanguageTextField(anActionEvent.getProject(), JavaFileType.INSTANCE.getLanguage(), JavaFileType.INSTANCE);
        languageTextField.setText(psiElement.getText());
        BorderLayoutPanel borderLayoutPanel = JBUI.Panels.simplePanel()
                .withPreferredSize(600, 400)
                .addToCenter(languageTextField);
        JBPopupFactory.getInstance()
                .createComponentPopupBuilder(borderLayoutPanel, new JBViewport())
                .setTitle("预览")
                .setTitleIcon(new ActiveIcon(Icons.X))
                .setMovable(true)
//                .setCancelButton(new IconButton("Close", Icons.CLOSE))
                .setCancelKeyEnabled(true)
                .setProject(anActionEvent.getProject())
                .setBelongsToGlobalPopupStack(true)
                .setCancelOnClickOutside(true)
                .setResizable(true)
                .setCancelOnOtherWindowOpen(true)
                .setNormalWindowLevel(false)
                .createPopup()
                .showInBestPositionFor(editor);
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
}

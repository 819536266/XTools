package com.xdl.action.utilAction;

import com.intellij.codeInsight.hint.HintManager;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.externalSystem.service.execution.TaskCompletionProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ui.componentsList.layout.ComponentOperation;
import com.intellij.openapi.ui.popup.IconButton;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.psi.*;
import com.intellij.spellchecker.ui.SpellCheckingEditorCustomization;
import com.intellij.ui.ActiveComponent;
import com.intellij.ui.EditorCustomization;
import com.intellij.ui.EditorTextField;
import com.intellij.ui.components.*;
import com.intellij.util.textCompletion.TextFieldWithCompletion;
import com.intellij.xdebugger.impl.ui.TextViewer;
import com.xdl.ui.PreviewContent;
import com.xdl.util.Icons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

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

        Document document = EditorFactory.getInstance()
                .createDocument(psiElement.getText());
        EditorTextField editorTextField =
                new EditorTextField(document, anActionEvent.getProject(), JavaFileType.INSTANCE);
        editorTextField.setPreferredSize(new Dimension(400, 600));
        editorTextField.setFont(new Font(null, Font.PLAIN, 12));
        JBPanel jPanel = new JBPanel<>();
        jPanel.setPreferredSize(new Dimension(400, 600));
        jPanel.setMaximumSize(new Dimension(-1, -1));
        jPanel.setMinimumSize(new Dimension(-1, -1));
        jPanel.add(editorTextField);
        JBScrollPane jbScrollPane = new JBScrollPane(jPanel);
        jbScrollPane.setVerticalScrollBarPolicy(JBScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jbScrollPane.setHorizontalScrollBarPolicy(JBScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jbScrollPane.setMaximumSize(new Dimension(400, 600));
        jbScrollPane.setMinimumSize(new Dimension(-1, -1));
        jbScrollPane.revalidate();

        JBPopupFactory instance = JBPopupFactory.getInstance();
        instance.createComponentPopupBuilder(jbScrollPane, new JViewport())
                .setTitle("预览")
                .setMovable(true)
                .setCancelButton(new IconButton("Close", Icons.CLOSE))
                .setMinSize(new Dimension(200, 400))
                .setBelongsToGlobalPopupStack(true)
                .setCancelOnClickOutside(true)
                .setResizable(true)
                .setMayBeParent(true)
                .setNormalWindowLevel(false)
                .setRequestFocus(true)
                .createPopup()
                .showInBestPositionFor(editor);
    }
}

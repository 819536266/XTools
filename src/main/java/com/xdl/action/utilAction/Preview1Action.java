package com.xdl.action.utilAction;

import com.intellij.codeInsight.hint.HintManager;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.externalSystem.service.execution.TaskCompletionProvider;
import com.intellij.openapi.fileEditor.impl.EditorEmptyTextPainter;
import com.intellij.openapi.fileTypes.FileTypes;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ui.componentsList.layout.ComponentOperation;
import com.intellij.openapi.ui.popup.IconButton;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.util.Disposer;
import com.intellij.psi.*;
import com.intellij.spellchecker.ui.SpellCheckingEditorCustomization;
import com.intellij.ui.*;
import com.intellij.ui.components.*;
import com.intellij.util.CommonProcessors;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.textCompletion.TextFieldWithCompletion;
import com.intellij.util.ui.JBHtmlEditorKit;
import com.intellij.xdebugger.impl.ui.TextViewer;
import com.xdl.ui.PreviewContent;
import com.xdl.util.Icons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

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
        editorTextField.addNotify();
        editorTextField.setRequestFocusEnabled(true);
        editorTextField.setDisposedWith(Disposer.newDisposable());
        EditorEx editor1 = editorTextField.getEditor(true);
        editor1.setOneLineMode(true);
        editor1.setHorizontalScrollbarVisible(true);
        editor1.setVerticalScrollbarVisible(true);
        PreviewContent.editorTextField = editorTextField;
        PreviewContent outContent = new PreviewContent();
        outContent.show();
//        JBPopupFactory instance = JBPopupFactory.getInstance();
//        instance.createComponentPopupBuilder(editorTextField, new JBViewport())
//                .setTitle("预览")
//                .setMovable(true)
//                .setCancelButton(new IconButton("Close", Icons.CLOSE))
//                .setCancelKeyEnabled(true)
//                .setBelongsToGlobalPopupStack(true)
//                .setCancelOnClickOutside(false)
//                .setResizable(true)
//                .setNormalWindowLevel(false)
//                .createPopup()
//                .showInBestPositionFor(editor);
    }
}

package com.xdl.action.utilAction;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;

/**
 * 单引号转双引号,双引号转单引号
 */
public class QuotesCastAction extends AnAction {


    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        Editor requiredData = e.getRequiredData(LangDataKeys.EDITOR);
        SelectionModel selectionModel = requiredData.getSelectionModel();
        String selectedText = selectionModel.getSelectedText();
        if (!ObjectUtil.isEmpty(selectedText)) {
            int selectionStart = selectionModel.getSelectionStart();
            int selectionEnd = selectionModel.getSelectionEnd();
            Document document = selectionModel.getEditor().getDocument();
            Runnable runnable = () -> document.replaceString(selectionStart, selectionEnd,quotesCast(selectedText));
            WriteCommandAction.runWriteCommandAction(project, runnable);
            selectionModel.removeSelection();
        }
    }



    private static String quotesCast(String selectedText){
        char dian= '\'';
        char dian1= '"';
        char c = selectedText.charAt(0);
        if (dian == c) {
            selectedText = StrUtil.replace(selectedText, 0, 1, '\"');
        } else if (c == dian1) {
            selectedText = StrUtil.replace(selectedText, 0, 1, '\'');
        }
        char c1 = selectedText.charAt(selectedText.length() - 1);
        if (c1 == dian) {
            selectedText = StrUtil.replace(selectedText, selectedText.length()-1, selectedText.length(), '\"');
        } else if (c1==dian1) {
            selectedText = StrUtil.replace(selectedText, selectedText.length()-1, selectedText.length(), '\'');
        }
        return selectedText;
    }
}

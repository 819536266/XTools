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
 * 去双引号 加 双引号
 */
public class QuotesChangeAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        Editor requiredData = e.getRequiredData(LangDataKeys.EDITOR);
        SelectionModel selectionModel = requiredData.getSelectionModel();
        String selectedText = selectionModel.getSelectedText();
        if(!ObjectUtil.isEmpty(selectedText)){
            int selectionStart = selectionModel.getSelectionStart();
            int selectionEnd = selectionModel.getSelectionEnd();
            Document document = selectionModel.getEditor().getDocument();
            Runnable runnable = () -> document.replaceString(selectionStart, selectionEnd, quotesChange(selectedText));
            WriteCommandAction.runWriteCommandAction(project,runnable);
            selectionModel.removeSelection();
        }
    }


    private static String quotesChange(String selectedText){
        char dian= '"';
        char c = selectedText.charAt(0);
        if (dian == c) {
            selectedText=StrUtil.removePrefix(selectedText,"\"");
        }else{
            selectedText= StrUtil.addPrefixIfNot(selectedText,"\"");
        }
        char c1 = selectedText.charAt(selectedText.length() - 1);
        if (dian == c1) {
            selectedText=StrUtil.removeSuffix(selectedText,"\"");
        }else{
            selectedText= StrUtil.addSuffixIfNot(selectedText,"\"");
        }
        return selectedText;
    }

}

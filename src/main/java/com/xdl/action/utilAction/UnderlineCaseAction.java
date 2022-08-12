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
 * 驼峰式命名的字符串转换为下划线方式
 */
public class UnderlineCaseAction extends AnAction {

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
            Runnable runnable = () -> document.replaceString(selectionStart, selectionEnd, StrUtil.toUnderlineCase(selectedText));
            WriteCommandAction.runWriteCommandAction(project,runnable);
            selectionModel.removeSelection();
        }
    }
}

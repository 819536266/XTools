package com.xdl.action.utilAction;

import cn.hutool.core.util.IdUtil;
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

public class UuidAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        Editor requiredData = e.getRequiredData(LangDataKeys.EDITOR);
        SelectionModel selectionModel = requiredData.getSelectionModel();
        String uuid = IdUtil.fastSimpleUUID();
        int selectionStart = selectionModel.getSelectionStart();
        Document document = selectionModel.getEditor().getDocument();
        Runnable runnable = () -> document.insertString(selectionStart , uuid);
        WriteCommandAction.runWriteCommandAction(project, runnable);
        selectionModel.removeSelection();
    }
}

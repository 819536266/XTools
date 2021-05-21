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
import com.xdl.model.DataCenter;
import com.xdl.ui.OutContent;
import com.xdl.util.props2yaml.Props2Yaml;
import com.xdl.util.yaml2props.Yaml2Props;

public class P2YAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        Editor requiredData = e.getRequiredData(LangDataKeys.EDITOR);
        SelectionModel selectionModel = requiredData.getSelectionModel();
        String selectedText = selectionModel.getSelectedText();
        if (!ObjectUtil.isEmpty(selectedText)) {
            try {
                String convert = Props2Yaml.fromContent(selectedText).convert();
                int selectionStart = selectionModel.getSelectionStart();
                int selectionEnd = selectionModel.getSelectionEnd();
                Document document = selectionModel.getEditor().getDocument();
                Runnable runnable = () -> document.replaceString(selectionStart, selectionEnd , convert);
                WriteCommandAction.runWriteCommandAction(project, runnable);
                selectionModel.removeSelection();
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        }
    }
}

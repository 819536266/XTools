package com.xdl.action.utilAction;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.ui.popup.IconButton;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTypesUtil;
import com.intellij.ui.EditorTextField;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextArea;
import com.intellij.ui.components.JBViewport;
import com.xdl.action.XToolsAction;
import com.xdl.constant.CommonConstants;
import com.xdl.ui.JsonFormat;
import com.xdl.ui.XTools;
import com.xdl.util.Icons;
import com.xdl.util.PsiClassUtils;
import com.xdl.util.parser.DefaultPOJO2JSONParser;
import com.xdl.util.parser.POJO2JSONParser;
import com.xdl.util.parser.type.SpecifyType;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Bx_Hu
 */
public class ObjectJsonAction extends AnAction {


    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        PsiElement requiredData = anActionEvent.getRequiredData(LangDataKeys.PSI_ELEMENT);
        PsiClass psiClass = PreviewAction.getPsiClass(requiredData);
        if (psiClass == null) {
            return;
        }
        POJO2JSONParser pojo2JSONParser = new DefaultPOJO2JSONParser();
        XTools xTools = XToolsAction.getUi(anActionEvent.getProject(), XTools.class);
        JsonFormat jsonFormat = xTools.getJsonFormat();
        jsonFormat.getFormatJson()
                .setText(pojo2JSONParser.psiClasstToJSONString(psiClass));
        xTools.openParent(3);
    }


}

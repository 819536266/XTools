package com.xdl.action.utilAction;

import cn.hutool.core.date.DateUtil;
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
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.ui.EditorTextField;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextArea;
import com.intellij.ui.components.JBViewport;
import com.xdl.action.XToolsAction;
import com.xdl.constant.CommonConstants;
import com.xdl.ui.JsonFormat;
import com.xdl.ui.XTools;
import com.xdl.util.Icons;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Bx_Hu
 */
public class ObjectJsonAction extends AnAction {

    public static Map<String, String> typeGeneratedMap = new HashMap<String, String>() {
        {
            put("boolean", "true");
            put("java.lang.Boolean", "true");
            put("int", "0");
            put("byte", "0");
            put("java.lang.Byte", "0");
            put("java.lang.Integer", "0");
            put("java.lang.String", "");
            put("java.math.BigDecimal", "0");
            put("java.lang.Long", "0");
            put("long", "0");
            put("short", "0");
            put("java.lang.Short", "0");
            put("java.util.Date", DateUtil.now());
            put("float", "0.0");
            put("java.lang.Float", "0.0");
            put("double", "0.0");
            put("java.lang.Double", "0.0");
            put("java.lang.Character", "");
            put("char", "");
            put("java.time.LocalDateTime", DateUtil.now());
            put("java.time.LocalDate", DateUtil.today());

        }
    };


    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        Editor editor = anActionEvent.getRequiredData(LangDataKeys.EDITOR);
        PsiElement requiredData = anActionEvent.getRequiredData(LangDataKeys.PSI_ELEMENT);
        PsiClass localVarialbeContainingClass = PreviewAction.getPsiClass(requiredData);
        if (localVarialbeContainingClass == null) {
            return;
        }
        PsiField[] fields = localVarialbeContainingClass.getFields();
        JSONObject jsonObject = new JSONObject();
        for (PsiField field : fields) {
            if (field != null && !CommonConstants.SERIAL_VERSION_UID.equals(field.getName()) && !field.hasModifierProperty(CommonConstants.FINAL)) {
                String classType = field.getType()
                        .getCanonicalText();
                String value = typeGeneratedMap.get(classType);
                jsonObject.set(field.getName(), value == null ? "" : value);
            }
        }
//        XTools xTools = XToolsAction.getUi(anActionEvent.getProject(), XTools.class);
//        JsonFormat jsonFormat = xTools.getJsonFormat();
//        jsonFormat.getFormatJson()
//                .setText(JSONUtil.formatJsonStr(JSONUtil.toJsonStr(jsonObject)));
//        xTools.openParent(3);

        Document document = EditorFactory.getInstance()
                .createDocument(JSONUtil.formatJsonStr(JSONUtil.toJsonStr(jsonObject)));
        EditorTextField editorTextField =
                new EditorTextField(document, anActionEvent.getProject(), JavaFileType.INSTANCE);
        editorTextField.setSize(new Dimension(300, 500));
        editorTextField.setViewer(true);
        editorTextField.setVisible(true);
        editorTextField.setFont(new Font(null, Font.PLAIN, 12));
        JBScrollPane jbScrollPane = new JBScrollPane(editorTextField);
        jbScrollPane.setMaximumSize(new Dimension(600, 800));
        jbScrollPane.setComponentOrientation(ComponentOrientation.UNKNOWN);
        JBPopupFactory instance = JBPopupFactory.getInstance();
        instance.createComponentPopupBuilder(jbScrollPane, new JBTextArea())
                .setTitle("预览")
                .setMovable(true)
                .setCancelKeyEnabled(true)
                .setCancelButton(new IconButton("Close", Icons.CLOSE))
                .setMinSize(new Dimension(200, 400))
                .setResizable(true)
                .setMayBeParent(true)
                .setNormalWindowLevel(false)
                .setRequestFocus(true)
                .createPopup()
                .showInBestPositionFor(editor);
    }
}

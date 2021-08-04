package com.xdl.action.utilAction;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.xdl.action.XToolsAction;
import com.xdl.constant.CommonConstants;
import com.xdl.ui.JsonFormat;
import com.xdl.ui.XTools;
import org.jetbrains.annotations.NotNull;

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
                jsonObject.put(field.getName(), value == null ? "" : value);
            }
        }
        XTools xTools = XToolsAction.getUi(anActionEvent.getProject(), XTools.class);
        JsonFormat jsonFormat = xTools.getJsonFormat();
        jsonFormat.getFormatJson()
                .setText(JSONUtil.formatJsonStr(JSONUtil.toJsonStr(jsonObject)));
        xTools.openParent(3);
    }
}

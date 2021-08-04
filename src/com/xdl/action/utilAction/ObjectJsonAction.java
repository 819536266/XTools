package com.xdl.action.utilAction;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.xdl.action.XToolsAction;
import com.xdl.ui.JsonFormat;
import com.xdl.ui.XTools;
import org.jetbrains.annotations.NotNull;


/**
 * @author Bx_Hu
 */
public class ObjectJsonAction extends AnAction {



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
            if (field != null && !"serialVersionUID".equals(field.getName())) {
                /*String classType = field.getType()
                        .getCanonicalText();
                String value = GenerateAllSetterBase.typeGeneratedMap.get(classType);*/
                jsonObject.put(field.getName(), "");
            }
        }
        XTools xTools = XToolsAction.getUi(anActionEvent.getProject(), XTools.class);
        JsonFormat jsonFormat = xTools.getJsonFormat();
        jsonFormat.getFormatJson().setText(JSONUtil.formatJsonStr(JSONUtil.toJsonStr(jsonObject)));
        xTools.openParent(3);
    }
}

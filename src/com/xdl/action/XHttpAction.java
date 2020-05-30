package com.xdl.action;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.*;
import com.xdl.model.*;
import com.xdl.ui.OutContent;
import com.xdl.ui.XHttpUi;
import com.xdl.util.MethodExcludeParam;
import com.xdl.util.SpringUtils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XHttpAction extends AnAction {

    private static Map<String, XHttpModel> modelMap = new HashMap<>();

    private PsiMethod psiMethod;

    private SpringRequestMethodAnnotation methodType;

    public XHttpAction() {
        super();
    }

    public XHttpAction(PsiMethod psiMethod, SpringRequestMethodAnnotation methodType, String text, String description,
                       Icon icon) {
        super(text, description, icon);
        this.psiMethod = psiMethod;
        this.methodType = methodType;
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        XHttpUi.xHttpModel = createXHttpModel();
        XHttpUi xHttpUi = XHttpUi.xHttpUi;
        if (ObjectUtil.isNotEmpty(xHttpUi)) {
            xHttpUi.open();
        }
        // TODO: insert action logic here
    }

    /**
     * 封装请求参数
     *
     * @return
     */
    private XHttpModel createXHttpModel() {
        PsiClass psiClass = (PsiClass) psiMethod.getParent();
        String id = psiClass.getQualifiedName() + "." + psiMethod.getName() + "." + methodType.getQualifiedName();
        XHttpModel xHttpModel1 = modelMap.get(id);
        if (ObjectUtil.isNotEmpty(xHttpModel1)) {
            return xHttpModel1;
        }
        XHttpModel xHttpModel = new XHttpModel();
        xHttpModel.setMethodType(methodType.getMethod());
        xHttpModel.setParamList(getParamList(psiMethod));
        xHttpModel.setPath(getPath(psiClass, psiMethod));
        modelMap.put(id, xHttpModel);
        return xHttpModel;
    }


    /**
     * 获取请求参数
     *
     * @param psiMethod psiMethod
     * @return List<XHttpParam>
     */
    private List<XHttpParam> getParamList(PsiMethod psiMethod) {
        PsiParameterList parameterList = psiMethod.getParameterList();
        PsiParameter[] parameters = parameterList.getParameters();
        List<XHttpParam> paramList = CollUtil.newArrayList();
        for (PsiParameter parameter : parameters) {
            boolean contains = ArrayUtil.contains(MethodExcludeParam.exclude, parameter.getType()
                    .getCanonicalText());
            if (contains) continue;
            XHttpParam xHttpParam = new XHttpParam();
            xHttpParam.setName(parameter.getName());
            xHttpParam.setClassType(parameter.getType()
                    .getCanonicalText());
            paramList.add(xHttpParam);
        }
        return paramList;
    }


    /**
     * 获取方法请求路径
     *
     * @param psiClass  类
     * @param psiMethod 方法
     * @return path
     */
    private String getPath(PsiClass psiClass, PsiMethod psiMethod) {
        String path = "";
        PsiAnnotation annotation = psiClass.getAnnotation(SpringRequestMethodAnnotation.REQUEST_MAPPING.getQualifiedName());
        PsiAnnotationMemberValue value = annotation.findAttributeValue(SpringUtils.MAPPING_PARAM_VALUE);
        path += value.getText()
                .replace("\"", "");
        PsiAnnotation methodAnnotation = psiMethod.getAnnotation(methodType.getQualifiedName());
        if (ObjectUtil.isEmpty(methodAnnotation)) {
            methodAnnotation = psiMethod.getAnnotation(SpringRequestMethodAnnotation.REQUEST_MAPPING.getQualifiedName());
        }
        PsiAnnotationMemberValue attributeValue = methodAnnotation.findAttributeValue(SpringUtils.MAPPING_PARAM_VALUE);
        path += attributeValue.getText()
                .replace("\"", "");
        return path;
    }

}

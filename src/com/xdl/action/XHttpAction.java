package com.xdl.action;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.psi.*;
import com.xdl.model.*;
import com.xdl.ui.XHttpUi;
import com.xdl.util.SpringUtils;

import javax.swing.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XHttpAction extends AnAction {

    public static Map<String, XHttpModel> modelMap = new HashMap<>();

    public XHttpModel xHttpModel;

    public static Map<Project, XHttpUi> xHttpUiMap = new HashMap<>();

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
        Project project = e.getProject();
        xHttpModel = createXHttpModel();
        XHttpUi xHttpUi = xHttpUiMap.get(project);
        if (ObjectUtil.isEmpty(xHttpUi)) {
            NotificationGroup notificationGroup = new NotificationGroup("XHttpNotification", NotificationDisplayType.BALLOON, false);
            //  content :  通知内容 type  ：通知的类型，warning,info,error
            Notification notification = notificationGroup.createNotification("请初始化窗口!", MessageType.WARNING);
            Notifications.Bus.notify(notification);
        } else {
            if (ObjectUtil.isNotEmpty(xHttpUi)) {
                xHttpUi.open(xHttpModel);
            }
        }
        // TODO: insert action logic here
    }

    /**
     * 封装请求参数
     *
     * @return XHttpModel
     */
    private XHttpModel createXHttpModel() {
        PsiClass psiClass = (PsiClass) psiMethod.getParent();
        String id = psiClass.getQualifiedName() + "." + psiMethod.getName() + "." + methodType.getQualifiedName();
        XHttpModel xHttpModel1 = modelMap.get(id);
        if (ObjectUtil.isNotEmpty(xHttpModel1)) {
            return xHttpModel1;
        }
        XHttpModel xHttpModel = new XHttpModel();
        xHttpModel.setKey(id);
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
    private List<XHttpParam> getParamList(PsiMethod psiMethod)  {
        PsiParameterList parameterList = psiMethod.getParameterList();
        //获取参数集合
        PsiParameter[] parameters = parameterList.getParameters();
        List<XHttpParam> paramList = CollUtil.newArrayList();
        for (PsiParameter parameter : parameters) {
            //排除参数
            boolean contains = ArrayUtil.contains(Settings.getInstance()
                    .getExclude(), parameter.getType()
                    .getCanonicalText());
            if (contains) continue;
            System.out.println(parameter.getType().toString());
            String canonicalText = parameter.getType()
                    .getCanonicalText();
//            try {
//                Class<?> aClass = Class.forName("com.xd.user.controller.DepartmentController");
//                System.out.println(JSONUtil.toJsonStr(aClass.getMethods()));
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
            PsiAnnotation annotation = parameter.getAnnotation(SpringUtils.REQUEST_BODY_CLASS_PATH);
            XHttpParam xHttpParam = new XHttpParam();
            xHttpParam.setName(parameter.getName());
            xHttpParam.setClassType(canonicalText);
            xHttpParam.setType(!ObjectUtil.isEmpty(annotation) ? XHttpParam.BODY_TYPE : SpringUtils.MULTIPART_FILE_CLASS_PATH.equals(canonicalText)
                    || SpringUtils.MULTIPART_FILES_CLASS_PATH.equals(canonicalText) ? XHttpParam.FILE_TYPE : XHttpParam.TEXT_TYPE);
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
        if (!ObjectUtil.isEmpty(annotation)) {
            PsiAnnotationMemberValue value = annotation.findAttributeValue(SpringUtils.MAPPING_PARAM_VALUE);
            if (!ObjectUtil.isEmpty(value) && !"{}".equals(value.getText()))
                path += StrUtil.stripIgnoreCase(value.getText()
                        .replace("\"", ""), "/", "/");
        }
        PsiAnnotation methodAnnotation = psiMethod.getAnnotation(methodType.getQualifiedName());
        if (ObjectUtil.isEmpty(methodAnnotation)) {
            methodAnnotation = psiMethod.getAnnotation(SpringRequestMethodAnnotation.REQUEST_MAPPING.getQualifiedName());
        }

        if (ObjectUtil.isEmpty(methodAnnotation)) return path;
        PsiAnnotationMemberValue attributeValue = methodAnnotation.findAttributeValue(SpringUtils.MAPPING_PARAM_VALUE);

        if (ObjectUtil.isEmpty(attributeValue) || attributeValue.getText().equals("{}")) return path;

        path += "/" + StrUtil.stripIgnoreCase(attributeValue.getText()
                .replace("\"", ""), "/", "/");
        return path;
    }


}

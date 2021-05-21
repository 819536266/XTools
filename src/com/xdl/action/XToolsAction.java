package com.xdl.action;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.psi.*;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;
import com.xdl.model.*;
import com.xdl.ui.*;
import com.xdl.util.SpringUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XToolsAction extends AnAction {

    public static Map<String, XHttpModel> modelMap = new HashMap<>();

    public XHttpModel xHttpModel;

    public static Map<Project, Map<Class<?>, Object>> xHttpUiMap = new HashMap<>();

    private PsiMethod psiMethod;

    private SpringRequestMethodAnnotation methodType;


    public XToolsAction() {
        super();
    }

    public XToolsAction(PsiMethod psiMethod, SpringRequestMethodAnnotation methodType, String text, String description,
                        Icon icon) {
        super(text, description, icon);
        this.psiMethod = psiMethod;
        this.methodType = methodType;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        xHttpModel = createXHttpModel();
        XHttpUi xHttpUi = getUi(project, XHttpUi.class);

        xHttpUi.open(xHttpModel);
    }


    /**
     * 初始化工具窗口
     *
     * @param project project
     */
    public static void init(Project project) {
        //创建出NoteListWindow对象
        XHttpUi xHttpUi = new XHttpUi(project);
        XTools xtools = new XTools(project);
        register(xHttpUi.getDebugPanel(), "XHttp", 0);
        register(xtools.getToolTabbedPane(), "XTools", 1);
        putUi(project, xHttpUi);
        putUi(project, xtools);
    }


    /**
     * 获取UI
     *
     * @param project project
     * @param tClass  UI类型
     * @param <T>     返回的UI类型
     * @return UI类
     */
    public static <T> T getUi(Project project, Class<T> tClass) {
        Map<Class<?>, Object> classMap = xHttpUiMap.get(project);
        if(classMap == null ){
            XHttpWindowFactory.toolWindow.getContentManager();
            classMap = xHttpUiMap.get(project);
        }
        Object o = classMap.get(tClass);
        return (T) o;
    }

    /**
     * 存入UI
     *
     * @param project project
     * @param o       o
     */
    public static void putUi(Project project, Object o) {
        Map<Class<?>, Object> classMap = xHttpUiMap.get(project);
        if (CollUtil.isEmpty(classMap)) {
            classMap = new HashMap<>(1);
        }
        classMap.put(o.getClass(), o);
        xHttpUiMap.put(project, classMap);
    }

    /**
     * 注册UI
     *
     * @param jComponent jTabbedPane
     * @param name       table名称
     * @param index      table下标
     */
    public static void register(JComponent jComponent, String name, int index) {
        //获取内容工厂的实例
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        //获取用于toolWindow显示的内容
        Content content = contentFactory.createContent(jComponent, name, false);
        XHttpWindowFactory.toolWindow.getContentManager().addContent(content, index);
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
    private List<XHttpParam> getParamList(PsiMethod psiMethod) {
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
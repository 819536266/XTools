package com.xdl.action;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTypesUtil;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;
import com.xdl.enums.ParamTypeEnum;
import com.xdl.model.Settings;
import com.xdl.model.SpringRequestMethodAnnotation;
import com.xdl.model.XHttpModel;
import com.xdl.model.XHttpParam;
import com.xdl.ui.XHttpUi;
import com.xdl.ui.XHttpWindowFactory;
import com.xdl.ui.XTools;
import com.xdl.util.PsiClassUtils;
import com.xdl.util.SpringUtils;
import com.xdl.util.parser.DefaultPOJO2JSONParser;
import com.xdl.util.parser.POJO2JSONParser;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Bx_Hu
 */
public class XToolsAction extends AnAction {

    public static Map<String, XHttpModel> modelMap = new HashMap<>();

    public XHttpModel xHttpModel;

    public static Map<Project, Map<Class<?>, Object>> xHttpUiMap = new HashMap<>();
    public static Map<Project, ToolWindow> ToolWindows = new HashMap<>();

    private PsiMethod psiMethod;

    private SpringRequestMethodAnnotation methodType;
    /**
     * 设置对象
     */
    private final Settings settings = Settings.getInstance();

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
     * @param project    project
     * @param toolWindow
     */
    public static void init(Project project, ContentManager toolWindow) {
        //创建出NoteListWindow对象
        XHttpUi xHttpUi = new XHttpUi(project);
        XTools xtools = new XTools(project);
        register(toolWindow, xHttpUi.getDebugPanel(), "XHttp", 0);
        register(toolWindow, xtools.getToolTabbedPane(), "XTools", 1);
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
        if (classMap == null) {
            XHttpWindowFactory xHttpWindowFactory = new XHttpWindowFactory();
            ToolWindow toolWindow = ToolWindows.get(project);
            xHttpWindowFactory.createToolWindowContent(project, toolWindow);
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
     * @param contentManager
     * @param jComponent     jTabbedPane
     * @param name           table名称
     * @param index          table下标
     */
    public static void register(ContentManager contentManager, JComponent jComponent, String name, int index) {
        //获取内容工厂的实例
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        //获取用于toolWindow显示的内容
        Content content = contentFactory.createContent(jComponent, name, false);
        contentManager.addContent(content, index);
    }


    /**
     * 封装请求参数
     *
     * @return XHttpModel
     */
    private XHttpModel createXHttpModel() {
        PsiClass psiClass = (PsiClass) psiMethod.getParent();
        String id = psiClass.getQualifiedName() + "." + psiMethod.getName() + "." + methodType.getQualifiedName();

        //从缓存中获取接口请求
        if (settings.getOrCache()) {
            XHttpModel xHttpModel1 = modelMap.get(id);
            if (ObjectUtil.isNotEmpty(xHttpModel1)) {
                return xHttpModel1;
            }
        }
        XHttpModel xHttpModel = new XHttpModel();
        xHttpModel.setKey(id);
        xHttpModel.setMethodType(methodType.getMethod());
        putParamList(xHttpModel, psiMethod);
        xHttpModel.setPath(getPath(psiClass, psiMethod));

        //设置到缓存中
        if (settings.getOrCache()) {
            modelMap.put(id, xHttpModel);
        }
        return xHttpModel;
    }


    /**
     * 获取请求参数
     *
     * @param xHttpModel xHttpModel
     * @param psiMethod  psiMethod
     */
    private void putParamList(XHttpModel xHttpModel, PsiMethod psiMethod) {
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

            String canonicalText = parameter.getType()
                    .getCanonicalText();
            PsiAnnotation annotation = parameter.getAnnotation(SpringUtils.REQUEST_BODY_CLASS_PATH);
            XHttpParam xHttpParam = new XHttpParam();
            xHttpParam.setName(parameter.getName());
            xHttpParam.setClassType(canonicalText);
            ParamTypeEnum paramTypeEnum = !ObjectUtil.isEmpty(annotation) ? ParamTypeEnum.BODY : SpringUtils.MULTIPART_FILE_CLASS_PATH.equals(canonicalText)
                    || SpringUtils.MULTIPART_FILES_CLASS_PATH.equals(canonicalText) ? ParamTypeEnum.FILE : ParamTypeEnum.TEXT;
            xHttpParam.setParamTypeEnum(paramTypeEnum);
            paramList.add(xHttpParam);

            //如果body类型,并且只有一个@RequestBody注释,设置RequestBody,表示有多个RequestBody 只存第一个
            if (ParamTypeEnum.BODY.equals(paramTypeEnum) && xHttpModel.getRequestBody() == null) {
                PsiClass psiClass = PsiTypesUtil.getPsiClass(parameter.getType());
                if(psiClass == null){
                    return;
                }
                POJO2JSONParser pojo2JSONParser = new DefaultPOJO2JSONParser();
                xHttpModel.setRequestBody(pojo2JSONParser.psiClasstToJSONString(psiClass));
            }
        }
        xHttpModel.setParamList(paramList);
    }

    /**
     * 生成json参数默认值
     *
     * @param field field
     * @return 默认值
     */
    public Object defaultValue(PsiField field) {
        return StrUtil.EMPTY;
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
            if (!ObjectUtil.isEmpty(value) && !"{}".equals(value.getText())) {
                path += StrUtil.stripIgnoreCase(value.getText()
                        .replace("\"", ""), "/", "/");
            }
        }
        PsiAnnotation methodAnnotation = psiMethod.getAnnotation(methodType.getQualifiedName());
        if (ObjectUtil.isEmpty(methodAnnotation)) {
            methodAnnotation = psiMethod.getAnnotation(SpringRequestMethodAnnotation.REQUEST_MAPPING.getQualifiedName());
        }

        if (ObjectUtil.isEmpty(methodAnnotation)) return path;
        PsiAnnotationMemberValue attributeValue = methodAnnotation.findAttributeValue(SpringUtils.MAPPING_PARAM_VALUE);

        if (ObjectUtil.isEmpty(attributeValue) || attributeValue.getText()
                .equals("{}")) {
            return path;
        }

        path += "/" + StrUtil.stripIgnoreCase(attributeValue.getText()
                .replace("\"", ""), "/", "/");
        return path;
    }


}

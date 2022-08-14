/*
 *  Copyright (c) 2017-2019, bruce.ge.
 *    This program is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU General Public License
 *    as published by the Free Software Foundation; version 2 of
 *    the License.
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *    GNU General Public License for more details.
 *    You should have received a copy of the GNU General Public License
 *    along with this program;
 */

package com.xdl.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.PsiClassReferenceType;
import com.intellij.psi.util.PsiTypesUtil;
import com.xdl.action.setter.GenerateAllSetterAction;
import com.xdl.constant.CommonConstants;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * @Author bruce.ge
 * @Date 2017/1/30
 * @Description
 */
public class PsiClassUtils {
    public static Map<String, String> typeGeneratedMap = new HashMap<>() {
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

    private final String collection = "java.util.Collection";

    public static boolean isNotSystemClass(PsiClass psiClass) {
        if (psiClass == null) {
            return false;
        }
        String qualifiedName = psiClass.getQualifiedName();
        if (qualifiedName == null || qualifiedName.startsWith("java.")) {
            return false;
        }
        return true;
    }

    public static boolean isValidSetMethod(PsiMethod m) {
        return m.hasModifierProperty(CommonConstants.PUBLIC) &&
                !m.hasModifierProperty(CommonConstants.STATIC) &&
                (m.getName()
                        .startsWith("set") || m.getName()
                        .startsWith("with"));
    }

    public static boolean isValidGetMethod(PsiMethod m) {
        return m.hasModifierProperty("public") && !m.hasModifierProperty(CommonConstants.STATIC) &&
                (m.getName()
                        .startsWith(GenerateAllSetterAction.GET) || m.getName()
                        .startsWith(GenerateAllSetterAction.IS));
    }

    public static void addSetMethodToList(PsiClass psiClass, List<PsiMethod> methodList) {
        PsiMethod[] methods = psiClass.getMethods();
        for (PsiMethod method : methods) {
            if (isValidSetMethod(method)) {
                methodList.add(method);
            }
        }
    }

    public static void addGettMethodToList(PsiClass psiClass, List<PsiMethod> methodList) {
        PsiMethod[] methods = psiClass.getMethods();
        for (PsiMethod method : methods) {
            if (isValidGetMethod(method)) {
                methodList.add(method);
            }
        }
    }

    @NotNull
    public static List<PsiMethod> extractSetMethods(PsiClass psiClass) {
        List<PsiMethod> methodList = new ArrayList<>();
        while (isNotSystemClass(psiClass)) {
            addSetMethodToList(psiClass, methodList);
            psiClass = psiClass.getSuperClass();
        }
        return methodList;
    }

    public static List<PsiMethod> extractGetMethod(PsiClass psiClass) {
        List<PsiMethod> methodList = new ArrayList<>();
        while (isNotSystemClass(psiClass)) {
            addGettMethodToList(psiClass, methodList);
            psiClass = psiClass.getSuperClass();
        }
        return methodList;
    }

    public static boolean checkClassHasValidSetMethod(PsiClass psiClass) {
        if (psiClass == null) {
            return false;
        }
        while (isNotSystemClass(psiClass)) {
            for (PsiMethod m : psiClass.getMethods()) {
                if (isValidSetMethod(m)) {
                    return true;
                }
            }
            psiClass = psiClass.getSuperClass();
        }
        return false;
    }


    public static boolean checkClasHasValidGetMethod(PsiClass psiClass) {
        if (psiClass == null) {
            return false;
        }
        while (isNotSystemClass(psiClass)) {
            for (PsiMethod m : psiClass.getMethods()) {
                if (isValidGetMethod(m)) {
                    return true;
                }
            }
            psiClass = psiClass.getSuperClass();
        }
        return false;
    }


    /**
     * PsiClass转化实体类
     *
     * @param psiClasses 实体类
     * @date 21:11 2022/8/13
     **/
    public static String beanToJsonStr(PsiClass psiClasses) {
        JSONObject jsonObject = new JSONObject();
        toJson(psiClasses, jsonObject);
        return JSONUtil.toJsonStr(jsonObject);
    }

    /**
     * PsiClass转化实体类
     *
     * @param psiClasses 实体类
     * @date 21:11 2022/8/13
     **/
    public static JSONObject beanToJson(PsiClass psiClasses) {
        JSONObject jsonObject = new JSONObject();
        toJson(psiClasses, jsonObject);
        return jsonObject;
    }

    /**
     * 递归转化实体类
     *
     * @param psiClasses 实体类
     * @param jsonObject jsonObject
     * @date 21:11 2022/8/13
     **/
    public static void toJson(PsiClass psiClasses, JSONObject jsonObject) {
        PsiField[] fields = psiClasses.getFields();
        if (ObjectUtil.isEmpty(fields)) {
            return;
        }
        for (PsiField field : fields) {
            // final 属性 和 serialVersionUID 不转化
            if (CommonConstants.SERIAL_VERSION_UID.equals(field.getName()) || field.hasModifierProperty(PsiModifier.FINAL)) {
                continue;
            }
            //解析到基本类型为止
            if (typeGeneratedMap.containsKey(field.getType()
                    .getCanonicalText())) {
                String classType = field.getType()
                        .getCanonicalText();
                String value = typeGeneratedMap.get(classType);
                jsonObject.set(field.getName(), value == null ? "" : value);
                continue;
            }
            //如果为泛型，继续往下解析
            PsiType type = field.getType();
            JSONObject childJson = new JSONObject();
//            PsiClass psiClass = putCollClass(jsonObject, field, childJson);
//            if (psiClass != null) {
//                toJson(psiClass, childJson);
//                continue;
//            }
//            psiClass = putMapClass(jsonObject, field, childJson);
//            if (psiClass != null) {
//                toJson(psiClass, childJson);
//                continue;
//            }
            PsiClass psiClass = PsiTypesUtil.getPsiClass(type);
            if (psiClass == null) {
                continue;
            }
            toJson(psiClass, childJson);
            jsonObject.set(field.getName(), childJson);
        }
    }


    private static PsiClass putCollClass(JSONObject jsonObject, PsiField field,
                                         JSONObject childJson) {
        PsiType type = field.getType();
        PsiType[] superTypes = type.getSuperTypes();
        String collPackageName = Collection.class.getCanonicalName();
        long collCount = Arrays.stream(superTypes)
                .filter(psiType -> ((PsiClassReferenceType) psiType).rawType()
                        .getCanonicalText()
                        .equals(collPackageName))
                .count();
        // 如果自己是 或者父类型是 集合类型处理
        if (ObjectUtil.notEqual(((PsiClassReferenceType) type).rawType()
                .getCanonicalText(), collPackageName) && collCount != 0) {
            return null;
        }
        PsiType parameter = ((PsiClassReferenceType) type).getParameters()[0];
        if (parameter == null) {
            return null;
        }
        PsiClass psiClass = PsiTypesUtil.getPsiClass(parameter);
        List<JSONObject> jsonObjects = CollUtil.toList(childJson);
        jsonObject.set(field.getName(), jsonObjects);
        return psiClass;
    }

    private static PsiClass putMapClass(JSONObject jsonObject, PsiField field, JSONObject childJson) {
        PsiType type = field.getType();
        String mapPackageName = Map.class.getCanonicalName();
        PsiType[] superTypes = type.getSuperTypes();
        long mapCount = Arrays.stream(superTypes)
                .filter(psiType -> ((PsiClassReferenceType) psiType).rawType()
                        .getCanonicalText()
                        .equals(mapPackageName))
                .count();
        // 如果自己是 或者父类型是 集合类型处理
        if (ObjectUtil.notEqual(((PsiClassReferenceType) type).rawType()
                .getCanonicalText(), mapPackageName) && mapCount != 0) {
            return null;
        }
        PsiType parameter = ((PsiClassReferenceType) type).getParameters()[0];
        if (parameter == null) {
            return null;
        }
        PsiClass psiClass = PsiTypesUtil.getPsiClass(parameter);
        List<JSONObject> jsonObjects = CollUtil.toList(childJson);
        jsonObject.set(field.getName(), jsonObjects);
        return psiClass;
    }
}

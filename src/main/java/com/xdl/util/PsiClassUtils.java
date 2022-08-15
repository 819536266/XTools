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



}

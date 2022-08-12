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

package com.xdl.action.setter;

import cn.hutool.core.util.StrUtil;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.util.IncorrectOperationException;
import com.xdl.constant.CommonConstants;
import com.xdl.util.PsiClassUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Bx_Hu
 */
public class GenerateWithAccessorsAction extends GenerateAllSetterBase {

    public GenerateWithAccessorsAction() {
        super(new GenerateAllHandlerAdapter() {
            @Override
            public boolean isAccessors() {
                return true;
            }
        });
    }


    @Override
    public void invoke(@NotNull Project project, Editor editor,
                       @NotNull PsiElement element) throws IncorrectOperationException {
        PsiClass psiClass = GenerateAllSetterBase.getLocalVarialbeContainingClass(element);
        // insert into the element.
        Document document = PsiDocumentManager.getInstance(project)
                .getDocument(element.getContainingFile());
        List<PsiMethod> psiMethods = PsiClassUtils.extractSetMethods(psiClass);
        String builder = "";
        for (PsiMethod method : psiMethods) {
            PsiParameter parameter = method.getParameterList()
                    .getParameters()[0];
            String value = null;
            if (parameter != null) {
                String classType = parameter.getType()
                        .getCanonicalText();
                value = GenerateAllSetterBase.typeGeneratedMap.get(classType);
            }
            int i = psiMethods.indexOf(method);
            builder = StrUtil.concat(false
                    , builder
                    , StrUtil.DOT
                    , method.getName()
                    , "(", value, ")"
                    , (psiMethods.size() - 1) == i ? "" : StrUtil.LF);
        }


        String finalBuilder = builder;
        WriteCommandAction.runWriteCommandAction(project, () -> {
            if (document != null) {
                document.insertString(element.getTextRange()
                        .getEndOffset() + 2, finalBuilder);
                PsiDocumentManager.getInstance(project)
                        .commitDocument(document);
            }
        });
    }


    @NotNull
    @Override
    public String getText() {
        return CommonConstants.GENERATE_ACCESSORS_METHOD;
    }
}

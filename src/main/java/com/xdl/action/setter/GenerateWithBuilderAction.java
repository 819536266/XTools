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

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTypesUtil;
import com.intellij.util.IncorrectOperationException;
import com.xdl.constant.CommonConstants;
import org.jetbrains.annotations.NotNull;

/**
 * @author Bx_Hu 2020/9/23
 */

public class GenerateWithBuilderAction extends GenerateAllSetterBase {
    public GenerateWithBuilderAction() {
        super(new GenerateAllHandlerAdapter() {
            @Override
            public boolean forBuilder() {
                return true;
            }
        });
    }


    @Override
    public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement element) throws IncorrectOperationException {
        PsiClass localVarialbeContainingClass = GenerateAllSetterBase.getLocalVarialbeContainingClass(element);
          for (PsiMethod method : localVarialbeContainingClass.getMethods()) {
            if (method.getName().equals(CommonConstants.BUILDER_METHOD_NAME)) {
                PsiType returnType = method.getReturnType();
                PsiClass psiClass = PsiTypesUtil.getPsiClass(returnType);
                StringBuilder builder = new StringBuilder(localVarialbeContainingClass.getQualifiedName() + ".builder()");
                for (PsiMethod psiClassMethod : psiClass.getMethods()) {
                    if (!psiClassMethod.isConstructor() && !psiClassMethod.getName().equals("toString")
                            && !psiClassMethod.getName().equals("build")) {
                        builder.append("." + psiClassMethod.getName() + "()");
                    }
                }
                builder.append(".build();");

                // insert into the element.
                Document document = PsiDocumentManager.getInstance(project).getDocument(element.getContainingFile());

                WriteCommandAction.runWriteCommandAction(project, () -> {
                    document.insertString(element.getTextRange().getEndOffset() + 2, builder.toString());
                    PsiDocumentManager.getInstance(project).commitDocument(document);
                });

            }
        }
    }


    @NotNull
    @Override
    public String getText() {
        return CommonConstants.GENERATE_BUILDER_METHOD;
    }
}

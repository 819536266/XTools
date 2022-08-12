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

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.xdl.constant.CommonConstants;
import org.jetbrains.annotations.NotNull;

/**
 * @author Bx_Hu
 */
public class AssertJAssertAllGetterAction extends GenerateAllSetterBase {
    public AssertJAssertAllGetterAction() {
        super(new GenerateAllHandlerAdapter() {
            @Override
            public boolean isSetter() {
                return false;
            }

            @Override
            public String formatLine(String line) {
                return "assertThat(" + line.substring(0, line.length() - 1) + ").isEqualTo();";
            }
        });
    }


    @NotNull
    @Override
    public String getText() {
        return CommonConstants.ASSERT_ALL_PROPS;
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement element) {
        PsiFile containingFile = element.getContainingFile();
        if(containingFile==null){
            return false;
        }
        VirtualFile virtualFile = containingFile.getVirtualFile();
        if(virtualFile==null){
            return false;
        }
        boolean inTestSourceContent = ProjectRootManager.getInstance(element.getProject()).getFileIndex().isInTestSourceContent(virtualFile);
        if (inTestSourceContent) {
            return super.isAvailable(project, editor, element);
        }
        return false;
    }
}

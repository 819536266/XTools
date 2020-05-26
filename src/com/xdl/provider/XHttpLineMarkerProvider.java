/*
package com.xdl.provider;

import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProviderDescriptor;
import com.intellij.execution.ExecutionBundle;
import com.intellij.execution.lineMarker.RunLineMarkerContributor.Info;
import com.intellij.lang.jvm.annotation.JvmAnnotationAttribute;
import com.intellij.lang.jvm.annotation.JvmAnnotationConstantValue;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.actionSystem.Separator;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.openapi.editor.markup.MarkupEditorFilter;
import com.intellij.openapi.editor.markup.MarkupEditorFilterFactory;
import com.intellij.openapi.util.IconLoader;
import com.intellij.psi.*;
import com.intellij.util.Function;
import com.xdl.action.XHttpAction;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.List;

*/
/**
 * @author huboxin
 * @description: TODO
 * @date 13:32 2020/5/26
 **//*

public class XHttpLineMarkerProvider extends LineMarkerProviderDescriptor {

    @Override
    public LineMarkerInfo<?> getLineMarkerInfo(@NotNull PsiElement element) {

        if (!(element instanceof PsiMethod)) return null;

        PsiMethod psiMethod = (PsiMethod) element;
        // System.out.println("类内容  :"+psiMethod.getContext().getText()); //类内容
        // System.out.println("注释内容  :"+psiMethod.getDocComment().getText()); //注释内容
        // System.out.println("方法全部内容  :"+psiMethod.getOriginalElement().getText()); //方法全部内容
        //System.out.println("内容  :"+psiMethod.getParameterList().getText());  (@PathVariable(value = "materialId") Integer materialId, @RequestBody List<Competing> competingList)
        // System.out.println("类内容  :"+psiMethod.getBody().getText()); //方法内容 不带方法名
        PsiAnnotation getMapping = psiMethod.getAnnotation("@PostMapping");
        PsiAnnotation[] annotations = psiMethod.getAnnotations();
        for (PsiAnnotation annotation : annotations) {
            if (annotation.getText()
                    .contains("PostMapping")) {
                //System.out.println("注解:"+annotation.getText()); //@PostMapping("resetCompeting/{materialId}")
                getMapping = annotation;
            }
        }
        if (getMapping == null) {
            return null;
        }
        // System.out.println("+++"+getMapping.getText());  +++@PostMapping("resetCompeting/{materialId}")
        // System.out.println("+++----"+getMapping.getQualifiedName());  +++----org.springframework.web.bind.annotation.PostMapping
        PsiAnnotationParameterList parameterList = getMapping.getParameterList();
        String text = parameterList.getText();
        // System.out.println("+++"+text);  +++("resetCompeting/{materialId}")
        List<JvmAnnotationAttribute> attributes = getMapping.getAttributes();
        PsiAnnotationMemberValue value = getMapping.findAttributeValue("value");
        // System.out.println(value.getText()); //"resetCompeting/{materialId}"
        for (JvmAnnotationAttribute
                attribute : attributes) {
            JvmAnnotationConstantValue attributeValue = (JvmAnnotationConstantValue) attribute.getAttributeValue();
            //System.out.println("----"+attribute.getAttributeName()+"--"+attributeValue.getConstantValue());  // ----value--resetCompeting/{materialId}
        }
        Info info = new Info(new XHttpAction(psiMethod.getBody(), "GetMapping", "get请求", IconLoader.getIcon("/images/mapper_method.png")));
        DefaultActionGroup actionGroup = new DefaultActionGroup();
        actionGroup.add(new XHttpAction(psiMethod.getBody(), "PetMapping", "get请求", IconLoader.getIcon("/images/mapper_method.png")));
        actionGroup.add(info.actions[0]);
        actionGroup.add(new Separator());
        Function<PsiElement, String> tooltipProvider = element1 -> {
            final StringBuilder tooltip = new StringBuilder();
            if (info.tooltipProvider != null) {
                String string = info.tooltipProvider.apply(element1);
                if (string == null) return null;
                if (tooltip.length() != 0) {
                    tooltip.append("\n");
                }
                tooltip.append(string);
            }
            return tooltip.length() == 0 ? null : tooltip.toString();
        };
        return new RunLineMarkerInfo(psiMethod.getBody(), IconLoader.getIcon("/images/mapper_method.png"), tooltipProvider, actionGroup);
    }

    static class RunLineMarkerInfo extends LineMarkerInfo<PsiElement> {
        private final DefaultActionGroup myActionGroup;

        RunLineMarkerInfo(PsiElement element, Icon icon, Function<PsiElement, String> tooltipProvider,
                          DefaultActionGroup actionGroup) {
            super(element, element.getTextRange(), icon, tooltipProvider, null, GutterIconRenderer.Alignment.CENTER);
            myActionGroup = actionGroup;
        }

        @Override
        public GutterIconRenderer createGutterRenderer() {
            return new LineMarkerGutterIconRenderer<PsiElement>(this) {
                @Override
                public AnAction getClickAction() {
                    return null;
                }

                @Override
                public boolean isNavigateAction() {
                    return true;
                }

                @Override
                public ActionGroup getPopupMenuActions() {
                    return myActionGroup;
                }
            };
        }

        @NotNull
        @Override
        public MarkupEditorFilter getEditorFilter() {
            return MarkupEditorFilterFactory.createIsNotDiffFilter();
        }
    }

    @NotNull
    @Override
    public String getName() {
        return "";
    }

}
*/

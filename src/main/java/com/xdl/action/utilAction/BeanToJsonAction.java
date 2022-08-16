package com.xdl.action.utilAction;

import com.intellij.json.JsonFileType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.popup.ActiveIcon;
import com.intellij.openapi.ui.popup.IconButton;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.psi.*;
import com.intellij.ui.LanguageTextField;
import com.intellij.ui.components.JBViewport;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.components.BorderLayoutPanel;
import com.xdl.util.Icons;
import com.xdl.util.MyLanguageTextField;
import com.xdl.util.parser.DefaultPOJO2JSONParser;
import com.xdl.util.parser.POJO2JSONParser;
import org.jetbrains.annotations.NotNull;


/**
 * @author Bx_Hu
 */
public class BeanToJsonAction extends AnAction {


    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        PsiElement requiredData = anActionEvent.getRequiredData(LangDataKeys.PSI_ELEMENT);
        Editor editor = anActionEvent.getRequiredData(LangDataKeys.EDITOR);
        PsiClass psiClass = PreviewAction.getPsiClass(requiredData);
        if (psiClass == null) {
            return;
        }
        POJO2JSONParser pojo = new DefaultPOJO2JSONParser();
        LanguageTextField languageTextField = new MyLanguageTextField(anActionEvent.getProject(), JsonFileType.INSTANCE.getLanguage(), JsonFileType.INSTANCE);
        languageTextField.setText(pojo.psiClasstToJSONString(psiClass));
        BorderLayoutPanel borderLayoutPanel = JBUI.Panels.simplePanel()
                .withPreferredSize(600, 400)
                .addToCenter(languageTextField);
        JBPopupFactory.getInstance()
                .createComponentPopupBuilder(borderLayoutPanel, new JBViewport())
                .setTitle("BeanToJson")
                .setTitleIcon(new ActiveIcon(Icons.X))
                .setMovable(true)
//                .setCancelButton(new IconButton("Close", Icons.CLOSE))
                .setCancelKeyEnabled(true)
                .setProject(anActionEvent.getProject())
                .setBelongsToGlobalPopupStack(true)
                .setCancelOnClickOutside(true)
                .setResizable(true)
                .setCancelOnOtherWindowOpen(true)
                .setNormalWindowLevel(false)
                .createPopup()
                .showInBestPositionFor(editor);

//        XTools xTools = XToolsAction.getUi(anActionEvent.getProject(), XTools.class);
//        JsonFormat jsonFormat = xTools.getJsonFormat();
//        jsonFormat.getFormatJson()
//                .setText(pojo2JSONParser.psiClasstToJSONString(psiClass));
//        xTools.openParent(3);
    }


}

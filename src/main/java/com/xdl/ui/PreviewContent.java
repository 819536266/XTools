package com.xdl.ui;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.EditorTextField;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;


/**
 * @author Bx_Hu
 */
public class PreviewContent extends DialogWrapper {


    public static EditorTextField editorTextField;

    public PreviewContent() {
        super(true);
        init();
    }



    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel jPanel = new JPanel();
        jPanel.setPreferredSize(new Dimension(500, 700));
        JScrollPane jPanel1 = new JScrollPane();
        jPanel1.setPreferredSize(new Dimension(500, 700));
        jPanel1.getViewport().add(editorTextField);
        jPanel1.validate();
        jPanel.add(jPanel1);

        //设置滚动条在开始位置
        JScrollBar verticalScrollBar = jPanel1.getVerticalScrollBar();
        verticalScrollBar.setValue(verticalScrollBar.getMinimum());
        return jPanel;
    }


}

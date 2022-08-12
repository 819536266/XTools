package com.xdl.ui;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;


/**
 * @author Bx_Hu
 */
public class PreviewContent extends DialogWrapper {


    public static  String text;

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
        jPanel1.setMaximumSize(new Dimension(-1, -1));
        jPanel1.setMinimumSize(new Dimension(-1, -1));

        JTextArea jTextArea = new JTextArea();
        jTextArea.setFont(new Font("Default", Font.PLAIN, 12));
        jTextArea.setText(text);
        //设置光标在开始位置
        jTextArea.setSelectionStart(1);
        jTextArea.setSelectionEnd(1);

        jPanel1.getViewport().add(jTextArea);
        jPanel1.validate();
        jPanel.add(jPanel1);

        //设置滚动条在开始位置
        JScrollBar verticalScrollBar = jPanel1.getVerticalScrollBar();
        verticalScrollBar.setValue(verticalScrollBar.getMinimum());
        return jPanel;
    }


}

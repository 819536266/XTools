package com.xdl.ui;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * @program:Mark
 * @description:创建内容
 * @author:胡博欣
 * @create:2020-05-23-51
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
        jPanel.setPreferredSize(new Dimension(500, 800));
        JScrollPane jPanel1 = new JScrollPane();
        jPanel1.setPreferredSize(new Dimension(500, 800));
        jPanel1.setMaximumSize(new Dimension(-1, -1));
        jPanel1.setMinimumSize(new Dimension(-1, -1));
        JTextArea jTextArea = new JTextArea();
        jTextArea.setFont(new Font("Default", Font.PLAIN, 12));
        jTextArea.setText(text);
        jPanel1.getViewport().add(jTextArea);
        jPanel1.validate();
        jPanel.add(jPanel1);
        return jPanel;
    }


}

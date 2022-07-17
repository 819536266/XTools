package com.xdl.ui;

import cn.hutool.core.util.StrUtil;
import com.xdl.util.props2yaml.Props2Yaml;
import com.xdl.util.yaml2props.Yaml2Props;
import lombok.Data;

import javax.swing.*;

/**
 * @author Bx_Hu
 */
@Data
public class Convert {
    private JPanel convertPanel;
    private JButton yTOPButton;
    private JButton clearButton;
    private JButton pTOYButton;
    private JTextArea yaml;
    private JTextArea properties;


    public Convert() {
        pTOYButton.addActionListener(o->{
            String text = properties.getText();
            if(!StrUtil.isEmpty(text)){
                String convert = Props2Yaml.fromContent(text).convert();
                yaml.setText(convert);
            }
        });
        yTOPButton.addActionListener(o->{
            String text = yaml.getText();
            if(!StrUtil.isEmpty(text)){
                String convert = Yaml2Props.fromContent(text).convert();
                properties.setText(convert);
            }
        });
        clearButton.addActionListener(o->{
            properties.setText("");
            yaml.setText("");
        });
    }
}

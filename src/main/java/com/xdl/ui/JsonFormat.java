package com.xdl.ui;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.Getter;

import javax.swing.*;
import java.util.HashMap;


/**
 * @author Bx_Hu
 */
public class JsonFormat {

    @Getter
    private JTextArea formatJson;
    @Getter
    private JTextArea json;
    private JButton formatButton;
    private JButton clearButton;
    private JPanel jsonFormatPanel;
    private JButton compressButton;
    private JScrollPane jsonScrollPane;
    private JScrollPane formatScrollPane;


    public JsonFormat() {

        formatButton.addActionListener(o->{
            String text = json.getText();
            if(!StrUtil.isEmpty(text) && JSONUtil.isTypeJSON(text)){
                formatJson.setText(JSONUtil.formatJsonStr(text));
                //设置光标在开始位置
                formatJson.setSelectionStart(1);
                formatJson.setSelectionEnd(1);

                //设置滚动条在开始位置
                JScrollBar verticalScrollBar = formatScrollPane.getVerticalScrollBar();
                verticalScrollBar.setValue(verticalScrollBar.getMinimum());
            }
        });

        compressButton.addActionListener(o->{
            String text = formatJson.getText();
            if(StrUtil.isEmpty(text) && !JSONUtil.isTypeJSON(text)){
                json.setText("JSON格式错误！");
            }
            if(JSONUtil.isTypeJSONArray(text)){
                JSONArray objects = JSONUtil.parseArray(text);
                json.setText(JSONUtil.toJsonStr(objects.toBean(HashMap.class)));
            }
            if(JSONUtil.isTypeJSONObject(text)){
                JSONObject jsonObject = JSONUtil.parseObj(text);
                json.setText(JSONUtil.toJsonStr(jsonObject.toBean(HashMap.class)));
            }
            //设置光标在开始位置
            json.setSelectionStart(1);
            json.setSelectionEnd(1);

            //设置滚动条在开始位置
            JScrollBar verticalScrollBar = jsonScrollPane.getVerticalScrollBar();
            verticalScrollBar.setValue(verticalScrollBar.getMinimum());
        });
        clearButton.addActionListener(o->{
            formatJson.setText("");
            json.setText("");
        });
    }



}

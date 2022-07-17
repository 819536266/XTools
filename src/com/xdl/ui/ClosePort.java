package com.xdl.ui;

import com.intellij.openapi.ui.Messages;
import com.xdl.util.KillServer;
import lombok.Data;

import javax.swing.*;

@Data
public class ClosePort{

    private JTextField portText;
    private JTextArea portContent;
    private JButton closePostButton;
    private JButton clearPortButton;
    private JPanel closePort;

    private final KillServer killServer = new KillServer();


    public ClosePort() {
        String message = "请确定是否关闭端口!!";
        //执行关闭端口
        closePostButton.addActionListener(e -> {
            int i = Messages.showOkCancelDialog(message, "关闭端口", "确认关闭", "取消", null);
            if (0 == i) killServer.kill(portText.getText(), portContent);
        });

        //清空关闭端口回执
        clearPortButton.addActionListener(e -> portContent.setText(""));
    }
}

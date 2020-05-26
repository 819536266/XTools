package com.xdl.ui;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.Notifications;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.wm.ToolWindow;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * @author huboxin
 * @title: XHttpUi
 * @projectName XHttp
 * @description:
 * @date 2020/5/2613:36
 */
@Data
public class XHttpUi {
    private JPanel parentPanel;
    private JTabbedPane tabbedPane1;
    private JTabbedPane tabbedPane2;
    private JTable table2;
    private JTabbedPane tabbedPane3;
    private JTable table1;
    private JPanel debugPanel;
    private JPanel requestHeader;
    private JPanel parameter;
    private JPanel tt;
    private JTextField path;
    private JButton send;
    private JPanel formData;
    private JPanel formTable;
    private JPanel result;
    private JPanel json;
    private JPanel raw;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JTextArea textArea3;



}

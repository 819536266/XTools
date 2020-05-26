package com.xdl.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import lombok.Data;

import javax.swing.*;
import java.awt.*;

/**
 * @author huboxin
 * @title: XHttpUi
 * @projectName XHttp
 * @description:
 * @date 2020/5/2613:36
 */
public class XHttpUi extends Component {
    private JTabbedPane tabbedPane1;
    private JTabbedPane tabbedPane2;
    private JTable table2;
    private JTabbedPane tabbedPane3;
    private JPanel parentJPanel;


    public XHttpUi(Project project, ToolWindow toolWindow) {

    }

    public void createUIComponents() {
        // TODO: place custom component creation code here
    }



    public JPanel getParentJPanel() {
        return parentJPanel;
    }


}

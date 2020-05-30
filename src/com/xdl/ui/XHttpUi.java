package com.xdl.ui;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.xdl.model.XHttpModel;
import com.xdl.model.XHttpParam;
import com.xdl.util.Icons;
import com.xdl.util.SpringUtils;
import lombok.Data;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.util.List;
import java.util.Map;
import java.util.Vector;

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
    private JTabbedPane request;
    private JTable headerTable;
    private JTabbedPane tabbedPane3;
    private JTable paramTable;
    private JPanel debugPanel;
    private JPanel requestHeader;
    private JPanel parameter;
    private JPanel tt;
    private JTextField path;
    private JButton send;
    private JPanel formData;
    private JPanel formTable;
    private JPanel json;
    private JPanel raw;
    private JTextArea responseContent;
    private JTextArea textArea2;
    private JTextArea textArea3;
    private JButton closeButton;
    private JTextField pathPrefix;
    private JButton emptyResponseButton;
    private JButton emptyButton;
    private JLabel methodType;

    public static XHttpModel xHttpModel;

    public static ToolWindow toolWindow;

    public static XHttpUi xHttpUi;

    private static final String[] paramTitle = {"选中", "参数名称", "类型", "参数值"};
    private static final String[] headerTitle = {"选中", "请求头", "内容"};

    private static DefaultTableModel paramTableModel = new DefaultTableModel(null, paramTitle);
    private static  DefaultTableModel headerTableModel = new DefaultTableModel(null, headerTitle);

    private static  Map<String,String> herder= CollUtil.newHashMap(1);

    public XHttpUi() {


    }

    private void init() {
        //返回数据 自动换行
        responseContent.setLineWrap(true);
        responseContent.setWrapStyleWord(true);
        //设置参数表头
        paramTable.setModel(paramTableModel);
        paramTable.setEnabled(true);

        headerTable.setModel(headerTableModel);
        headerTable.setEnabled(true);
    }

    public XHttpUi(Project project, ToolWindow toolWindow) {
        init();
        XHttpUi.toolWindow = toolWindow;
        XHttpUi.xHttpUi = this;

        send.addActionListener(e -> {
            System.out.println("开始发送请求");
            //重新封装参数
            Vector dataVector1 = headerTableModel.getDataVector();
            xHttpModel.getHeader()
                    .clear();
            Map<String, String> map = xHttpModel.getHeader();
            map.clear();
            List<String[]> strings1 = JSONUtil.parseArray(JSONUtil.toJsonStr(dataVector1))
                    .toList(String[].class);
            for (String[] o : strings1) {
                map.put(o[1], o[2]);
            }
            Vector dataVector = paramTableModel.getDataVector();
            List<XHttpParam> paramList = xHttpModel.getParamList();
            paramList.clear();
            List<String[]> strings = JSONUtil.parseArray(JSONUtil.toJsonStr(dataVector))
                    .toList(String[].class);
            for (String[] o : strings) {
                XHttpParam xHttpParam = new XHttpParam();
                xHttpParam.setIsCheck(Boolean.valueOf(o[0]));
                xHttpParam.setName(o[1]);
                xHttpParam.setValue(o[3]);
                paramList.add(xHttpParam);
            }
            HttpRequest request = HttpUtil.createRequest(xHttpModel.getMethodType(), SpringUtils.restful(path.getText(), paramList));
            request.setReadTimeout(2000).timeout(2000);
            Map<String, String> header = xHttpModel.getHeader();
            header.forEach(request::header);
            paramList.forEach(xHttpParam -> {
                if (xHttpParam.getIsCheck()) request.form(xHttpParam.getName(), xHttpParam.getValue());
            });
            HttpResponse execute = null;
            try {
                System.out.println("开始发请求");
                execute = request.execute(true);
            } catch (Exception e1) {
                responseContent.setText("请求超时!!!!!");
                System.out.println("请求超时!!!!!");
            }
            responseContent.setText(execute.body());
        });

        closeButton.addActionListener(e -> toolWindow.hide(null));


        emptyButton.addActionListener(e -> {
            path.setText("");
            paramTableModel.setDataVector(null, paramTitle);
            //设置参数 第一列为复选框
            TableColumn column = paramTable.getColumnModel()
                    .getColumn(0);
            column.setCellEditor(paramTable.getDefaultEditor(Boolean.class));
            column.setCellRenderer(paramTable.getDefaultRenderer(Boolean.class));
        });
        emptyResponseButton.addActionListener(e -> responseContent.setText(""));
    }


    public void open() {
        //打开列表重置表信息
        paramTableModel.setDataVector(null, paramTitle);
        headerTableModel.setDataVector(null, headerTitle);
        //设置参数 第一列为复选框
        TableColumn column = paramTable.getColumnModel()
                .getColumn(0);
//        EditorComboBox editorComboBox = new EditorComboBox("文本");
//        editorComboBox.addItem("文件");
//        editorComboBox.addActionListener(e -> {
//            System.out.println(editorComboBox.getSelectedItem());
//        });
//        TableColumn column2 = paramTable.getColumnModel()
//                .getColumn(2);
//        column2.setCellEditor(new DefaultCellEditor(editorComboBox));
        column.setCellRenderer(paramTable.getDefaultRenderer(List.class));
        column.setCellEditor(paramTable.getDefaultEditor(Boolean.class));
        column.setCellRenderer(paramTable.getDefaultRenderer(Boolean.class));
        TableColumn column1 = headerTable.getColumnModel()
                .getColumn(0);
        column1.setCellEditor(paramTable.getDefaultEditor(Boolean.class));
        column1.setCellRenderer(paramTable.getDefaultRenderer(Boolean.class));

        //添加对应参数
        xHttpModel.getParamList()
                .forEach(xHttpParam -> {
                    paramTableModel.addRow(new Object[]{xHttpParam.getIsCheck(), xHttpParam.getName(), "文本", xHttpParam.getValue()});
                });
        if(ObjectUtil.isNotEmpty(herder.get("token"))) herder.put("token","");
        herder.forEach((k,v)->{
            headerTableModel.addRow(new Object[]{true,k,v});
        });
        path.setText(pathPrefix.getText() + xHttpModel.getPath());
        methodType.setIcon(Icons.getMethodIcon(xHttpModel.getMethodType()));
        //打开
        XHttpUi.toolWindow.show(null);
    }


}

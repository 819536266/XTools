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
import com.xdl.util.XHttpButtonCellEditor;
import lombok.Data;
import org.jdesktop.swingx.JXComboBox;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import java.util.List;
import java.util.Map;

/**
 * @author huboxin
 * @title: XHttpUi
 * @projectName XHttp
 * @description:
 * @date 2020/5/2613:36
 */
@Data
public class XHttpUi2 {


    private JPanel parentPanel;
    private JTabbedPane tabbedPane1;
    private JTable headerTable;
    private JTable paramTable;
    private JPanel debugPanel;
    private JPanel tt;
    private JTextField path;
    private JButton send;
    private JTextArea responseContent;
    private JButton closeButton;
    private JTextField pathPrefix;
    private JButton emptyResponseButton;
    private JButton emptyButton;
    private JLabel methodType;
    private JTextField postText;
    private JButton closePostButton;
    private JPanel postPanel;

    public static XHttpModel xHttpModel;

    public static ToolWindow toolWindow;

    public static XHttpUi2 xHttpUi;

    //路径 Document
    public static Document pathDocument=new DefaultStyledDocument();

    //路径 Document
    public static Icon methodTypeIcon=Icons.load("/icons/x.png");

    private static final String[] paramTitle = {"选中", "参数名称", "类型", "参数值","操作"};
    private static final String[] headerTitle = {"选中", "请求头", "内容"};

    private static DefaultTableModel paramTableModel = new DefaultTableModel(null, paramTitle);
    private static DefaultTableModel headerTableModel = new DefaultTableModel(null, headerTitle);

    private static Map<String, String> herder = CollUtil.newHashMap(1);

    private static final  String FILE_TYPE="文件";
    private static final  String TEXT_TYPE="文本";

    public XHttpUi2() {}

    /**
     * 初始化
     */
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

    /**
     * 构造方法
     *
     * @param project    project
     * @param toolWindow toolWindow
     */
    public XHttpUi2(Project project, ToolWindow toolWindow) {
        init();
        XHttpUi2.toolWindow = toolWindow;
        XHttpUi2.xHttpUi = this;
        //发送请求监听事件
        send.addActionListener(e -> sendHttp());
        //关闭窗口
        closeButton.addActionListener(e -> toolWindow.hide(null));

        //清空返回值
        emptyResponseButton.addActionListener(e -> responseContent.setText(""));

        //清空全部
        emptyButton.addActionListener(e -> {
            path.setText("");
            paramTableModel.setDataVector(null, paramTitle);
            //设置参数 第一列为复选框
            TableColumn column = paramTable.getColumnModel()
                    .getColumn(0);
            column.setCellEditor(paramTable.getDefaultEditor(Boolean.class));
            column.setCellRenderer(paramTable.getDefaultRenderer(Boolean.class));
        });
        closePostButton.addActionListener(e -> {
         /*   URL resource = XHttpUi.class.getResource("close.bat");
            String s = RuntimeUtil.execForStr("set port=" + postText.getText() + "\n" +
                            "for /f \"tokens=1-5\" %%i in ('netstat -ano^|findstr \":%port%\"') do taskkill -f /pid %%m");
            System.out.println(s);*/
        });
    }




    /**
     * 发送请求
     */
    private void sendHttp() {
        //重新封装参数
        xHttpModel.getHeader()
                .clear();
        Map<String, String> map = xHttpModel.getHeader();
        map.clear();
        List<String[]> strings1 = JSONUtil.parseArray(JSONUtil.toJsonStr(headerTableModel.getDataVector()))
                .toList(String[].class);
        for (String[] o : strings1) {
            map.put(o[1], o[2]);
        }
        List<XHttpParam> paramList = xHttpModel.getParamList();
        paramList.clear();
        List<String[]> strings = JSONUtil.parseArray(JSONUtil.toJsonStr(paramTableModel.getDataVector()))
                .toList(String[].class);
        for (String[] o : strings) {
            XHttpParam xHttpParam = new XHttpParam();
            xHttpParam.setIsCheck(Boolean.valueOf(o[0]));
            xHttpParam.setName(o[1]);
            xHttpParam.setValue(o[3]);
            paramList.add(xHttpParam);
        }
        if (ObjectUtil.isEmpty(path.getText())) {
            responseContent.setText("路径错误");
            return;
        }
        HttpRequest request = HttpUtil.createRequest(xHttpModel.getMethodType(), SpringUtils.restful(path.getText(), paramList));

        //设置超时时间,不设置会DeBug时,发请求IDEA会挂掉
        request.setReadTimeout(1000)
                .timeout(1000);
        //设置请求头
        Map<String, String> header = xHttpModel.getHeader();
        header.forEach(request::header);
        //设置选中的参数
        paramList.forEach(xHttpParam -> {
            if (xHttpParam.getIsCheck()) request.form(xHttpParam.getName(), xHttpParam.getValue());
        });
        HttpResponse execute = null;
        try {
            execute = request.execute(true);
        } catch (Exception e1) {
            responseContent.setText("请求超时!!!");
        }
        String body = "";
        if (ObjectUtil.isEmpty(execute)) body = "响应失败!!!"; responseContent.setText(body);
        body = execute.body();
        if (!ObjectUtil.isEmpty(body) && JSONUtil.isJson(body)) body = JSONUtil.formatJsonStr(body);
        responseContent.setText(body);
    }

    public static void get(TableCellEditor tableCellEditor){

    }
    /**
     * 打开窗口
     */
    public void open() {
        //打开列表重置表信息
        paramTableModel.setDataVector(null, paramTitle);
        headerTableModel.setDataVector(null, headerTitle);
        //设置参数 第一列为复选框
        TableColumn column = paramTable.getColumnModel()
                .getColumn(0);
        JComboBox editorComboBox = new JXComboBox(new String[]{FILE_TYPE,TEXT_TYPE});
        editorComboBox.addActionListener(e -> {
           int selectedRow = paramTable.getSelectedRow();
           TableColumn column2 = paramTable.getColumnModel()
                   .getColumn(4);
//           column2.setCellEditor(new XHttpButtonCellEditor(selectedRow, FILE_TYPE, false, project));
        });
        TableColumn column2 = paramTable.getColumnModel()
                .getColumn(2);
        column2.setCellEditor(new DefaultCellEditor(editorComboBox));
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
        if (ObjectUtil.isEmpty(herder.get("token"))) herder.put("token", "");
        //设置请求头
        herder.forEach((k, v) -> {
            headerTableModel.addRow(new Object[]{true, k, v});
        });
        //设置路径 ,不使用 path.setText(localPath); 多个窗口会出现设置不统一
        try {
            ((AbstractDocument)pathDocument).replace(0, pathDocument.getLength(), pathPrefix.getText() + xHttpModel.getPath(),null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        path.setDocument(pathDocument);
        //设置图标
        methodTypeIcon = Icons.getMethodIcon(xHttpModel.getMethodType());
        methodType.setIcon(methodTypeIcon);
        //打开
        XHttpUi2.toolWindow.show(null);
    }





}

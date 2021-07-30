package com.xdl.ui;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.content.Content;
import com.xdl.action.XToolsAction;
import com.xdl.enums.ParamTypeEnum;
import com.xdl.model.Settings;
import com.xdl.model.SpringRequestMethodAnnotation;
import com.xdl.model.XHttpModel;
import com.xdl.model.XHttpParam;
import com.xdl.util.Icons;
import com.xdl.util.SpringUtils;
import com.xdl.util.XHttpButtonCellEditor;
import lombok.Data;
import org.jdesktop.swingx.JXComboBox;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import java.awt.event.ActionEvent;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * @author huboxin
 * @date 2020/5/2613:36
 */
@Data
public class XHttpUi {


    private JTable headerTable;
    private JTabbedPane tabbedPane3;
    private JTable paramTable;
    private JTextField path;
    private JButton send;
    private JTextArea responseContent;
    private JButton closeButton;
    private JTextField pathPrefix;
    private JButton emptyResponseButton;
    private JButton emptyButton;
    private JLabel methodType;

    private JTextArea jsonBody;
    private JTable jsonParamTable;
    private JButton deleteHeaderButton;
    private JButton addHeaderButton;
    private JTextArea headerContent;
    private JTextArea urlContent;
    private JTextArea rowContent;
    private JTable rowParamTable;
    private JSplitPane paramPane;
    private JPanel parentPanel;
    private JPanel debugPanel;
    private JTabbedPane tabbedPane4;
    private JTabbedPane request;
    private JPanel parameter;
    private JPanel requestHeader;
    private JPanel formData;
    private JPanel rowPanel;
    private JScrollPane jsonParamScroll;
    private JPanel json;
    private JPanel formTable;

    private JButton formAddButton;
    private JButton formDeleteButton;
    private JButton formAddButton1;
    private JButton formDeleteButton1;

    private JScrollPane parentJScroll;

    public XHttpModel xHttpModel;

    public ToolWindow toolWindow;

    public Project project;

    public XHttpParam xHttpParamBody;

    public Document pathDocument = new DefaultStyledDocument();

    public Icon methodTypeIcon = Icons.load("/icons/x.png");

    private static final String[] PARAM_TITLE = {"选中", "参数名称", "类型", "参数值", "操作"};
    private static final String[] HEADER_TITLE = {"选中", "请求头", "内容"};

    public DefaultTableModel paramTableModel = new DefaultTableModel(null, PARAM_TITLE);
    public DefaultTableModel headerTableModel = new DefaultTableModel(null, HEADER_TITLE);

    private Map<String, String> herder = CollUtil.newHashMap(1);


    /**
     * 初始化
     */
    private void init() {

        //设置参数表头
        paramTable.setModel(paramTableModel);
        paramTable.setEnabled(true);

        //设置参数表头
        jsonParamTable.setModel(paramTableModel);
        jsonParamTable.setEnabled(true);
        //设置参数表头
        rowParamTable.setModel(paramTableModel);
        rowParamTable.setEnabled(true);

        setCheckBox();

        headerTable.setModel(headerTableModel);
        headerTable.setEnabled(true);
        pathPrefix.setText(Settings.getInstance()
                .getDoMain());
    }


    /**
     * 构造方法
     *
     * @param project project
     */
    public XHttpUi(Project project) {

        init();
        toolWindow = XToolsAction.ToolWindows.get(project);
        this.project = project;
        //发送请求监听事件
        send.addActionListener(e -> sendHttp());

        ///切换窗口模式
        closeButton.addActionListener(e -> {
            if (paramPane.getOrientation() == JSplitPane.VERTICAL_SPLIT) {
                paramPane.setDividerLocation(300);
                paramPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
            } else {
                paramPane.setDividerLocation(280);
                paramPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
            }
        });
        //清空返回值
        emptyResponseButton.addActionListener(e -> {
            rowContent.setText("");
            urlContent.setText("");
            headerContent.setText("");
            responseContent.setText("");
        });

        //清空全部
        emptyButton.addActionListener(e -> {
            path.setText("");
            paramTableModel.setDataVector(null, PARAM_TITLE);
            setCheckBox();
            XToolsAction.modelMap.remove(ObjectUtil.isEmpty(xHttpModel) ? "" : xHttpModel.getKey());
        });

        //添加请求头
        addHeaderButton.addActionListener(e -> headerTableModel.addRow(new Object[]{true, "", ""}));
        //删除请求头
        deleteHeaderButton.addActionListener(this::actionPerformed);

        //添加参数
//        formAddButton.addActionListener(e-> paramTableModel.addRow(new Object[]{true, "", ""}));
//        formAddButton1.addActionListener(e-> paramTableModel.addRow(new Object[]{true, "", ""}));
        //删除删除
//        formDeleteButton.addActionListener(this::deleteParamRow);
//        formDeleteButton1.addActionListener(this::deleteParamRow1);
    }



    /**
     * 发送请求
     */
    private void sendHttp() {
        if (ObjectUtil.isEmpty(path) || (ObjectUtil.isEmpty(headerTableModel) && ObjectUtil.isEmpty(paramTableModel)) || ObjectUtil.isEmpty(xHttpModel)) {
            responseContent.setText("请输入正确的请求");
            return;
        }
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

        if (!ObjectUtil.isEmpty(xHttpParamBody)) paramList.add(xHttpParamBody);
        Vector<Vector> vector = paramTableModel.getDataVector();
        HashSet<ParamTypeEnum> resType = CollUtil.newHashSet();
        for (Vector param : vector) {
            XHttpParam xHttpParam = new XHttpParam();
            xHttpParam.setIsCheck((Boolean) param.get(0));
            xHttpParam.setName((String) param.get(1));
            ParamTypeEnum paramTypeEnum = ParamTypeEnum.getParamTypeEnum(param.get(2));
            if (ParamTypeEnum.FILE.equals(paramTypeEnum)) {
                resType.add(ParamTypeEnum.FILE);
            }
            xHttpParam.setParamTypeEnum(paramTypeEnum);
            xHttpParam.setValue(param.get(3));
            paramList.add(xHttpParam);
        }
        if (ObjectUtil.isEmpty(path.getText())) {
            responseContent.setText("路径错误");
            return;
        }

        String contentPath = StrUtil.addSuffixIfNot(StrUtil.stripIgnoreCase(pathPrefix.getText(), "/", "/"), "/") + StrUtil.removePrefix(path.getText(), "/");
        String restful = SpringUtils.restful(contentPath, paramList);
        HttpRequest request = HttpUtil.createRequest(xHttpModel.getMethodType()
                , restful);

        //设置超时时间,不设置会DeBug时,发请求IDEA会挂掉
        request.setReadTimeout(1000)
                .timeout(1000);
        //设置请求头
        Map<String, String> header = xHttpModel.getHeader();
        header.forEach(request::header);
        //设置body
        paramList.forEach(xHttpParam -> {
            if (ParamTypeEnum.BODY.equals(xHttpParam.getParamTypeEnum())) {
                xHttpParam.setValue(jsonBody.getText());
                resType.add(ParamTypeEnum.BODY);
                request.body((String) xHttpParam.getValue());
            }
        });
        //设置选中的参数
        paramList.forEach(xHttpParam -> {
            //未选中
            if (!xHttpParam.getIsCheck()) return;

            //restful风格
            if (contentPath.contains(StrUtil.DELIM_START+ xHttpParam.getName() +StrUtil.DELIM_END)) return;

            //body参数
            if (ParamTypeEnum.BODY.equals(xHttpParam.getParamTypeEnum())) return;

            //body类型请求,拼接参数到url
            if (resType.contains(ParamTypeEnum.BODY) && !ParamTypeEnum.FILE.equals(xHttpParam.getParamTypeEnum())) {
                Map<String, Object> hashMap = MapUtil.of(xHttpParam.getName(), xHttpParam.getValue());
                String toParams = HttpUtil.toParams(hashMap, StandardCharsets.UTF_8);
                String urlWithForm = HttpUtil.urlWithForm(request.getUrl(), toParams, StandardCharsets.UTF_8, false);
                request.setUrl(urlWithForm);
                return;
            }

            //form请求
            if (xHttpParam.getValue() instanceof List || xHttpParam.getValue() instanceof Array) {
                List<Object> paramValues = (List<Object>) xHttpParam.getValue();
                paramValues.forEach(paramValue -> request.form(xHttpParam.getName(), paramValue));
            } else {
                request.form(xHttpParam.getName(), xHttpParam.getValue());
            }
        });
        //设置文件上传类型
        request.contentType(resType.contains(ParamTypeEnum.BODY) ?
                ContentType.JSON.toString() : (resType.contains(ParamTypeEnum.FILE) ?
                ContentType.MULTIPART.toString() : ContentType.FORM_URLENCODED.toString()));
        urlContent.setText(request.getUrl());
        headerContent.setText(JSONUtil.formatJsonStr(JSONUtil.toJsonStr(request.headers())));

        HttpResponse execute = null;
        try {
            execute = request.execute(true);
        } catch (Exception e1) {
            responseContent.setText("请求超时!!!");
        }
        String body = "";
        if (execute == null) {
            body = "响应失败!!!";
            rowContent.setText(body);
            responseContent.setText(body);
            return;
        }
        body = execute.body();
        rowContent.setText(body);
        if (!ObjectUtil.isEmpty(body) && JSONUtil.isJson(body)) body = JSONUtil.formatJsonStr(body);
        responseContent.setText(body);
    }


    /**
     * 打开窗口
     */
    public void open(XHttpModel xHttpModel) {
        this.xHttpModel = xHttpModel;
        //打开列表重置表信息
        paramTableModel.setDataVector(null, PARAM_TITLE);
        headerTableModel.setDataVector(null, HEADER_TITLE);
        setParamTableStyle(paramTable);
        setParamTableStyle(jsonParamTable);
        setParamTableStyle(rowParamTable);

        TableColumn column1 = headerTable.getColumnModel()
                .getColumn(0);
        column1.setCellEditor(headerTable.getDefaultEditor(Boolean.class));
        column1.setCellRenderer(headerTable.getDefaultRenderer(Boolean.class));

        jsonBody.setText("no body!");
        //添加对应参数
        int windowType = 0;
        for (XHttpParam xHttpParam : xHttpModel.getParamList()) {
            if (!ParamTypeEnum.BODY.equals(xHttpParam.getParamTypeEnum())) {
                paramTableModel.addRow(new Object[]{xHttpParam.getIsCheck(), xHttpParam.getName(), xHttpParam.getParamTypeEnum().getName(), xHttpParam.getValue()});
            } else {
                windowType = 2;
                jsonBody.setText(xHttpModel.getRequestBody());
                xHttpParamBody = xHttpParam;
            }
        }

        Map<String, String> header = xHttpModel.getHeader();
        //设置请求头
        header.forEach((k, v) -> headerTableModel.addRow(new Object[]{true, k, v}));
        //设置路径 ,不使用 path.setText(localPath); 多个窗口会出现设置不统一
        try {
            ((AbstractDocument) pathDocument).replace(0, pathDocument.getLength()
                    , xHttpModel.getPath(), null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        path.setDocument(pathDocument);
        //设置图标
        methodTypeIcon = Icons.getMethodIcon(xHttpModel.getMethodType());
        methodType.setIcon(methodTypeIcon);
        tabbedPane3.setSelectedIndex(windowType == 2 ? 2 : SpringRequestMethodAnnotation.POST_MAPPING.getMethod()
                .equals(xHttpModel.getMethodType()) ? 1 : 0);
        //打开
        toolWindow.show(null);

    }

    /**
     * 打开对应窗口
     */
    public void openParent() {
        toolWindow.show(null);
        Content content = toolWindow.getContentManager()
                .getContent(0);
        if (content != null) {
            toolWindow.getContentManager()
                    .setSelectedContent(content);
        }
    }


    /**
     * 封装table值
     *
     * @param paramTable JTable
     */
    public void setParamTableStyle(JTable paramTable) {
        if (paramTable.getColumnModel()
                .getColumnCount() < 5) {
            return;
        }
        JXComboBox editorComboBox = new JXComboBox(new String[]{ParamTypeEnum.TEXT.getName(), ParamTypeEnum.FILE.getName()});
        editorComboBox.addPropertyChangeListener(e -> {
            TableColumn column4 = paramTable.getColumnModel()
                    .getColumn(4);
            column4.setCellEditor(new XHttpButtonCellEditor(this));
        });
        //设置三列为下拉
        TableColumn column2 = paramTable.getColumnModel()
                .getColumn(2);
        column2.setCellEditor(new DefaultCellEditor(editorComboBox));
        //第四列不可编辑
        TableColumn column4 = paramTable.getColumnModel()
                .getColumn(4);
        column4.setCellEditor(new XHttpButtonCellEditor(this));
        //设置参数 第一列为复选框
        TableColumn column = paramTable.getColumnModel()
                .getColumn(0);
        column.setCellRenderer(paramTable.getDefaultRenderer(List.class));
        column.setCellEditor(paramTable.getDefaultEditor(Boolean.class));
        column.setCellRenderer(paramTable.getDefaultRenderer(Boolean.class));

    }


    private void actionPerformed(ActionEvent e) {
        try {
            int selectedRow = headerTable.getSelectedRow();
            headerTableModel.removeRow(selectedRow);
        } catch (Exception ignored) {
        }
    }

    private void deleteParamRow(ActionEvent e) {
        try {
            int selectedRow = paramTable.getSelectedRow();
            paramTableModel.removeRow(selectedRow);
        } catch (Exception ignored) {
        }
    }

    private void deleteParamRow1(ActionEvent e) {
        try {
            int selectedRow = jsonParamTable.getSelectedRow();
            paramTableModel.removeRow(selectedRow);
        } catch (Exception ignored) {
        }
    }

    /**
     * 设置复选框
     */
    private void setCheckBox() {
        //设置参数 第一列为复选框
        TableColumn column = paramTable.getColumnModel()
                .getColumn(0);
        column.setCellEditor(paramTable.getDefaultEditor(Boolean.class));
        column.setCellRenderer(paramTable.getDefaultRenderer(Boolean.class));

        //设置参数 第一列为复选框
        TableColumn column1 = jsonParamTable.getColumnModel()
                .getColumn(0);
        column1.setCellEditor(jsonParamTable.getDefaultEditor(Boolean.class));
        column1.setCellRenderer(jsonParamTable.getDefaultRenderer(Boolean.class));
        //设置参数 第一列为复选框
        TableColumn column2 = rowParamTable.getColumnModel()
                .getColumn(0);
        column2.setCellEditor(rowParamTable.getDefaultEditor(Boolean.class));
        column2.setCellRenderer(rowParamTable.getDefaultRenderer(Boolean.class));
    }

}

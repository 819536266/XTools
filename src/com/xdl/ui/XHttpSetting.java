package com.xdl.ui;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.xdl.action.XHttpAction;
import com.xdl.model.Settings;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author huboxin
 * @title: XHttpSetting
 * @projectName XHttp
 * @description:
 * @date 2020/5/3017:02
 */
public class XHttpSetting implements Configurable, Configurable.Composite {

    private JPanel mainPanel;
    private JTextField doMain;
    private JButton addExcludeButton;
    private JButton deleteExcludeButton;
    private JTable excludeTable;
    private JButton restartButton;
    private JButton clear;

    private static final DefaultTableModel defaultTableModel = new DefaultTableModel(null, new String[]{"排除参数类路径"});


    /**
     * 设置对象
     */
    private final Settings settings = Settings.getInstance();

    public XHttpSetting() {
        doMain.setText(settings.getDoMain());
        String[] exclude = settings.getExclude();
        defaultTableModel.setDataVector(null, new String[]{"排除参数类路径"});
        Arrays.stream(exclude)
                .forEach(e -> defaultTableModel.addRow(new String[]{e}));
        excludeTable.setModel(defaultTableModel);
        excludeTable.setEnabled(true);
        //添加一行
        addExcludeButton.addActionListener(e -> defaultTableModel.addRow(new String[]{""}));
        //删除一行
        deleteExcludeButton.addActionListener(e -> {
            int selectedRow = excludeTable.getSelectedRow();
            defaultTableModel.removeRow(selectedRow);
        });
        //重置
        restartButton.addActionListener(e -> {
            settings.restart();
            doMain.setText(settings.getDoMain());
            defaultTableModel.setDataVector(null, new String[]{"排除参数类路径"});
            Arrays.stream(settings.getExclude())
                    .forEach(e1 -> defaultTableModel.addRow(new String[]{e1}));
        });
        //清除全部缓存
        clear.addActionListener(e -> XHttpAction.modelMap.clear());
    }

    /**
     * 设置显示名称
     *
     * @return 显示名称
     */
    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "XHttp";
    }

    /**
     * 获取主面板信息
     *
     * @return 主面板
     */
    @Nullable
    @Override
    public JComponent createComponent() {
        return mainPanel;
    }

    /**
     * 判断是否修改
     *
     * @return 是否修改
     */
    @Override
    public boolean isModified() {
        Vector<Vector> vector = defaultTableModel.getDataVector();
        Set<Object> collect = vector.stream()
                .map(vector1 -> vector1.get(0))
                .collect(Collectors.toSet());
        int size = collect.size();
        Arrays.stream(settings.getExclude())
                .forEach(e -> collect.add(e));
        return !settings.getDoMain()
                .equals(doMain.getText()) || collect.size() != size;
    }


    /**
     * 应用修改
     */
    @Override
    public void apply() {
        settings.setDoMain(doMain.getText());
        List<String[]> strings1 = JSONUtil.parseArray(JSONUtil.toJsonStr(defaultTableModel.getDataVector()))
                .toList(String[].class);
        String[] string = new String[strings1.size()];
        for (int i = 0; i < strings1.size(); i++) {
            string[i] = strings1.get(i)[0];
        }
        settings.setExclude(string);
    }

    /**
     * 更多配置
     *
     * @return 配置选项
     */
    @NotNull
    @Override
    public Configurable[] getConfigurables() {
        //"exclude";
        return new Configurable[0];
    }
}

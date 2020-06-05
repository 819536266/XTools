package com.xdl.ui;

import cn.hutool.json.JSONUtil;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.xdl.model.Settings;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    private static DefaultTableModel defaultTableModel=new DefaultTableModel(null,new String[]{"排除参数类路径"});


    /**
     * 设置对象
     */
    private final Settings settings = Settings.getInstance();

    public XHttpSetting() {
        doMain.setText(settings.getDoMain());
        String[] exclude = settings.getExclude();
        defaultTableModel.setDataVector(null,new String[]{"排除参数类路径"});
        Arrays.stream(exclude).forEach(e->defaultTableModel.addRow(new String[]{e}));
        excludeTable.setModel(defaultTableModel);
        excludeTable.setEnabled(true);
        addExcludeButton.addActionListener(e->{
            defaultTableModel.addRow(new String[]{""});
        });
        deleteExcludeButton.addActionListener(e->{
            int selectedRow = excludeTable.getSelectedRow();
            defaultTableModel.removeRow(selectedRow);
        });
        restartButton.addActionListener(e->settings.restart());
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
        return true;
    }


    /**
     * 应用修改
     */
    @Override
    public void apply()  {
        settings.setDoMain(doMain.getText());
        List<String[]> strings1 = JSONUtil.parseArray(JSONUtil.toJsonStr(defaultTableModel.getDataVector()))
                .toList(String[].class);
        String[] string=new String[strings1.size()];
        for (int i = 0; i < strings1.size(); i++) {
            string[i]=strings1.get(i)[0];
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

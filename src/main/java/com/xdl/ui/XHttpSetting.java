package com.xdl.ui;

import cn.hutool.json.JSONUtil;
import com.intellij.openapi.options.Configurable;
import com.xdl.action.XToolsAction;
import com.xdl.model.Settings;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author huboxin
 * @date 2020/5/3017:02
 */
public class XHttpSetting implements Configurable, Configurable.Composite {

    private JPanel mainPanel;
    private JTextField doMain;
    private JButton addExcludeButton;
    private JButton deleteExcludeButton;
    private JTable excludeTable;
    private JButton restartButton;
    private JButton clearCacheButton;
    private JCheckBox cacheCheckBox;

    private static final DefaultTableModel DEFAULT_TABLE_MODEL = new DefaultTableModel(null, new String[]{"排除参数类路径"});


    /**
     * 设置对象
     */
    private final Settings settings = Settings.getInstance();

    public XHttpSetting() {
        doMain.setText(settings.getDoMain());
        String[] exclude = settings.getExclude();
        cacheCheckBox.setSelected(settings.getOrCache());

        DEFAULT_TABLE_MODEL.setDataVector(null, new String[]{"排除参数类路径"});
        Arrays.stream(exclude)
                .forEach(e -> DEFAULT_TABLE_MODEL.addRow(new String[]{e}));
        excludeTable.setModel(DEFAULT_TABLE_MODEL);
        excludeTable.setEnabled(true);
        //添加一行
        addExcludeButton.addActionListener(e -> DEFAULT_TABLE_MODEL.addRow(new String[]{""}));
        //删除一行
        deleteExcludeButton.addActionListener(e -> {
            int selectedRow = excludeTable.getSelectedRow();
            DEFAULT_TABLE_MODEL.removeRow(selectedRow);
        });
        //重置
        restartButton.addActionListener(e -> {
            settings.restart();
            doMain.setText(settings.getDoMain());
            DEFAULT_TABLE_MODEL.setDataVector(null, new String[]{"排除参数类路径"});
            Arrays.stream(settings.getExclude())
                    .forEach(e1 -> DEFAULT_TABLE_MODEL.addRow(new String[]{e1}));
        });
        //清除全部缓存
        clearCacheButton.addActionListener(e -> XToolsAction.modelMap.clear());

    }

    /**
     * 设置显示名称
     *
     * @return 显示名称
     */
    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "XTools";
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
        Vector<Vector> vector = DEFAULT_TABLE_MODEL.getDataVector();
        Set<Object> collect = vector.stream()
                .map(vector1 -> vector1.get(0))
                .collect(Collectors.toSet());
        int size = collect.size();
        collect.addAll(Arrays.asList(settings.getExclude()));

        return !settings.getDoMain()
                .equals(doMain.getText()) || collect.size() != size || !settings.getOrCache()
                .equals(cacheCheckBox.isSelected());
    }


    /**
     * 应用修改
     */
    @Override
    public void apply() {
        //设置默认域名
        settings.setDoMain(doMain.getText());

        List<String[]> strings1 = JSONUtil.parseArray(JSONUtil.toJsonStr(DEFAULT_TABLE_MODEL.getDataVector()))
                .toList(String[].class);
        String[] string = new String[strings1.size()];
        for (int i = 0; i < strings1.size(); i++) {
            string[i] = strings1.get(i)[0];
        }
        //设置排除的headers
        settings.setExclude(string);
        //设置是否缓存
        settings.setOrCache(cacheCheckBox.isSelected());
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

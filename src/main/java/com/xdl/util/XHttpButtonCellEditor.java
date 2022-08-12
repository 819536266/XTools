package com.xdl.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.vfs.VirtualFile;
import com.xdl.enums.ParamTypeEnum;
import com.xdl.ui.XHttpUi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author huboxin
 * @date 2020/6/117:31
 */
public class XHttpButtonCellEditor  extends AbstractCellEditor implements TableCellEditor{


    private final JTextField textField=new JTextField();

    private final XHttpUi xHttpUi;

    public XHttpButtonCellEditor(XHttpUi xHttpUi) {
        this.xHttpUi = xHttpUi;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        textField.setEditable(false);
        DefaultTableModel paramTableModel = xHttpUi.paramTableModel;
        List<Object[]> objects =  JSONUtil.parseArray(JSONUtil.toJsonStr(paramTableModel.getDataVector()))
                .toList(Object[].class);
        if( ParamTypeEnum.FILE.equals(ParamTypeEnum.getParamTypeEnum(objects.get(row)[2]))){
            JButton jButton=new JButton("选择文件");
            jButton.addActionListener(e->{
                //idea文件选择器
                VirtualFile[]  virtualFiles = FileChooser.chooseFiles(FileChooserDescriptorFactory.createMultipleFilesNoJarsDescriptor(), xHttpUi.project, xHttpUi.project.getWorkspaceFile());
                List<File> collect = Arrays.stream(virtualFiles)
                        .map(virtualFile -> FileUtil.file(virtualFile.getPath()))
                        .collect(Collectors.toList());
                xHttpUi.paramTableModel.setValueAt(ObjectUtil.isEmpty(collect)?"":collect,row,column-1);
            });
            return jButton;
        }else{
            return textField;
        }
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }


}

package com.jiangli.jni.app.richman9.smallgame;

import com.jiangli.common.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ImgPathButtonEditor extends DefaultCellEditor {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -6546334664166791132L;

    private JPanel panel;

    private JButton button;
    private String imgPath;

    public ImgPathButtonEditor() {
        // DefautlCellEditor有此构造器，需要传入一个，但这个不会使用到，直接new一个即可。   
        super(new JTextField());

        // 设置点击几次激活编辑。   
        this.setClickCountToStart(1);

        this.initButton();

        this.initPanel();

        // 添加按钮。   
        this.panel.add(this.button);
    }

    private void initButton() {
        this.button = new JButton();

        // 设置按钮的大小及位置。   
        this.button.setBounds(0, 0, 200, 15);

        // 为按钮添加事件。这里只能添加ActionListner事件，Mouse事件无效。   
        this.button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 触发取消编辑的事件，不会调用tableModel的setValue方法。   
                ImgPathButtonEditor.this.fireEditingCanceled();

                // 这里可以做其它操作。   
                // 可以将table传入，通过getSelectedRow,getSelectColumn方法获取到当前选择的行和列及其它操作等。

                FileUtil.openPicture(imgPath);
            }
        });

    }

    private void initPanel() {
        this.panel = new JPanel();

        // panel使用绝对定位，这样button就不会充满整个单元格。   
        this.panel.setLayout(null);
    }


    /**
     * 这里重写父类的编辑方法，返回一个JPanel对象即可（也可以直接返回一个Button对象，但是那样会填充满整个单元格）
     */
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        // 只为按钮赋值即可。也可以作其它操作。   
        this.button.setText(value == null ? "" : String.valueOf(value));
        this.imgPath = String.valueOf(value);
        return this.panel;
    }

    /**
     * 重写编辑单元格时获取的值。如果不重写，这里可能会为按钮设置错误的值。
     */
    @Override
    public Object getCellEditorValue() {
        return this.button.getText();
    }

} 
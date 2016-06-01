package com.jiangli.jni.common.window;

import com.jiangli.jni.common.Config;
import com.jiangli.jni.common.HwndUtil;
import com.jiangli.jni.core.User32;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/1 0001 13:26
 */
public class CaptureWindow extends JFrame {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private JTextField jtfHwnd = new JTextField("460564");
    private JLabel jlbHwnd = new JLabel("句柄");
    private JTextField jtfTitleStr = new JTextField("大富翁9");
    private JLabel jlbTitleStr = new JLabel("标题");
    private JButton btnCapture = new JButton("捕获");
    private JButton btnDeleteAll = new JButton("删除所有");
    private JCheckBox jcbShowCaps = new JCheckBox("捕获后打开");
    private Integer hWnd = null;
    private User32 user32 = User32.INSTANCE;

    public CaptureWindow() throws HeadlessException {
        this.setVisible(true);
        this.setTitle("CaptureWindow");
        this.setSize(400, 300);
        setFrameMiddle();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setDefaultLookAndFeelDecorated(false);

        Container root = getContentPane();

        paintUI(root);

        btnCapture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    rereshHwnd();

                    File file = HwndUtil.shortCut(hWnd, Config.capture_path);
                    if (jcbShowCaps.isSelected()) {
                        Runtime.getRuntime().exec("mspaint \"" + file.getAbsolutePath() + "\"");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(root, ex.getMessage());
                }
            }
        });

        btnDeleteAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                     File file = new File(Config.capture_path);
                    int count = 0;
                    if (file.isDirectory()) {
                        File[] files = file.listFiles();
                        for (File file1 : files) {
                            if (file1.isFile()) {
                                file1.delete();
                                count++;
                            }
                        }
                    }
                    JOptionPane.showMessageDialog(root, "已成功删除"+count+"个");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(root, ex.getMessage());
                }
            }
        });

    }

    private void paintUI(Container root) {
        final JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(2, 2));
        formPanel.add(jlbHwnd);
        formPanel.add(jtfHwnd);
        formPanel.add(jlbTitleStr);
        formPanel.add(jtfTitleStr);

        final JPanel actionPanel = new JPanel();
        actionPanel.add(jcbShowCaps);
        actionPanel.add(btnCapture);
        actionPanel.add(btnDeleteAll);

        root.setLayout(new BorderLayout());
        root.add(formPanel, BorderLayout.CENTER);
        root.add(actionPanel, BorderLayout.PAGE_END);
    }

    public void rereshHwnd() throws Exception {
        hWnd = null;

        String text = jtfHwnd.getText();
        if (!StringUtils.isEmpty(text)) {
            try {
                hWnd = Integer.parseInt(text);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        text = jtfTitleStr.getText();
        if (hWnd == null) {
            hWnd = user32.FindWindowA(null, text);
            if (hWnd != 0) {
                jtfHwnd.setText(hWnd + "");
            } else {
                hWnd = null;
            }
        }

        if (hWnd == null) {
            throw new Exception("找不到句柄");
        }

    }

    public void setFrameMiddle() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }

        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        this.setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);
    }

    public static void main(String[] args) {
        CaptureWindow captureWindow = new CaptureWindow();
    }
}

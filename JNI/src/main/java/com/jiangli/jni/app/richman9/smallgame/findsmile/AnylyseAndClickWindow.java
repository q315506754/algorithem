package com.jiangli.jni.app.richman9.smallgame.findsmile;

import com.jiangli.common.utils.FileUtil;
import com.jiangli.common.utils.PathUtil;
import com.jiangli.common.utils.TimeAnalyser;
import com.jiangli.graphics.common.BMP;
import com.jiangli.graphics.common.Color;
import com.jiangli.graphics.common.Point;
import com.jiangli.graphics.common.Rect;
import com.jiangli.graphics.impl.RmoveDuplicatePointFilter;
import com.jiangli.graphics.inf.BMPMatcher;
import com.jiangli.graphics.inf.PointFilter;
import com.jiangli.jni.app.impl.FindSmileJavaCVThreadMathcer;
import com.jiangli.jni.common.Config;
import com.jiangli.jni.common.DrawUtil;
import com.jiangli.jni.common.HwndUtil;
import com.jiangli.jni.common.Mouse;
import com.jiangli.jni.core.User32;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/1 0001 13:26
 */
public class AnylyseAndClickWindow extends JFrame {
    public static final float SIMILARITY = Config.smileSimilartity;
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private JTextField jtfHwnd = new JTextField(Config.test_hWnd+"");
    private JLabel jlbHwnd = new JLabel("句柄");

    private JTextField jtfTitleStr = new JTextField("大富翁9");
    private JLabel jlbTitleStr = new JLabel("标题");

    private JMenuBar jmbMenuBar = new JMenuBar();
    private JMenu jmnConfigMenu = new JMenu("配置");

    private JTabbedPane tabbedPane = new JTabbedPane();

    private JTextArea jtaConsole = new JTextArea("");
    private JScrollPane jspConsole = new JScrollPane(jtaConsole);


    private JTable jtbFires = new JTable();
    private JScrollPane jspFires = new JScrollPane(jtbFires);
    private String[] columnNames = {"匹配点数目","捕获图","分析图"};


    private JButton btnHwnd = new JButton("句柄程序");
    private JButton btnFire = new JButton("点击笑脸");
    private JButton btnDeleteCapture = new JButton("删除捕获");
    private JButton btnOpenCapture = new JButton("打开捕获");
    private JButton btnDeleteAnalyse = new JButton("删除分析");
    private JButton btnOpenAnalyse = new JButton("打开分析");

    private Integer hWnd = null;
    private User32 user32 = User32.INSTANCE;

//    private SmileAnylyser anylyser = new SmileAnylyser(Config.characteristic_path);
    private BMPMatcher mathcer  = new FindSmileJavaCVThreadMathcer();

    private final boolean capture = true;//不能改为false

    private boolean anylyse = true;
    private boolean any_drawLine = true;
    private boolean any_drawPoints = true;
    private com.jiangli.graphics.common.Color MATCH_COLOR = new Color(0,0,0);
    private Color CLICK_POINT_COLOR = new Color(255,0,0);
    private int CLICK_POINT_LENGTH = 50;
    private int CLICK_INTERVAL = 200;

    private Random seed = new Random();
    private Robot robot;
    private Rect offset = new Rect(580,210,400,400);
    private PointFilter pointFilter = new RmoveDuplicatePointFilter(20);
    private JMenuItem jmiEnableAnalyse = new JCheckBoxMenuItem("开启分析");
    private JMenuItem jmiEnableAnalyseDrawCross = new JCheckBoxMenuItem("开启分析-描线");


    public void log(String msg) {
//        jtaConsole.setText(msg+"\r\n"+jtaConsole.getText());
        jtaConsole.setText(jtaConsole.getText()+"\r\n"+msg);
    }

    public AnylyseAndClickWindow() throws Exception {
        this.setVisible(true);
        this.setTitle("找笑脸");
        this.setSize(350, 500);
        setFrameRight();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setDefaultLookAndFeelDecorated(false);
//        setDefaultLookAndFeelDecorated(true);
        jtaConsole.setEditable(false);
        jtaConsole.setAutoscrolls(true);
        jtbFires.setAutoscrolls(true);
        DefaultTableModel  tableModel = new DefaultTableModel(null,columnNames);
        jtbFires.setModel(tableModel);
        TableColumnModel columnModel = jtbFires.getColumnModel();
//        columnModel.getColumn(1).set;

        robot = new Robot();

        Container root = getContentPane();

        paintMenu();
        paintUI(root);

        btnFire.addActionListener(new BtnFireAction(root));

        btnHwnd.addActionListener(new OpenHwndAction());
        btnOpenCapture.addActionListener(new OpenCaptureAction());
        btnDeleteCapture.addActionListener(new DeleteCaptureAction());
        btnOpenAnalyse.addActionListener(new OpenAnalyseAction());
        btnDeleteAnalyse.addActionListener(new DeleteAnalyseAction());
        jmiEnableAnalyse.addActionListener(new EnableAnalyseAction());
        jmiEnableAnalyse.setSelected(anylyse);
        jmiEnableAnalyseDrawCross.addActionListener(new EnableAnaDrawCrossAction());
        jmiEnableAnalyseDrawCross.setSelected(any_drawPoints);

    }

    private void paintMenu() {
        jmnConfigMenu.add(jmiEnableAnalyse);
        jmnConfigMenu.add(jmiEnableAnalyseDrawCross);


        this.jmbMenuBar.add(jmnConfigMenu);
        this.setJMenuBar(this.jmbMenuBar);
    }

    private void paintUI(Container root) {
        final JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(2, 2));
        formPanel.add(jlbHwnd);
        formPanel.add(jtfHwnd);
        formPanel.add(jlbTitleStr);
        formPanel.add(jtfTitleStr);



//        final JPanel actionPanel = new JPanel(new FlowLayout());
        final JPanel actionPanel = new JPanel(new GridLayout(2,4));
        actionPanel.add(btnHwnd);
        actionPanel.add(btnOpenCapture);
        actionPanel.add(btnDeleteCapture);
        actionPanel.add(btnOpenAnalyse);
        actionPanel.add(btnDeleteAnalyse);
        actionPanel.add(btnFire);

        tabbedPane.add("控制台", jspConsole);
//        tabbedPane.add("记录", jtbFires);
        tabbedPane.add("记录", jspFires);

        final JPanel contentPanel = new JPanel(new BorderLayout());
//        contentPanel.add(jtaConsole, BorderLayout.CENTER);
//        contentPanel.add(jspConsole, BorderLayout.CENTER);
        contentPanel.add(tabbedPane, BorderLayout.CENTER);

        contentPanel.add(actionPanel, BorderLayout.PAGE_END);


        root.setLayout(new BorderLayout());
//        root.add(formPanel);
        root.add(formPanel, BorderLayout.NORTH);
//        root.add(contentPanel);
        root.add(contentPanel, BorderLayout.CENTER);
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

    public void setFrameRight() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }

        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }

        this.setLocation((screenSize.width - frameSize.width)*9/10,
                (screenSize.height - frameSize.height) / 2);
    }

    public static void main(String[] args) {
        try {
            AnylyseAndClickWindow captureWindow = new AnylyseAndClickWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private  class OpenHwndAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String toolPath = PathUtil.getProjectPath("JNI").getPath("tool");
            String exePath = PathUtil.buildPath(toolPath, "LookHandles.exe");
            try {
                Runtime.getRuntime().exec(exePath);
                log("已打开句柄程序");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private  class DeleteCaptureAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int i =FileUtil.deleteFilesUnderDir(Config.capture_path);
            log("已删除捕获目录下文件:"+i+"个");
        }
    }

    private  class DeleteAnalyseAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int i = FileUtil.deleteFilesUnderDir(Config.anylyse_path);
            log("已删除分析目录下文件:"+i+"个");
        }
    }

    private  class OpenCaptureAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            FileUtil.openDirectory(Config.capture_path);
            log("已打开捕获目录");
        }
    }

    private  class OpenAnalyseAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            FileUtil.openDirectory(Config.anylyse_path);
            log("已打开分析目录");
        }
    }

    private class BtnFireAction implements ActionListener {
        private final Container root;

        public BtnFireAction(Container root) {
            this.root = root;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            TimeAnalyser timeAnalyser = new TimeAnalyser();
//            List<String> record = new ArrayList<>();
            String[] record = new String[3];
            try {
                rereshHwnd();
                btnFire.setEnabled(false);

                BMP bmp = null;
                if (capture) {
                    logger.debug("capture enabled");
                    File file = HwndUtil.shortCut(hWnd, Config.capture_path,offset);
                    timeAnalyser.setTitle(file.getAbsolutePath());
                    record[1] = file.getAbsolutePath();
                    bmp =  new BMP(file);
                } else {
                    bmp =HwndUtil.captureAndGetBMP(hWnd,offset);
                }

                timeAnalyser.push("get BMP");

                List<Point> points = mathcer.match(bmp, SIMILARITY);
                timeAnalyser.push("match points================>"+points.size());

                //remove duplicate
                pointFilter.filter(points);
                timeAnalyser.push("remove duplicate ... rest================>"+points.size());

                if (anylyse) {
                    logger.debug("anylyse enabled");

                    if (any_drawPoints) {
                        logger.debug("any_drawPoints enabled");
                        //draw point
                        for (Point point : points) {
                            DrawUtil.drawPointCross(bmp, point, CLICK_POINT_LENGTH, CLICK_POINT_COLOR);
                        }
                        timeAnalyser.push("[√]drawPointCross");
                    }


                    //write
                    try {
                        File outFile = new File(Config.anylyse_path + "\\" + bmp.getFile().getName());
                        DrawUtil.writeFile(bmp, outFile);
                        timeAnalyser.push("[√]writeFile after drawing");
                        timeAnalyser.pushStringOnly(outFile.getAbsolutePath());
                        record[2] = outFile.getAbsolutePath();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }

                logger.debug("start clicking..");
                logger.debug("clickPoints:"+points.size());

                //convert offset before click
                for (Point point : points) {
                    point.setX(point.getX() + offset.getX());
                    point.setY(point.getY() + offset.getY());
                }
                timeAnalyser.push("convert offset================>"+points.size());


                //click
                for (Point point : points) {
//                        Mouse.click(hWnd, point.getX(), point.getY());
                    Mouse.pressByRobot(hWnd,robot,point);

//                        Thread.sleep(seed.nextInt(CLICK_INTERVAL));
                }
                record[0] = points.size()+"";

                timeAnalyser.push("click points================>"+points.size() + " "+points);

                //log
                log(timeAnalyser.analyse());


                //record
                DefaultTableModel model = (DefaultTableModel) jtbFires.getModel();
                model.addRow(record);

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(root, ex.getMessage());
            } finally {
                btnFire.setEnabled(true);
            }
        }
    }

    private class EnableAnalyseAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (jmiEnableAnalyse.isSelected()) {
                jmiEnableAnalyseDrawCross.setEnabled(true);
            } else {
                jmiEnableAnalyseDrawCross.setEnabled(false);
            }

            anylyse = jmiEnableAnalyse.isSelected();

            log(jmiEnableAnalyse.getText()+":"+ getBooleanStr(AnylyseAndClickWindow.this.anylyse));
        }
    }

    private class EnableAnaDrawCrossAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            any_drawPoints = jmiEnableAnalyseDrawCross.isSelected();

            log(jmiEnableAnalyseDrawCross.getText()+":"+ getBooleanStr(AnylyseAndClickWindow.this.any_drawPoints));
        }
    }

    private String getBooleanStr(boolean any_drawPoints) {
        return any_drawPoints ?"已启用":"已禁用";
    }
}

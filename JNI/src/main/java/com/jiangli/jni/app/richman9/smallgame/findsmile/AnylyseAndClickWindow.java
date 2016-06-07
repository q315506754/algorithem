package com.jiangli.jni.app.richman9.smallgame.findsmile;

import com.jiangli.common.core.CommonObjectToObjectFieldBinding;
import com.jiangli.common.core.FileStringRegexDynamicProcesser;
import com.jiangli.common.core.ValueDecorator;
import com.jiangli.common.utils.*;
import com.jiangli.graphics.common.*;
import com.jiangli.graphics.common.Color;
import com.jiangli.graphics.common.Point;
import com.jiangli.graphics.impl.RmoveDuplicatePointFilter;
import com.jiangli.graphics.inf.PointFilter;
import com.jiangli.jni.app.impl.FindSmileJavaCVThreadMathcer;
import com.jiangli.jni.common.Config;
import com.jiangli.jni.common.DrawUtil;
import com.jiangli.jni.common.HwndUtil;
import com.jiangli.jni.common.Mouse;
import com.jiangli.jni.core.User32;
import com.jiangli.swing.*;
import com.jiangli.swing.formatter.DoubleStringFormatter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/1 0001 13:26
 */
public class AnylyseAndClickWindow extends JFrame {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private JTextField jtfHwnd = new JTextField(Config.test_hWnd+"");
    private JLabel jlbHwnd = new JLabel("句柄");

    private JTextField jtfTitleStr = new JTextField("大富翁9");
    private JLabel jlbTitleStr = new JLabel("标题");

    private JTextField jtfSimilar = new JTextField(getSimilarityString());
    private final StartAndStopBinding binding;
    private FileStringRegexDynamicProcesser dynamicProcesser;
    private InputJavaCodeBinding inputJav;

    private String getSimilarityString() {
        return NumberUtil.getDoubleString(Config.getSmileSimilartity(),8);
    }

    private JLabel jlbSimilar = new JLabel("相似度");

    private JMenuBar jmbMenuBar = new JMenuBar();
    private JMenu jmnConfigMenu = new JMenu("配置");

    private JTabbedPane tabbedPane = new JTabbedPane();

    private JTextArea jtaConsole = new JTextArea("");
    private JScrollPane jspConsole = new JScrollPane(jtaConsole);


    private JTable jtbFires = new JTable();
    private JScrollPane jspFires = new JScrollPane(jtbFires);
    private String[] columnNames = {"匹配点数目","捕获图","分析图"};

    private JButton btnTestCapture = new JButton("测试截图");
    private JButton btnTestSample = new JButton("测试样本");

    private JButton btnHwnd = new JButton("句柄程序");
    private JButton btnFire = new JButton("点击笑脸");
    private JButton btnFireStart = new JButton("开启点击");
    private Thread startedClickingThread = null;
    private boolean startedClicking = false;
    private JButton btnFireStop = new JButton("结束点击");
    private JButton btnDeleteCapture = new JButton("删除捕获");
    private JButton btnOpenCapture = new JButton("打开捕获");
    private JButton btnDeleteAnalyse = new JButton("删除分析");
    private JButton btnOpenAnalyse = new JButton("打开分析");
    private JButton btnAnalyseDir = new JButton("分析目录");

    private JButton btnReloadThread = new JButton("刷新线程");

    private Integer hWnd = null;
    private User32 user32 = User32.INSTANCE;

//    private SmileAnylyser anylyser = new SmileAnylyser(Config.characteristic_path);


    private final boolean capture = true;//不能改为false

    private boolean anylyse = true;
    private boolean any_drawLine = true;
    private boolean any_drawPoints = true;
    private boolean use_offSet = true;

    private com.jiangli.graphics.common.Color MATCH_COLOR = new Color(0,0,0);
    private Color CLICK_POINT_COLOR = new Color(255,0,0);
    private int CLICK_POINT_LENGTH = 50;
    private int CLICK_INTERVAL = 200;

    private Random seed = new Random();
    private Robot robot;
    private Rect offset = new Rect(580,210,400,400);
    private RectPercentage offsetPercentage = new RectPercentage(27.00,18.50,18.00,32.00);
    private PointFilter pointFilter = new RmoveDuplicatePointFilter(20);
    private JMenuItem jmiEnableAnalyse = new JCheckBoxMenuItem("开启分析");
    private JMenuItem jmiEnableAnalyseDrawCross = new JCheckBoxMenuItem("开启分析-描线");
    private JMenuItem jmiEnableUseOffSet = new JCheckBoxMenuItem("启用截图偏移");

    private JTextField jtfOffSetRectLeft = new JTextField();
    private JTextField jtfOffSetRectTop = new JTextField();
    private JTextField jtfOffSetRectWidth = new JTextField();
    private JTextField jtfOffSetRectLength = new JTextField();

    //lazy init
    private FindSmileJavaCVThreadMathcer mathcer ;


    public void log(String msg) {
//        jtaConsole.setText(msg+"\r\n"+jtaConsole.getText());
        jtaConsole.setText(jtaConsole.getText() + "\r\n" + msg);
    }

    private Rect getOffSet(){
        if (use_offSet) {
            return offset;
        }
        return null;
    }

    private void convertPercentageToAbs() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double restWidth = screenSize.getWidth() - this.getWidth();
        double restHeight = screenSize.getHeight() - this.getHeight();
        offset.setX((int)(offsetPercentage.getLeft() * screenSize.getWidth()/100));
        offset.setY((int)(offsetPercentage.getTop() * screenSize.getHeight()/100));
        offset.setWidth((int)(offsetPercentage.getWidth() * screenSize.getWidth()/100));
        offset.setLength((int) (offsetPercentage.getHeight() * screenSize.getHeight() / 100));
        log("abs offset:" + offset);
    }

    private void syncRectOffsetFieldToRectPercentrage() {
        offsetPercentage.setLeft(Double.parseDouble(jtfOffSetRectLeft.getText()));
        offsetPercentage.setTop(Double.parseDouble(jtfOffSetRectTop.getText()));
        offsetPercentage.setWidth(Double.parseDouble(jtfOffSetRectWidth.getText()));
        offsetPercentage.setHeight(Double.parseDouble(jtfOffSetRectLength.getText()));
        log("offset percentage:"+offsetPercentage);
    }

    public AnylyseAndClickWindow() throws Exception {
        this.setVisible(true);
        this.setTitle("找笑脸");
        this.setSize(400, 500);
        SwingUtil.setFrameRelativePos(this,90,50);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setDefaultLookAndFeelDecorated(false);
//        convertPercentageToAbs();
//        setDefaultLookAndFeelDecorated(true);
        jtaConsole.setEditable(false);
        jtaConsole.setAutoscrolls(true);
        jtbFires.setAutoscrolls(true);
        DefaultTableModel  tableModel = new DefaultTableModel(null,columnNames);
        jtbFires.setModel(tableModel);

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
        jmiEnableUseOffSet.addActionListener(new EnableUseOffSetAction());
        jmiEnableUseOffSet.setSelected(use_offSet);
        btnTestCapture.addActionListener(new TestCaptureAction());
        btnTestSample.addActionListener(new TestSampleAction());
        btnAnalyseDir.addActionListener(new AnalyseDirAction());
        btnReloadThread.addActionListener(new ReloadThreadAction());

        jtfHwnd.addKeyListener(new RefreshHwndAction());
        jtfTitleStr.addKeyListener(new RefreshHwndAction());
        jtfSimilar.addKeyListener(new SimilarRefreshAction());

        //init instance
        robot = new Robot();
        mathcer = new FindSmileJavaCVThreadMathcer();
        dynamicProcesser = new FileStringRegexDynamicProcesser("int\\s*test_hWnd\\s*=\\s*\\d*;", "int test_hWnd=<value>;");
        inputJav = new InputJavaCodeBinding(Config.class, dynamicProcesser);


        //binding events
        // start&stop
        binding = new StartAndStopBinding(btnFireStart, btnFireStop, new BindingCallBack() {
            @Override
            public void run() {
                btnFire.doClick();
            }
        });

        //perRect -> absRect
        CommonObjectToObjectFieldBinding perToAbsLeft = new CommonObjectToObjectFieldBinding(offsetPercentage, "left", offset, "x", new ScreenPercentageBindingX());
        CommonObjectToObjectFieldBinding perToAbsTop = new CommonObjectToObjectFieldBinding(offsetPercentage, "top", offset, "y", new ScreenPercentageBindingY());
        CommonObjectToObjectFieldBinding perToAbsWidth = new CommonObjectToObjectFieldBinding(offsetPercentage, "width", offset, "width", new ScreenPercentageBindingX());
        CommonObjectToObjectFieldBinding perToAbsHeight=new CommonObjectToObjectFieldBinding(offsetPercentage, "height", offset, "length", new ScreenPercentageBindingY());

        //JText -> perRect
        DoubleStringFormatter doubleStringFormatter = new DoubleStringFormatter(2);
        new TextFObjectFRefershBinding(jtfOffSetRectLeft,offsetPercentage,"left",doubleStringFormatter,perToAbsLeft);
        new TextFObjectFRefershBinding(jtfOffSetRectTop,offsetPercentage,"top",doubleStringFormatter,perToAbsTop);
        new TextFObjectFRefershBinding(jtfOffSetRectWidth,offsetPercentage,"width",doubleStringFormatter,perToAbsWidth);
        new TextFObjectFRefershBinding(jtfOffSetRectLength,offsetPercentage,"height",doubleStringFormatter,perToAbsHeight);


        //lazy last
        refreshHwnd();
    }

    private void paintMenu() {
        jmnConfigMenu.add(jmiEnableAnalyse);
        jmnConfigMenu.add(jmiEnableAnalyseDrawCross);
        jmnConfigMenu.add(jmiEnableUseOffSet);


        this.jmbMenuBar.add(jmnConfigMenu);
        this.setJMenuBar(this.jmbMenuBar);
    }

    private void paintUI(Container root) {
        final JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 2));
        formPanel.add(jlbHwnd);
        formPanel.add(jtfHwnd);
        formPanel.add(jlbTitleStr);
        formPanel.add(jtfTitleStr);
        formPanel.add(jlbSimilar);
        formPanel.add(jtfSimilar);


        final JPanel rectOffSet = new JPanel(new GridLayout(2,4));
        rectOffSet.add(new JLabel("left:"));
        rectOffSet.add(jtfOffSetRectLeft);

        rectOffSet.add(new JLabel("top:"));
        rectOffSet.add(jtfOffSetRectTop);

        rectOffSet.add(new JLabel("width:"));
        rectOffSet.add(jtfOffSetRectWidth);

        rectOffSet.add(new JLabel("height:"));
        rectOffSet.add(jtfOffSetRectLength);

        final JPanel rectOffBtns = new JPanel(new GridLayout(1,2));
        rectOffBtns.add(btnTestCapture);
        rectOffBtns.add(btnTestSample);

        formPanel.add(rectOffSet);
        formPanel.add(rectOffBtns);

//        final JPanel actionPanel = new JPanel(new FlowLayout());
        final JPanel actionPanel = new JPanel(new GridLayout(3,4));
        actionPanel.add(btnHwnd);
        actionPanel.add(btnOpenCapture);
        actionPanel.add(btnDeleteCapture);
        actionPanel.add(btnOpenAnalyse);
        actionPanel.add(btnDeleteAnalyse);
        actionPanel.add(btnAnalyseDir);
        actionPanel.add(btnReloadThread);
        actionPanel.add(btnFire);
        actionPanel.add(btnFireStart);
        actionPanel.add(btnFireStop);

        tabbedPane.add("控制台", jspConsole);
        tabbedPane.add("记录", jspFires);

        final JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(tabbedPane, BorderLayout.CENTER);

        contentPanel.add(actionPanel, BorderLayout.PAGE_END);

        root.setLayout(new BorderLayout());
        root.add(formPanel, BorderLayout.NORTH);
        root.add(contentPanel, BorderLayout.CENTER);
    }

    private String getDoubleString(double left) {
        return new BigDecimal(left).setScale(2).toString();
    }

    public void refreshHwnd() throws Exception {
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

        logger.debug("已经成功获得句柄:"+hWnd);
        log("当前句柄:"+hWnd);
//        String src_java_code_path = PathUtil.getSRC_JAVA_Code_Path(Config.class);
//        File file = new File(src_java_code_path);


        dynamicProcesser.setPhValue(hWnd+"");
        inputJav.refresh();
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
                btnFire.setEnabled(false);

                BMP bmp = null;
                if (capture) {
                    logger.debug("capture enabled");
                    File file = HwndUtil.shortCut(hWnd, Config.capture_path,getOffSet());
                    timeAnalyser.setTitle(file.getAbsolutePath());
                    record[1] = file.getAbsolutePath();
                    bmp =  new BMP(file);
                } else {
                    bmp =HwndUtil.captureAndGetBMP(hWnd,getOffSet());
                }

                timeAnalyser.push("get BMP");

                List<Point> points = mathcer.match(bmp, Config.getSmileSimilartity());
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
                if (getOffSet() != null) {
                    for (Point point : points) {
                        point.setX(point.getX() + offset.getX());
                        point.setY(point.getY() + offset.getY());
                    }
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

    private class EnableUseOffSetAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            use_offSet = jmiEnableUseOffSet.isSelected();

            log(jmiEnableUseOffSet.getText()+":"+ getBooleanStr(AnylyseAndClickWindow.this.use_offSet));
        }
    }

    private class TestCaptureAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                File file = HwndUtil.shortCut(hWnd,getOffSet());
                FileUtil.openPicture(file);
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        }
    }

    private class RefreshHwndAction implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
            try {
                refreshHwnd();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }


    private class TestSampleAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            BMP bmp = new BMP(PathUtil.buildPath(Config.sample_path,"gaming.bmp"));
            File file = DrawUtil.drawRect(bmp, offset, new Color(255, 0, 0));
            FileUtil.openPicture(file);
        }
    }

    private class AnalyseDirAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TimeAnalyser analyser = new TimeAnalyser();
            DirAnalyser dirAnalyser = new DirAnalyser();
//                log("分析结束,共分析了:"+dirAnalyser.getAnalysedSize()+"个文件");
            analyser.pushStringOnly("分析了:"+dirAnalyser.getAnalysedSize()+"个文件");
            analyser.push("分析结束");
            log(analyser.analyse());
        }
    }

    private class ReloadThreadAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mathcer = new FindSmileJavaCVThreadMathcer();
            log("重新加载线程完毕");
        }
    }

    private class SimilarRefreshAction implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
            double v = Double.parseDouble(jtfSimilar.getText());
            Config.setSmileSimilartity((float) v);
            log("相似度已修改为:"+getSimilarityString());
        }
    }

}

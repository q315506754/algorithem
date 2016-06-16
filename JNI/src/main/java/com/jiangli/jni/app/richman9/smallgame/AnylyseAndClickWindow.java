package com.jiangli.jni.app.richman9.smallgame;

import com.jiangli.common.core.CommonObjectToObjectFieldBinding;
import com.jiangli.common.core.FileStringRegexDynamicProcesser;
import com.jiangli.common.utils.*;
import com.jiangli.graphics.common.*;
import com.jiangli.graphics.common.Color;
import com.jiangli.graphics.common.Point;
import com.jiangli.graphics.impl.RmoveDuplicatePointFilter;
import com.jiangli.graphics.inf.BMPMatcher;
import com.jiangli.graphics.inf.PointFilter;
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
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/1 0001 13:26
 */
public abstract class AnylyseAndClickWindow extends JFrame implements CatureAndClickWindow{
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected String project_base;
    protected String anylyse_path;
    protected String captured_path;
    protected String sample_path;
    protected float similartity;
    protected Analyser dirAnalyser;
    protected BMPMatcher mathcer ;
    protected int mouse_press_duration_start=30;
    protected int mouse_press_duration_end=100;
    protected int mouse_press_interval_start = 10;
    protected int mouse_press_interval_end = 300;
    protected List<PointsListener> pointsListeners=new ArrayList<>();
    protected Optional<Musicial> piano = Optional.empty();

    private JTextField jtfHwnd = new JTextField(Config.test_hWnd+"");
    private JLabel jlbHwnd = new JLabel("句柄");

    private JTextField jtfTitleStr = new JTextField("大富翁9");
    private JLabel jlbTitleStr = new JLabel("标题");

    private JTextField jtfSimilar = new JTextField();
    private  StartAndStopBinding binding;
    private FileStringRegexDynamicProcesser dynamicProcesser;
    private InputJavaCodeBinding inputJav;

    private File generatedSampleFile;
    private String samplePicName = "gaming.bmp";


    private String getSimilarityString() {
        return NumberUtil.getDoubleString(similartity,8);
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
    protected JButton btnFire = new JButton("点击笑脸");
    private JButton btnFireStart = new JButton("开启点击");
    private JButton btnFireStop = new JButton("结束点击");
    private JButton btnDeleteCapture = new JButton("删除捕获");
    private JButton btnOpenCapture = new JButton("打开捕获");
    private JButton btnDeleteAnalyse = new JButton("删除分析");
    private JButton btnOpenAnalyse = new JButton("打开分析");
    private JButton btnAnalyseDir = new JButton("分析目录");

    private JButton btnReloadThread = new JButton("刷新线程");
    private JButton btnClearConsole = new JButton("清空消息");
    private JButton btnStartMusic = new JButton("开始音乐");
    private JButton btnStopMusic = new JButton("停止音乐");

    private Integer hWnd = null;
    private User32 user32 = User32.INSTANCE;


    private final boolean capture = true;//不能改为false

    private boolean anylyse = false;
//    private boolean anylyse = true;
    private boolean any_drawLine = true;
    private boolean any_drawPoints = true;
    private boolean rec_table = false;
    private boolean use_offSet = true;
    private boolean mouse_press_duration= false;
    private boolean mouse_press_interval= false;

    private com.jiangli.graphics.common.Color MATCH_COLOR = new Color(0,0,0);
    private Color CLICK_POINT_COLOR = new Color(255,0,0);
    private int CLICK_POINT_LENGTH = 50;

    private Robot robot;
    private Rect offset = new Rect(580,210,400,400);//截图位移
    protected RectPercentage offsetPercentage = new RectPercentage(27.00,18.50,18.00,32.00);//截图位移 百分比
    private PointFilter pointFilter = new RmoveDuplicatePointFilter(20);
    private JMenuItem jmiEnableAnalyse = new JCheckBoxMenuItem("开启分析");
    private JMenuItem jmiEnableAnalyseDrawCross = new JCheckBoxMenuItem("开启分析-描线");
    private JMenuItem jmiEnableUseOffSet = new JCheckBoxMenuItem("启用截图偏移");
    private JMenuItem jmiEnableTableRec = new JCheckBoxMenuItem("记录表");
    private JMenuItem jmiEnableMousePressing = new JCheckBoxMenuItem("鼠标按住");
    private JMenuItem jmiEnableMouseInterval = new JCheckBoxMenuItem("鼠标点击间隔");
    private JMenuItem jmiSampleSelect = new JMenuItem("样本选择");

    private JTextField jtfOffSetRectLeft = new JTextField();
    private JTextField jtfOffSetRectTop = new JTextField();
    private JTextField jtfOffSetRectWidth = new JTextField();
    private JTextField jtfOffSetRectLength = new JTextField();


    private JTextField jtfDrawPointX = new JTextField();
    private JTextField jtfDrawPointY = new JTextField();
    private JCheckBox jcbDrawCalcOffset = new JCheckBox("计算偏移");
    private boolean draw_points_calc_offset= true;
    private JButton btnDrawPoints = new JButton("描点");
    private JButton btnDrawPointsGetColor = new JButton("取色");


    //RECORD
    private TimeAnalyser timeAnalyser;
    private String[] record;
    private BMP capturedBPM;
    private int captureMode;

    protected void setMousePressDuration(int start, int end){
        this.mouse_press_duration_start = start;
        this.mouse_press_duration_end = end;
    }


    @Override
    public void turboFire() {
        btnFireStart.doClick();
    }

    @Override
    public void stopTurboFire() {
        btnFireStop.doClick();
    }

    @Override
    public void playMusic() {
        piano.ifPresent((msc)->{
            msc.playMusic();
        });
//     JOptionPane.showMessageDialog(this, "没有音乐可以播放", "错误", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void stopMusic() {
        piano.ifPresent((msc)->{
            msc.stopMusic();
        });
    }

    @Override
    public String getCaptureFilePath() {
        return captured_path;
    }

    @Override
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


    public AnylyseAndClickWindow()  {
        try {
            initial();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initial() throws Exception {
        //children config
        project_base = getProjectBasePath();
        similartity= getSimilarity();
        anylyse_path = PathUtil.buildPath(project_base, "anylyse");
        captured_path= PathUtil.buildPath(project_base, "captured");
        sample_path= PathUtil.buildPath(project_base, "sample");
        mathcer = getBMPMatcher();
        dirAnalyser = getDirAnalyser();

        log("project_base:"+project_base);
        log("anylyse_path:"+anylyse_path);
        log("captured_path:"+captured_path);
        log("sample_path:"+sample_path);

        //init property
        jtaConsole.setEditable(false);
        jtaConsole.setAutoscrolls(true);
        jtbFires.setAutoscrolls(true);
        jtfSimilar.setText(getSimilarityString());
        piano = Optional.of(new EmptyMusicial(this));

        //init instance
        robot = new Robot();
        dynamicProcesser = new FileStringRegexDynamicProcesser("int\\s*test_hWnd\\s*=\\s*\\d*;", "int test_hWnd=<value>;");
        inputJav = new InputJavaCodeBinding(Config.class, dynamicProcesser);


        //init children
        childrenInitialStart();

        Container root = getContentPane();

        //btn events form
        btnTestCapture.addActionListener(new TestCaptureAction());
        btnTestSample.addActionListener(new TestSampleAction());
        btnDrawPoints.addActionListener(new TestDrawPointsAction(root));
        btnDrawPointsGetColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (generatedSampleFile == null) {
                    JOptionPane.showMessageDialog(root, "请先测试样本", "错误", JOptionPane.ERROR_MESSAGE);
                } else {
                    BMP bmp = new BMP(generatedSampleFile);

                    Point drawed = getRealDrawPoint();
                    Color colorObj = bmp.getColorObj(drawed.getX(), drawed.getY());
                    JOptionPane.showInputDialog(root, "请复制下面颜色","new Color("+colorObj.getR()+","+colorObj.getG()+","+colorObj.getB()+")");


                }
            }
        });

        //btn events main
        btnHwnd.addActionListener(new OpenHwndAction());
        btnOpenCapture.addActionListener(new OpenCaptureAction());
        btnDeleteCapture.addActionListener(new DeleteCaptureAction());
        btnOpenAnalyse.addActionListener(new OpenAnalyseAction());
        btnDeleteAnalyse.addActionListener(new DeleteAnalyseAction());
        btnAnalyseDir.addActionListener(new AnalyseDirAction());
        btnReloadThread.addActionListener(new ReloadThreadAction());
        btnFire.addActionListener(new BtnFireAction(root));
        //binding events
        // start&stop
        binding = new StartAndStopBinding(btnFireStart, btnFireStop, btnFire::doClick);
        btnClearConsole.addActionListener((e)->jtaConsole.setText(""));
        btnStartMusic.addActionListener((e)-> playMusic());
        btnStopMusic.addActionListener((e)->stopMusic());

        //keyboard listeners
        jtfHwnd.addKeyListener(new RefreshHwndAction());
        jtfTitleStr.addKeyListener(new RefreshHwndAction());
        jtfSimilar.addKeyListener(new SimilarRefreshAction());

        //table render
        DefaultTableModel tableModel = new DefaultTableModel(null,columnNames);
        jtbFires.setModel(tableModel);
        TableCellRenderer cellRenderer = new ImgPathCellRenderer();
        jtbFires.getColumnModel().getColumn(1).setCellRenderer(cellRenderer);
        jtbFires.getColumnModel().getColumn(2).setCellRenderer(cellRenderer);
        jtbFires.getColumnModel().getColumn(1).setCellEditor(new ImgPathButtonEditor());
        jtbFires.getColumnModel().getColumn(2).setCellEditor(new ImgPathButtonEditor());


        //jMenuItem -> boolean & log
        new SelectableButtonToObjFieldBinding(jmiEnableAnalyse, this, "anylyse", new BtnObjFActionListener<Boolean>() {
            @Override
            public void actionPerformed(AbstractButton com, Boolean val, Object obj, String field) {
                jmiEnableAnalyseDrawCross.setEnabled(val);
            }
        },new LogBooleanMenuItemAction());
        new SelectableButtonToObjFieldBinding(jmiEnableAnalyseDrawCross, this, "any_drawPoints", new LogBooleanMenuItemAction());
        new SelectableButtonToObjFieldBinding(jmiEnableUseOffSet, this, "use_offSet", new LogBooleanMenuItemAction());
        new SelectableButtonToObjFieldBinding(jmiEnableTableRec, this, "rec_table", new LogBooleanMenuItemAction());
        new SelectableButtonToObjFieldBinding(jmiEnableMousePressing, this, "mouse_press_duration", new LogBooleanMenuItemAction());
        new SelectableButtonToObjFieldBinding(jmiEnableMouseInterval,this,"mouse_press_interval", new LogBooleanMenuItemAction());
        new SelectableButtonToObjFieldBinding(jcbDrawCalcOffset,this,"draw_points_calc_offset", new LogBooleanMenuItemAction());
        jmiSampleSelect.addActionListener(new MenuSampleSelectAction(root));

        //perRect -> absRect
        CommonObjectToObjectFieldBinding perToAbsLeft = new CommonObjectToObjectFieldBinding(offsetPercentage, "left", offset, "x", new ScreenPercentageBindingX());
        CommonObjectToObjectFieldBinding perToAbsTop = new CommonObjectToObjectFieldBinding(offsetPercentage, "top", offset, "y", new ScreenPercentageBindingY());
        CommonObjectToObjectFieldBinding perToAbsWidth = new CommonObjectToObjectFieldBinding(offsetPercentage, "width", offset, "width", new ScreenPercentageBindingX());
        CommonObjectToObjectFieldBinding perToAbsHeight=new CommonObjectToObjectFieldBinding(offsetPercentage, "height", offset, "length", new ScreenPercentageBindingY());

        //JText -> perRect
        DoubleStringFormatter doubleStringFormatter = new DoubleStringFormatter(2);
        new TextFObjectFRefershBinding(jtfOffSetRectLeft, offsetPercentage, "left", doubleStringFormatter, perToAbsLeft, new LogOffsetAction());
        new TextFObjectFRefershBinding(jtfOffSetRectTop,offsetPercentage,"top",doubleStringFormatter,perToAbsTop, new LogOffsetAction());
        new TextFObjectFRefershBinding(jtfOffSetRectWidth,offsetPercentage,"width",doubleStringFormatter,perToAbsWidth, new LogOffsetAction());
        new TextFObjectFRefershBinding(jtfOffSetRectLength,offsetPercentage,"height",doubleStringFormatter,perToAbsHeight, new LogOffsetAction());

        //lazy last
        refreshHwnd();

        //paint main
        paintMenu();
        paintUI(root);
        //set style
        SwingUtil.setCommonFrameStyle(this);

        childrenInitialEnd();
    }

    public Point getRealDrawPoint() {
        Point drawed = new Point();
        drawed.setX(NumberUtil.parseInt(jtfDrawPointX.getText()));
        drawed.setY(NumberUtil.parseInt(jtfDrawPointY.getText()));
        if (jcbDrawCalcOffset.isSelected()) {
            drawed.setX(drawed.getX() + offset.getX());
            drawed.setY(drawed.getY() + offset.getY());
        }
        return drawed;
    }

    public abstract Analyser getDirAnalyser() ;

    public abstract BMPMatcher getBMPMatcher();

    protected abstract float getSimilarity();

    protected abstract void childrenInitialStart();

    protected abstract String getProjectBasePath();

    protected abstract void childrenInitialEnd();

    private void paintMenu() {
        jmnConfigMenu.add(jmiEnableAnalyse);
        jmnConfigMenu.add(jmiEnableAnalyseDrawCross);
        jmnConfigMenu.add(jmiEnableUseOffSet);
        jmnConfigMenu.add(jmiEnableTableRec);
        jmnConfigMenu.add(jmiEnableMousePressing);
        jmnConfigMenu.add(jmiEnableMouseInterval);
        jmnConfigMenu.add(jmiSampleSelect);


        this.jmbMenuBar.add(jmnConfigMenu);
        this.setJMenuBar(this.jmbMenuBar);
    }

    private void paintUI(Container root) {
        final JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(5, 2));
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

        //描点行
        final JPanel drawPointsLeftRow = new JPanel(new GridLayout(1,4));
        drawPointsLeftRow.add(new JLabel("X:"));
        drawPointsLeftRow.add(jtfDrawPointX);
        drawPointsLeftRow.add(new JLabel("Y:"));
        drawPointsLeftRow.add(jtfDrawPointY);

        final JPanel drawPointsRightRow = new JPanel(new GridLayout(1,3));
        drawPointsRightRow.add(jcbDrawCalcOffset);
        drawPointsRightRow.add(btnDrawPoints);
        drawPointsRightRow.add(btnDrawPointsGetColor);

        formPanel.add(drawPointsLeftRow);
        formPanel.add(drawPointsRightRow);


//        final JPanel actionPanel = new JPanel(new FlowLayout());
        final JPanel actionPanel = new JPanel(new GridLayout(4,4));
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
        actionPanel.add(btnClearConsole);
        actionPanel.add(btnStartMusic);
        actionPanel.add(btnStopMusic);

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







    private static class ImgPathCellRenderer implements TableCellRenderer {
        private JPanel panel = new JPanel();
        private JButton button = new JButton();


        private void init() {
            this.button.setBounds(0, 0, 50, 15);
            this.panel.setLayout(null);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            init();
            this.button.setText(value == null ? "" : String.valueOf(value));
            return this.panel;
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
            int i =FileUtil.deleteFilesUnderDir(captured_path);
            log("已删除捕获目录下文件:"+i+"个");
        }
    }

    private  class DeleteAnalyseAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int i = FileUtil.deleteFilesUnderDir(anylyse_path);
            log("已删除分析目录下文件:"+i+"个");
        }
    }

    private  class OpenCaptureAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            FileUtil.openDirectory(captured_path);
            log("已打开捕获目录");
        }
    }

    private  class OpenAnalyseAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            FileUtil.openDirectory(anylyse_path);
            log("已打开分析目录");
        }
    }

    @Override
    public void intialProcess() {
        timeAnalyser = new TimeAnalyser();
        record = new String[3];
    }

    @Override
    public void accept(List<Point> points) {
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
                    DrawUtil.drawPointCross(capturedBPM, point, CLICK_POINT_LENGTH, CLICK_POINT_COLOR);
                }
                timeAnalyser.push("[√]drawPointCross");
            }


            //write
            try {
                File outFile = new File(anylyse_path + "\\" + capturedBPM.getFile().getName());
                DrawUtil.writeFile(capturedBPM, outFile);
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
        //将 小图中的相对点 转成 相对于hwnd的点击目标
        if (getCaptureRect() != null) {
            for (Point point : points) {
                int oldX = point.getX();
                point.setX(oldX + offset.getX());
                int oldY = point.getY();
                point.setY(oldY + offset.getY());

                logger.debug("offset point("+oldX+","+oldY+") -> point("+point.getX()+","+point.getY()+")");
            }
            logger.debug("used offsetX:"+offset.getX());
            logger.debug("used offsetY:"+offset.getY());
        }

        timeAnalyser.push("convert offset================>"+points.size());


        //click
        int pCount = points.size();
        try {
            for (Point point : points) {
                if (mouse_press_duration) {
                    Mouse.pressByRobot(hWnd,robot,point,mouse_press_duration_start,mouse_press_duration_end);
                }else{
                    Mouse.pressByRobot(hWnd,robot,point);
                }


                if (--pCount>0) {
                    if (mouse_press_interval) {
                        if(LogicUtil.checkStartEnd(mouse_press_interval_start,mouse_press_interval_end)){
                            Thread.sleep(RandomUtil.getRandomNum(mouse_press_interval_start,mouse_press_interval_end));
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        record[0] = points.size()+"";

        timeAnalyser.push("click points================>"+points.size() + " "+points);

        //log
        log(timeAnalyser.analyse());


        //record
        if (rec_table) {
            DefaultTableModel model = (DefaultTableModel) jtbFires.getModel();
            model.addRow(record);
        }



    }

    private class BtnFireAction implements ActionListener {
        private final Container root;

        public BtnFireAction(Container root) {
            this.root = root;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            intialProcess();
//            List<String> record = new ArrayList<>();

            try {
                btnFire.setEnabled(false);

                BMP bmp = captureBMP();
                timeAnalyser.push("get BMP");

                //获得相对于位图的点击点
                //应该产生一个可以修改的副本
                List<Point> points = mathcer.match(bmp, similartity);

                accept(points);

                //fire event
                pointsListeners.stream().forEach((listener)->listener.accept(points));

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(root, ex.getMessage());
            } finally {
                btnFire.setEnabled(true);
            }
        }
    }

    @Override
    public BMP captureBMP(int mode) throws IOException {
        BMP bmp;

        captureMode = mode;

        Rect l_offSet = getCaptureRect();

        if (capture) {
            logger.debug("capture enabled");
            File file = HwndUtil.shortCut(hWnd, captured_path, l_offSet);
            timeAnalyser.setTitle(file.getAbsolutePath());
            record[1] = file.getAbsolutePath();
            bmp =  new BMP(file);
        } else {
            bmp =HwndUtil.captureAndGetBMP(hWnd,l_offSet);
        }
        capturedBPM = bmp;
        return bmp;
    }

    private Rect getCaptureRect() {
        Rect l_offSet = null;
        switch (captureMode) {
            case MODE_DEFAULT:
            case MODE_PARTIAL:l_offSet = getOffSet();break;
        }
        return l_offSet;
    }

    @Override
    public BMP captureBMP() throws IOException {
        return captureBMP(Capturable.MODE_PARTIAL);
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
            BMP bmp = new BMP(PathUtil.buildPath(sample_path, samplePicName));
            generatedSampleFile = DrawUtil.drawRect(bmp, offset, new Color(255, 0, 0));
            FileUtil.openPicture(generatedSampleFile);
        }
    }

    private class AnalyseDirAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TimeAnalyser analyser = new TimeAnalyser();

            int analysed = dirAnalyser.analyse();
//                log("分析结束,共分析了:"+dirAnalyser.getAnalysedSize()+"个文件");
            analyser.pushStringOnly("分析了:"+analysed+"个文件");
            analyser.push("分析结束");
            log(analyser.analyse());
        }
    }

    private class ReloadThreadAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mathcer = getBMPMatcher();
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
            similartity = (float) v;
            log("相似度已修改为:"+getSimilarityString());
        }
    }

    private class LogOffsetAction implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
            log("perRect:"+offsetPercentage);
            log("absRect:"+offset);
        }
    }

    private class ClearConsoleAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            jtaConsole.setText("");
        }
    }

    private class EnableTableRecAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            rec_table = jmiEnableTableRec.isSelected();
            log(jmiEnableTableRec.getText()+":"+ getBooleanStr(AnylyseAndClickWindow.this.rec_table));
        }
    }


    public boolean isMouse_press_interval() {
        return mouse_press_interval;
    }

    public void setMouse_press_interval(boolean mouse_press_interval) {
        this.mouse_press_interval = mouse_press_interval;
    }

    public boolean isRec_table() {
        return rec_table;
    }

    public void setRec_table(boolean rec_table) {
        this.rec_table = rec_table;
    }

    public boolean isUse_offSet() {
        return use_offSet;
    }

    public void setUse_offSet(boolean use_offSet) {
        this.use_offSet = use_offSet;
    }

    public boolean isMouse_press_duration() {
        return mouse_press_duration;
    }

    public void setMouse_press_duration(boolean mouse_press_duration) {
        this.mouse_press_duration = mouse_press_duration;
    }

    public boolean isAny_drawPoints() {
        return any_drawPoints;
    }

    public void setAny_drawPoints(boolean any_drawPoints) {
        this.any_drawPoints = any_drawPoints;
    }

    public boolean isAny_drawLine() {
        return any_drawLine;
    }

    public void setAny_drawLine(boolean any_drawLine) {
        this.any_drawLine = any_drawLine;
    }

    public boolean isAnylyse() {
        return anylyse;
    }

    public void setAnylyse(boolean anylyse) {
        this.anylyse = anylyse;
    }

    public boolean isCapture() {
        return capture;
    }

    private class LogBooleanMenuItemAction implements BtnObjFActionListener<Boolean> {
        @Override
        public void actionPerformed(AbstractButton com, Boolean val, Object obj, String field) {
            log(com.getText()+":"+ getBooleanStr(val));
        }
    }

    public boolean isDraw_points_calc_offset() {
        return draw_points_calc_offset;
    }

    public void setDraw_points_calc_offset(boolean draw_points_calc_offset) {
        this.draw_points_calc_offset = draw_points_calc_offset;
    }

    private class TestDrawPointsAction implements ActionListener {
        private final Container root;

        public TestDrawPointsAction(Container root) {
            this.root = root;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (generatedSampleFile == null) {
                JOptionPane.showMessageDialog(root, "请先测试样本", "错误", JOptionPane.ERROR_MESSAGE);
            } else {
                BMP bmp = new BMP(generatedSampleFile);
                Point drawed = getRealDrawPoint();
                generatedSampleFile = DrawUtil.drawPointCross(bmp, drawed, CLICK_POINT_LENGTH, new Color(255, 0, 0));
                FileUtil.openPicture(generatedSampleFile);

            }

        }
    }

    private class MenuSampleSelectAction implements ActionListener {
        private final Container root;

        public MenuSampleSelectAction(Container root) {
            this.root = root;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String s = JOptionPane.showInputDialog(root, "样本图片名", samplePicName);
            if (s!= null) {
               File file = new File( PathUtil.buildPath(sample_path, s));
                if (file.exists()&&file.isFile()) {
                    samplePicName = s;
                    log("使用:" + s + "作为新的样本图片");
                } else {
                    JOptionPane.showMessageDialog(root,"该样本图片不存在");
                }
            }
//                JDialog dialog = new JDialog();
//                dialog.setVisible(true);

        }
    }
}

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
import com.jiangli.jni.app.richman9.smallgame.findsmile.FindSmileDirAnalyser;
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
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/1 0001 13:26
 */
public abstract class AnylyseAndClickWindow extends JFrame {
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

    private JTextField jtfHwnd = new JTextField(Config.test_hWnd+"");
    private JLabel jlbHwnd = new JLabel("句柄");

    private JTextField jtfTitleStr = new JTextField("大富翁9");
    private JLabel jlbTitleStr = new JLabel("标题");

    private JTextField jtfSimilar = new JTextField();
    private  StartAndStopBinding binding;
    private FileStringRegexDynamicProcesser dynamicProcesser;
    private InputJavaCodeBinding inputJav;

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

    private Integer hWnd = null;
    private User32 user32 = User32.INSTANCE;


    private final boolean capture = true;//不能改为false

    private boolean anylyse = true;
    private boolean any_drawLine = true;
    private boolean any_drawPoints = true;
    private boolean rec_table = false;
    private boolean use_offSet = true;
    private boolean mouse_press_duration= true;
    private boolean mouse_press_interval= true;

    private com.jiangli.graphics.common.Color MATCH_COLOR = new Color(0,0,0);
    private Color CLICK_POINT_COLOR = new Color(255,0,0);
    private int CLICK_POINT_LENGTH = 50;

    private Robot robot;
    private Rect offset = new Rect(580,210,400,400);
    protected RectPercentage offsetPercentage = new RectPercentage(27.00,18.50,18.00,32.00);
    private PointFilter pointFilter = new RmoveDuplicatePointFilter(20);
    private JMenuItem jmiEnableAnalyse = new JCheckBoxMenuItem("开启分析");
    private JMenuItem jmiEnableAnalyseDrawCross = new JCheckBoxMenuItem("开启分析-描线");
    private JMenuItem jmiEnableUseOffSet = new JCheckBoxMenuItem("启用截图偏移");
    private JMenuItem jmiEnableTableRec = new JCheckBoxMenuItem("记录表");
    private JMenuItem jmiEnableMousePressing = new JCheckBoxMenuItem("鼠标按住");
    private JMenuItem jmiEnableMouseInterval = new JCheckBoxMenuItem("鼠标点击间隔");

    private JTextField jtfOffSetRectLeft = new JTextField();
    private JTextField jtfOffSetRectTop = new JTextField();
    private JTextField jtfOffSetRectWidth = new JTextField();
    private JTextField jtfOffSetRectLength = new JTextField();

    protected void setMousePressDuration(int start, int end){
        this.mouse_press_duration_start = start;
        this.mouse_press_duration_end = end;
    }

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

        childrenInitialStart();

        //paint main
        Container root = getContentPane();
        paintMenu();
        paintUI(root);

        btnFire.addActionListener(new BtnFireAction(root));
        btnHwnd.addActionListener(new OpenHwndAction());
        btnOpenCapture.addActionListener(new OpenCaptureAction());
        btnDeleteCapture.addActionListener(new DeleteCaptureAction());
        btnOpenAnalyse.addActionListener(new OpenAnalyseAction());
        btnDeleteAnalyse.addActionListener(new DeleteAnalyseAction());

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

        btnTestCapture.addActionListener(new TestCaptureAction());
        btnTestSample.addActionListener(new TestSampleAction());
        btnAnalyseDir.addActionListener(new AnalyseDirAction());
        btnReloadThread.addActionListener(new ReloadThreadAction());
        btnClearConsole.addActionListener(new ClearConsoleAction());

        jtfHwnd.addKeyListener(new RefreshHwndAction());
        jtfTitleStr.addKeyListener(new RefreshHwndAction());
        jtfSimilar.addKeyListener(new SimilarRefreshAction());

        //init property
        jtaConsole.setEditable(false);
        jtaConsole.setAutoscrolls(true);
        jtbFires.setAutoscrolls(true);
        jtfSimilar.setText(getSimilarityString());

        //table
        DefaultTableModel tableModel = new DefaultTableModel(null,columnNames);
        jtbFires.setModel(tableModel);
        TableCellRenderer cellRenderer = new ImgPathCellRenderer();
        jtbFires.getColumnModel().getColumn(1).setCellRenderer(cellRenderer);
        jtbFires.getColumnModel().getColumn(2).setCellRenderer(cellRenderer);
        jtbFires.getColumnModel().getColumn(1).setCellEditor(new ImgPathButtonEditor());
        jtbFires.getColumnModel().getColumn(2).setCellEditor(new ImgPathButtonEditor());

        //init instance
        robot = new Robot();
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
        new TextFObjectFRefershBinding(jtfOffSetRectLeft, offsetPercentage, "left", doubleStringFormatter, perToAbsLeft, new LogOffsetAction());
        new TextFObjectFRefershBinding(jtfOffSetRectTop,offsetPercentage,"top",doubleStringFormatter,perToAbsTop, new LogOffsetAction());
        new TextFObjectFRefershBinding(jtfOffSetRectWidth,offsetPercentage,"width",doubleStringFormatter,perToAbsWidth, new LogOffsetAction());
        new TextFObjectFRefershBinding(jtfOffSetRectLength,offsetPercentage,"height",doubleStringFormatter,perToAbsHeight, new LogOffsetAction());


        //lazy last
        refreshHwnd();

        //set style
        SwingUtil.setCommonFrameStyle(this);

        childrenInitialEnd();
    }

    public abstract FindSmileDirAnalyser getDirAnalyser() ;

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
        actionPanel.add(btnClearConsole);

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
                    File file = HwndUtil.shortCut(hWnd, captured_path,getOffSet());
                    timeAnalyser.setTitle(file.getAbsolutePath());
                    record[1] = file.getAbsolutePath();
                    bmp =  new BMP(file);
                } else {
                    bmp =HwndUtil.captureAndGetBMP(hWnd,getOffSet());
                }

                timeAnalyser.push("get BMP");

                List<Point> points = mathcer.match(bmp, similartity);
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
                        File outFile = new File(anylyse_path + "\\" + bmp.getFile().getName());
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
                int pCount = points.size();
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
                record[0] = points.size()+"";

                timeAnalyser.push("click points================>"+points.size() + " "+points);

                //log
                log(timeAnalyser.analyse());


                //record
                if (rec_table) {
                    DefaultTableModel model = (DefaultTableModel) jtbFires.getModel();
                    model.addRow(record);
                }

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
            BMP bmp = new BMP(PathUtil.buildPath(sample_path,"gaming.bmp"));
            File file = DrawUtil.drawRect(bmp, offset, new Color(255, 0, 0));
            FileUtil.openPicture(file);
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
}

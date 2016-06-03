package com.jiangli.jni.app.richman9.smallgame.findsmile;

import com.jiangli.graphics.common.*;
import com.jiangli.graphics.common.Color;
import com.jiangli.graphics.common.Point;
import com.jiangli.graphics.impl.RmoveDuplicatePointFilter;
import com.jiangli.graphics.inf.BMPMatcher;
import com.jiangli.graphics.inf.PointFilter;
import com.jiangli.jni.app.impl.FindSmileJavaCVThreadMathcer;
import com.jiangli.jni.common.*;
import com.jiangli.jni.core.User32;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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

    private JTextArea jtaConsole = new JTextArea("");
    private JButton btnFire = new JButton("点击笑脸");

    private Integer hWnd = null;
    private User32 user32 = User32.INSTANCE;

//    private SmileAnylyser anylyser = new SmileAnylyser(Config.characteristic_path);
    private BMPMatcher mathcer  = new FindSmileJavaCVThreadMathcer();

    private boolean capture = true;//不能改为false
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

    public void log(String msg) {
        jtaConsole.setText(jtaConsole.getText()+"\r\n"+msg);
    }

    public AnylyseAndClickWindow() throws HeadlessException {
        this.setVisible(true);
        this.setTitle("找笑脸");
        this.setSize(400, 300);
        setFrameMiddle();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setDefaultLookAndFeelDecorated(false);
        jtaConsole.setEditable(false);
        try {
            robot = new Robot();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Container root = getContentPane();

        paintUI(root);

        btnFire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    rereshHwnd();
                    btnFire.setEnabled(false);

                    BMP bmp = null;
                    if (capture) {
                        logger.debug("capture enabled");
                        File file = HwndUtil.shortCut(hWnd, Config.capture_path,offset);
                        bmp =  new BMP(file);
                    } else {
                        bmp =HwndUtil.captureAndGetBMP(hWnd,offset);
                    }


                    List<Point> points = mathcer.match(bmp, SIMILARITY);

                    if (anylyse) {
                        logger.debug("anylyse enabled");

                        if (any_drawPoints) {
                            logger.debug("any_drawPoints enabled");
                            //draw point
                            for (Point point : points) {
                                DrawUtil.drawPointCross(bmp, point, CLICK_POINT_LENGTH, CLICK_POINT_COLOR);
                            }
                        }


                        //write
                        try {
                            File outFile = new File(Config.anylyse_path + "\\" + bmp.getFile().getName());
                            DrawUtil.writeFile(bmp, outFile);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }

                    logger.debug("start clicking..");
                    logger.debug("clickPoints:"+points.size());


                    //remove duplicate
                    pointFilter.filter(points);

                    //convert offset
                    for (Point point : points) {
                        point.setX(point.getX() + offset.getX());
                        point.setY(point.getY() + offset.getY());
                    }


                    for (Point point : points) {
//                        Mouse.click(hWnd, point.getX(), point.getY());
                        Mouse.pressByRobot(hWnd,robot,point);

//                        Thread.sleep(seed.nextInt(CLICK_INTERVAL));
                    }


                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(root, ex.getMessage());
                } finally {
                    btnFire.setEnabled(true);
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

        final JPanel actionPanel = new JPanel(new BorderLayout());
        actionPanel.add(jtaConsole, BorderLayout.CENTER);
//        actionPanel.add(jtaConsole);
        actionPanel.add(btnFire, BorderLayout.PAGE_END);
//        actionPanel.add(btnFire);

        root.setLayout(new GridLayout(2,1));
        root.add(formPanel);
//        root.add(formPanel, BorderLayout.CENTER);
        root.add(actionPanel);
//        root.add(actionPanel, BorderLayout.PAGE_END);
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
        AnylyseAndClickWindow captureWindow = new AnylyseAndClickWindow();
    }
}

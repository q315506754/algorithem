package com.jiangli.jni.app.richman9.smallgame.findsmile;

import com.jiangli.jni.common.*;
import com.jiangli.jni.common.Point;
import com.jiangli.jni.core.User32;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Random;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/1 0001 13:26
 */
public class AnylyseAndClickWindow extends JFrame {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private JTextField jtfHwnd = new JTextField("460564");
    private JLabel jlbHwnd = new JLabel("句柄");

    private JTextField jtfTitleStr = new JTextField("大富翁9");
    private JLabel jlbTitleStr = new JLabel("标题");

    private JTextArea jtaConsole = new JTextArea("");
    private JButton btnFire = new JButton("点击笑脸");

    private Integer hWnd = null;
    private User32 user32 = User32.INSTANCE;

    private SmileAnylyser anylyser = new SmileAnylyser(Config.characteristic_path);
    private boolean capture = true;
    private boolean anylyse = true;
    private boolean any_drawLine = true;
    private boolean any_drawPoints = true;
    private com.jiangli.jni.common.Color MATCH_COLOR = new com.jiangli.jni.common.Color(0,0,0);
    private com.jiangli.jni.common.Color CLICK_POINT_COLOR = new com.jiangli.jni.common.Color(0,0,0);
    private int CLICK_POINT_LENGTH = 5;
    private int CLICK_INTERVAL = 200;
    private Random seed = new Random();

    public AnylyseAndClickWindow() throws HeadlessException {
        this.setVisible(true);
        this.setTitle("找笑脸");
        this.setSize(400, 300);
        setFrameMiddle();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setDefaultLookAndFeelDecorated(false);
        jtaConsole.setEditable(false);

        Container root = getContentPane();

        paintUI(root);

        btnFire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    rereshHwnd();
                    BMP bmp = null;
                    if (capture) {
                        logger.debug("capture enabled");
                        File file = HwndUtil.shortCut(hWnd, Config.capture_path);
                        bmp =  new BMP(file);
                    } else {
                        bmp =HwndUtil.captureAndGetBMP(hWnd);
                    }

                    List<Rect> rects = anylyser.searchRect(bmp);
                    SmileAnylyser.ClickPoints clickPoints = anylyser.getClickPoints(rects);

                    if (anylyse) {
                        logger.debug("anylyse enabled");
                        if (any_drawLine) {
                            logger.debug("any_drawLine enabled");
                            //draw
                            for (Rect rect : rects) {
                                for (int i = rect.getX(); i < rect.getX() + rect.getWidth(); i++) {
                                    bmp.setColorObj(i,rect.getY(),MATCH_COLOR);
                                    bmp.setColorObj(i, rect.getY() + rect.getLength(), MATCH_COLOR);
                                }
                                for (int j = rect.getY(); j < rect.getY() + rect.getLength(); j++) {
                                    bmp.setColorObj(rect.getX(),j,MATCH_COLOR);
                                    bmp.setColorObj(rect.getX() + rect.getWidth(), j, MATCH_COLOR);
                                }
                            }
                        }

                        if (any_drawPoints) {
                            logger.debug("any_drawPoints enabled");
                            //draw point
                            for (com.jiangli.jni.common.Point point : clickPoints.pointList) {
                                for (int i = point.getX() - CLICK_POINT_LENGTH; i < point.getX() + CLICK_POINT_LENGTH; i++) {
                                    bmp.setColorObj(i,point.getY(),CLICK_POINT_COLOR);
                                }
                                for (int i = point.getY() - CLICK_POINT_LENGTH; i < point.getY() + CLICK_POINT_LENGTH; i++) {
                                    bmp.setColorObj(point.getX(),i,CLICK_POINT_COLOR);
                                }
                            }
                        }


                        //write
                        try {
                            logger.debug("start writing anylysed file");
                            BufferedImage repainted = ImageIO.read(new ByteArrayInputStream(bmp.getData()));
                            File dir = new File(Config.anylyse_path);
                            if (!dir.exists()) {
                                dir.mkdirs();
                            }
                            String name = null;
                            if (bmp.getFile() != null) {
                                name = bmp.getFile().getName();
                            } else {
                                name = HwndUtil.generateName(hWnd)+".bmp";
                            }
                            File outFile = new File(Config.anylyse_path+"\\"+ name);
                            outFile.createNewFile();
                            FileOutputStream output = new FileOutputStream(outFile);
                            ImageIO.write(repainted, "bmp", output);
                            output.flush();
                            output.close();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }

                    logger.debug("start clicking..");
                    logger.debug("clickPoints:"+clickPoints.pointList.size());
                    for (Point point : clickPoints.pointList) {
                        Mouse.click(hWnd, point.getX(), point.getY());

                        Thread.sleep(seed.nextInt(CLICK_INTERVAL));
                    }
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

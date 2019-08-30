package com.jiangli.common.utils;

import com.jiangli.common.core.FileStringProcesser;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author Jiangli
 * <p>
 * CreatedTime  2016/6/2 0002 17:55
 */
public class FileUtil {
    public static String SYSTEM_DELIMETER = "\r\n";

    public static void processVisit(File src, FileStringProcesser processer) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(src)));
            String line = null;

            while ((line = br.readLine()) != null) {
                processer.process(line);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //public static void processVisit(File src, FileStringProcesser processer) {
    //
    //}
    public static File process(File src, FileStringProcesser processer) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(src)));
            File outFile = File.createTempFile(getPrefix(src) + System.currentTimeMillis(), getSuffix(src));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
            String line = null;

            while ((line = br.readLine()) != null) {
                String processedLine = processer.process(line);
                bw.write(processedLine + SYSTEM_DELIMETER);
            }
            bw.flush();
            bw.close();
            br.close();
            return outFile;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String desktop() {
        FileSystemView view = FileSystemView.getFileSystemView();
        File homeDirectory = view.getHomeDirectory();
        return homeDirectory.getPath();
    }

    public static File processAndReplace(File src, FileStringProcesser processer) {
        File processed = process(src, processer);
        try {
            FileInputStream fileInputStream = new FileInputStream(processed);
            FileOutputStream fileOutputStream = new FileOutputStream(src);
            IOUtils.copy(fileInputStream, fileOutputStream);
            fileInputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return src;
    }


    public static String getPrefix(File src) {
        return getPrefix(src.getName());
    }

    public static String getPrefix(String name) {
        return name.substring(0, name.lastIndexOf("."));
    }

    public static String getSuffix(File src) {
        return getSuffix(src.getName());
    }

    public static String getSuffix(String name) {
        return name.substring(name.lastIndexOf("."));
    }

    public static File getNoDupfile(File src) {
        String name = src.getName();
        if (src.exists()) {
            int i = 2;

            while (true) {
                String prefix = getPrefix(name) + "_" + i++;
                String suffix = getSuffix(name);
                String path = prefix + suffix;
                File ret = new File(src.getParentFile(), path);
                if (!ret.exists()) {
                    return ret;
                }
            }
        }
        return null;
    }

    public static List<String> getFilePathFromDirPath(String dirPath) {
        return getFilePathFromDirPath(dirPath, false);
    }

    public static List<String> getFilePathFromDirPath(String dirPath, boolean includeChildren) {
        List<String> paths = new LinkedList<>();

        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                paths.add(file.getPath());
            } else if (file.isDirectory() && includeChildren) {
                paths.addAll(getFilePathFromDirPath(file.getPath(), true));
            }
        }
        return paths;
    }

    public static List<File> getFilesFromDirPath(String dirPath, Predicate<File> filePredicate) {
        List<File> paths = new LinkedList<>();

        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                boolean collect = true;
                if (filePredicate != null) {
                    collect = filePredicate.test(file);
                }
                if (collect)
                    paths.add(file);
            } else if (file.isDirectory()) {
                paths.addAll(getFilesFromDirPath(file.getPath(), filePredicate));
            }
        }
        return paths;
    }

    public static void openPicture(File file) {
        try {
            Runtime.getRuntime().exec("mspaint \"" + file.getAbsolutePath() + "\"");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openFile(File file) {
        try {
            Runtime.getRuntime().exec("notepad \"" + file.getAbsolutePath() + "\"");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openPicture(String path) {
        openPicture(new File(path));
    }

    public static void openDirectory(File file) {
        try {
            Runtime.getRuntime().exec("explorer.exe \"" + file.getAbsolutePath() + "\"");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File generateTemp(String suffix) {
        try {
            return File.createTempFile(System.currentTimeMillis() + "", suffix);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void openDirectory(String path) {
        openDirectory(new File(path));
    }

    public static int deleteFilesUnderDir(String dirPath) {
        File dir = new File(dirPath);
        int count = 0;
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    file.delete();
                    count++;
                }
            }
        }
        return count;

    }

    public static int deleteUnderDir(String dirPath) {
        File dir = new File(dirPath);
        int count = 0;
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    count += deleteUnderDir(file.getAbsolutePath());
                    file.delete();
                } else {
                    file.delete();
                    count++;
                }
            }
        }
        return count;

    }

    public static void acceptDragFile(boolean close, Function<List<File>,String> consumer) {
        new DragFileDemo(consumer, close);
    }

    public static void main(String[] args) {
        acceptDragFile(true,files -> {
            System.out.println(files);
            return null;
        });
    }


    public static class DragFileDemo extends JFrame {
        public DragFileDemo(Function<List<File>,String> consumer, boolean closeOnReceive) {
            super("文件选择器");

            final JTextArea area = new JTextArea();
            area.setLineWrap(true);
            add(new JScrollPane(area));

            //拖拽事件
            new DropTarget(area, DnDConstants.ACTION_COPY_OR_MOVE,
                    new DropTargetAdapter() {
                        @Override
                        public void drop(DropTargetDropEvent dtde) {
                            try {

                                boolean recieved = false;
                                List<File> list = null;

                                // 如果拖入的文件格式受支持
                                if (dtde.isDataFlavorSupported(DataFlavor.imageFlavor)) {
                                    // 接收拖拽来的数据
                                    dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);

                                    try {
                                        File tempFile = File.createTempFile(System.currentTimeMillis() + "", ".png");

                                        BufferedImage transferData = (BufferedImage) (dtde.getTransferable().getTransferData(DataFlavor.imageFlavor));

                                        //BufferedImage read = ImageIO.read(ImageIO.createImageInputStream(transferData));
                                        ImageIO.write(transferData, "png", tempFile);
                                        list = Arrays.asList(tempFile);

                                        recieved = true;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    // 指示拖拽操作已完成
                                    dtde.dropComplete(true);
                                }  else if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                                    // 接收拖拽来的数据
                                    dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                                    list = (List<File>) (dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor));


                                    // 指示拖拽操作已完成
                                    dtde.dropComplete(true);

                                    recieved = true;

                                } else if (dtde.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                                    // 接收拖拽来的数据
                                    dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                                    String transferData = (String) (dtde.getTransferable().getTransferData(DataFlavor.stringFlavor));

                                    //area.setText(transferData);
                                    File file = new File(transferData);

                                    if (file.exists()) {
                                        list = Arrays.asList(file);

                                        recieved = true;
                                    }

                                    // 指示拖拽操作已完成
                                    dtde.dropComplete(true);
                                } else {
                                    // 拒绝拖拽来的数据
                                    dtde.rejectDrop();
                                }

                                if (recieved) {
                                    if (closeOnReceive) {
                                        DragFileDemo.this.dispose();
                                    }

                                    if (list != null) {
                                        List<File> oldList = list;
                                        List<File> newList = new ArrayList<>();

                                        for (File file : oldList) {
                                            if (file.isDirectory()) {
                                                File[] files = file.listFiles();
                                                if (files != null) {
                                                    for (File o : files) {
                                                        if (o.isFile()) {
                                                            newList.add(o);
                                                        }
                                                    }
                                                }
                                            } else {
                                                newList.add(file);
                                            }
                                        }

                                        list = newList;


                                        //display
                                        area.setText("");
                                        for (File file : list) {
                                            area.append(file.getAbsolutePath());
                                            area.append("\r\n");
                                        }

                                        String apply = consumer.apply(list);
                                        if (apply != null) {
                                            area.setText(apply);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

            setSize(300, 300);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setVisible(true);
        }
    }
}

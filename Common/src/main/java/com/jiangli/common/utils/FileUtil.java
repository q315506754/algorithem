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
import java.net.URL;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author Jiangli
 * <p>
 * CreatedTime  2016/6/2 0002 17:55
 */
public class FileUtil {
    public static String SYSTEM_DELIMETER = "\r\n";


    public static void writeStr(String str, String file) {
        try {
            File f = new File(file);
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(f);
            IOUtils.write(str, fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void writeBytes(byte[] str, String file) {
        try {
            File f = new File(file);
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(f);
            IOUtils.write(str, fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    public static void acceptDragFile(boolean close, Function<List<File>, String> consumer) {
        new DragFileDemo(consumer, close);
    }

    public static void downloadM3U8(String url, String outdir) {
        String body = downloadBody(url);
        boolean skipDownload = false;
        //boolean skipDownload = true;

        boolean deleteTemp = false;
        //boolean deleteTemp = true;

        String prefix = url.substring(0, url.lastIndexOf("/"));
        String name = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
        System.out.println(body);

        //初始化文件路径
        PathUtil.ensurePath(outdir);
        File outDir = new File(outdir);
        File outTsFile = new File(outDir, name + ".ts");
        tryDelete(outTsFile);

        //File tempDir = new File(outDir,name+"-"+System.currentTimeMillis()+"");
        File tempDir = new File(outDir, name + "");

        File outMp4File = new File(tempDir, name + ".mp4");
        File m3u8File = new File(tempDir, "index.m3u8");
        File keyFile = new File(tempDir, "key.key");
        File batchFile = new File(tempDir, "run.bat");

        tryDelete(outMp4File);
        tryDelete(m3u8File);
        tryDelete(keyFile);
        tryDelete(batchFile);

        PathUtil.ensurePath(tempDir.getAbsolutePath());
        if (!skipDownload && deleteTemp) {
            deleteUnderDir(tempDir.getAbsolutePath());
        }


        //解析文件
        List<ParsedResult> downUrls = parseDownloadUrls(body, prefix);
        Map<String, String> encryptionUrl = parseEncryptionUrl(body, prefix);
        String encMethod = null;
        byte[] encKey = null;

        //秘钥信息
        if (encryptionUrl != null) {
            encMethod = encryptionUrl.get("METHOD");
            String URI = encryptionUrl.get("URI");

            //无效
            body = body.replaceAll(URI, keyFile.getName());

            //请求秘钥
            try {
                InputStream inputStream = new URL(URI).openStream();
                encKey = IOUtils.toByteArray(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //处理body
        body = processLine(body,s -> {
            if (s.startsWith("#EXT-X-KEY:")) {
                return "#EXT-X-KEY:METHOD=AES-128,URI=\"key.key\"";
            }
            return s;
        });

        //生成临时文件
        FileUtil.writeStr(body,m3u8File.getAbsolutePath());
        if (encKey != null) {
            FileUtil.writeBytes(encKey,keyFile.getAbsolutePath());
        }

        String command = "";
        command+="\ncd "+tempDir.getAbsolutePath();
        command+="\nffmpeg -y -allowed_extensions ALL -protocol_whitelist \"file,http,https,rtp,udp,tcp,tls,crypto\" -i \"" + m3u8File.getName() + "\" -c copy -bsf:a aac_adtstoasc \"" + outMp4File.getName() +"\"";
        FileUtil.writeStr(command,batchFile.getAbsolutePath());

        //计算输出路径
        for (ParsedResult downDto : downUrls) {
            String downUrl = downDto.url;
            //System.out.println((count.incrementAndGet())+ "/"+downUrls.size());
            String downLoadFileName = downUrl.substring(downUrl.lastIndexOf("/") + 1);
            String outOnePath = PathUtil.buildPath(tempDir.getAbsolutePath(), false, downLoadFileName);
            downDto.filePath = outOnePath;
        }

        //    download
        //    int count = 0;
        if (!skipDownload) {
            AtomicInteger count = new AtomicInteger(0);
            ExecutorService pool = Executors.newFixedThreadPool(10);
            CountDownLatch countDownLatch = new CountDownLatch(downUrls.size());
            System.out.println("start download...");
            long currentTimeMillis = System.currentTimeMillis();

            for (ParsedResult downDto : downUrls) {
                String downUrl = downDto.url;

                byte[] finalEncKey = encKey;
                pool.execute(() -> {
                    //if (finalEncKey == null) {
                    boolean exists = new File(downDto.filePath).exists();
                    if (!exists) {
                        download(downUrl, downDto.filePath);
                    }
                    //} else {
                    //    byte[] content = downloadByte(downUrl);
                    //    javax.crypto.spec.IvParameterSpec ips = new javax.crypto.spec.IvParameterSpec(String.format("%016x", downDto.idx).getBytes());
                    //    ips = null;
                    //
                    //    try {
                    //        content = AESUtil.decrypt(content, finalEncKey, ips);
                    //    } catch (Exception e) {
                    //        e.printStackTrace();
                    //    }
                    //
                    //    try {
                    //        FileOutputStream output = new FileOutputStream(downDto.filePath);
                    //        IOUtils.write(content, output);
                    //        output.close();
                    //    } catch (Exception e) {
                    //        e.printStackTrace();
                    //    }
                    //}
                    System.out.println((count.incrementAndGet()) + "/" + downUrls.size() + (exists?" skipped ":"") +" " + downUrl + " ===> " + downDto.filePath);

                    countDownLatch.countDown();
                });
            }

            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long cost = System.currentTimeMillis() - currentTimeMillis;

            System.out.println("download finish . cost:" + cost / 1000 + " s");

            //不关则会阻塞main线程
            pool.shutdown();
        }

        //    combine
        //try {
        //    System.out.println("合并..."+outTsFile.getAbsolutePath() + " <= " +tempDir.getAbsolutePath()+"\\*.ts");
        //    FileOutputStream fileOutputStream = new FileOutputStream(outTsFile);
        //    for (ParsedResult downUrl : downUrls) {
        //        IOUtils.copy(new FileInputStream(downUrl.filePath),fileOutputStream);
        //    }
        //    fileOutputStream.close();
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}

    //    转码 FFmpeg
    //    ffmpeg -y -i 365eaf317efa4e72bf3c5735221aeafe.ts -c:v libx264 -c:a copy -bsf:a aac_adtstoasc output.mp4
    //    ffmpeg -allowed_extensions ALL -i index.m3u8  -c copy -bsf:a aac_adtstoasc ALL.mp4
        try {
            //String command = "ffmpeg -y -allowed_extensions ALL -i \"" + m3u8File.getAbsolutePath() + "\" -c copy -bsf:a aac_adtstoasc \"" + outMp4File.getAbsolutePath() +"\"";
            //String command = batchFile.getAbsolutePath();
            System.out.println("转码中...");
            //System.out.println(command);
            Process exec = Runtime.getRuntime().exec("cmd.exe /c start "+batchFile.getAbsolutePath());
            //InputStream inputStream = exec.getInputStream();
            //Thread thread = new Thread(() -> {
            //    try {
            //        IOUtils.copy(inputStream, System.out);
            //    } catch (Exception e) {
            //        e.printStackTrace();
            //    }
            //});
            ////thread.setDaemon(true);
            //thread.start();
            exec.waitFor();
            System.out.println("转码结束..."+outMp4File.getAbsolutePath());
            //fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String processLine(String body,Function<String,String> fc) {
        StringBuilder sb = new StringBuilder();

        String[] split = body.split("\n");
        for (String s : split) {
            sb.append(fc.apply(s));
            sb.append("\n");
        }

        return sb.toString();
    }

    private static void tryDelete(File outTsFile) {
        if (outTsFile!=null && outTsFile.exists()) {
            outTsFile.delete();
        }
    }

    static class ParsedResult {
        int idx;
        String url;
        String filePath;

        public ParsedResult(int idx, String url) {
            this.idx = idx;
            this.url = url;
        }
    }

    private static List<ParsedResult> parseDownloadUrls(String body, String prefix) {
        List<ParsedResult> downUrls = new ArrayList<>();
        String[] split = body.split("\n");
        int count = 0;
        for (String s : split) {
            if (!s.startsWith("#") && !s.trim().equals("")) {
                if (s.startsWith("http")) {
                    downUrls.add(new ParsedResult(count++, s));
                } else {
                    downUrls.add(new ParsedResult(count++, prefix + "/" + s));
                }
            }
        }
        return downUrls;
    }

    private static Map<String, String> parseEncryptionUrl(String body, String url_prefix) {
        Map<String, String> ret = new HashMap<>();
        String[] split = body.split("\n");
        for (String s : split) {
            String prefix = "#EXT-X-KEY:";
            if (s.startsWith(prefix)) {
                String line = s.substring(prefix.length());
                String methodKV = line.substring(0, line.indexOf(","));
                String uriKV = line.substring(line.indexOf(",") + 1);
                uriKV = uriKV.replaceAll("\"", "");

                parseKv(ret, methodKV);
                parseKv(ret, uriKV);

                String URI = ret.get("URI");
                if (!URI.startsWith("http")) {
                    URI = url_prefix+"/"+URI;
                    ret.put("URI",URI);
                }

                return ret;
            }
        }
        return null;
    }

    private static void parseKv(Map<String, String> ret, String methodKV) {
        int i = methodKV.indexOf("=");
        ret.put(methodKV.substring(0, i), methodKV.substring(i + 1));
    }

    public static String downloadBody(String url) {
        try {
            URL url1 = new URL(url);
            InputStream inputStream = url1.openStream();
            return IOUtils.toString(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] downloadByte(String url) {
        try {
            URL url1 = new URL(url);
            return IOUtils.toByteArray(url1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void download(String url, String out) {
        try {
            URL url1 = new URL(url);
            InputStream inputStream = url1.openStream();

            File outFile = new File(out);
            if (!outFile.exists()) {
                outFile.createNewFile();
            }

            FileOutputStream output = new FileOutputStream(outFile);
            IOUtils.copy(inputStream, output);
            inputStream.close();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(String.format("%032x", 3));
        System.out.println(String.format("%032x", 3).getBytes().length);

        //downloadM3U8("https://cdn.kuyunbo.club/20170930/FHLkCRSr/hls/index.m3u8","C:\\Users\\Jiangli\\Videos");
        String outdir = "C:\\Users\\Jiangli\\Videos";

        //FileUtil.openDirectory(outdir);
        downloadM3U8("http://aries-video.g2s.cn/zhs_yanfa_150820/ablecommons/demo/201912/365eaf317efa4e72bf3c5735221aeafe.m3u8?MtsHlsUriToken=zxcvbn", outdir);
        //downloadM3U8("https://cdn.kuyunbo.club/20170930/FHLkCRSr/hls/index.m3u8", outdir);

        //download("https://cdn.kuyunbo.club/20170930/FHLkCRSr/hls/swPd4537702.ts","C:\\Users\\Jiangli\\Videos/swPd4537702.ts");

        //acceptDragFile(true, files -> {
        //    System.out.println(files);
        //    return null;
        //});
    }


    public static class DragFileDemo extends JFrame {
        public DragFileDemo(Function<List<File>, String> consumer, boolean closeOnReceive) {
            super("文件选择器");

            final JTextArea area = new JTextArea();
            area.setLineWrap(true);
            add(new JScrollPane(area));
            area.setLineWrap(true);

            ExecutorService executorService = Executors.newFixedThreadPool(1);

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
                                } else if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
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
                                        area.append("等待进一步结果...\r\n");

                                        List<File> finalList = list;
                                        executorService.submit(() -> {
                                                    String apply = consumer.apply(finalList);
                                                    if (apply != null) {
                                                        area.setText(apply);
                                                    } else {
                                                        area.append("执行结束...\r\n");
                                                    }
                                                }
                                        );

                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

            setSize(1200, 300);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setVisible(true);
        }
    }
}

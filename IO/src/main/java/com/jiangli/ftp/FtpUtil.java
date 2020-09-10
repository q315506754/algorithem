package com.jiangli.ftp;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author zbf
 * @create 2019/3/1 9:37
 **/
public class FtpUtil {

    /**
     * 日志对象
     **/
    private static final Logger LOGGER = LoggerFactory.getLogger(FtpUtil.class);

    //private static Properties ftpProperties = PropertiesUtil.quartzProperties("ftp.properties");
    /**
     * FTP基础目录
     **/
    //private static final String BASE_PATH = ftpProperties.getProperty("ftp.path");
    private static final String BASE_PATH = "/home/ftp";

    /**
     * 本地字符编码
     **/
    private static String localCharset = "GBK";

    /**
     * FTP协议里面，规定文件名编码为iso-8859-1
     **/
    private static String serverCharset = "ISO-8859-1";

    /**
     * UTF-8字符编码
     **/
    private static final String CHARSET_UTF8 = "UTF-8";

    /**
     * OPTS UTF8字符串常量
     **/
    private static final String OPTS_UTF8 = "OPTS UTF8";

    /**
     * 设置缓冲区大小4M
     **/
    private static final int BUFFER_SIZE = 1024 * 1024 * 4;

    /**
     * 本地文件上传到FTP服务器
     *
     * @param ftpPath  FTP服务器文件相对路径，例如：test/123
     * @param savePath 本地文件路径，例如：D:/test/123/test.txt
     * @param fileName 上传到FTP服务的文件名，例如：666.txt
     * @return boolean 成功返回true，否则返回false
     */
    public boolean uploadLocalFile(String ftpPath, String savePath, String fileName) {
        // 登录
        FTPClient ftpClient = login();
        boolean flag = false;
        if (ftpClient != null) {
            File file = new File(savePath);
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                ftpClient.setBufferSize(BUFFER_SIZE);
                // 设置编码：开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）
                if (FTPReply.isPositiveCompletion(ftpClient.sendCommand(OPTS_UTF8, "ON"))) {
                    localCharset = CHARSET_UTF8;
                }
                ftpClient.setControlEncoding(localCharset);
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                String path = changeEncoding(BASE_PATH + ftpPath, ftpClient);
                // 目录不存在，则递归创建
                if (!ftpClient.changeWorkingDirectory(path)) {
                    this.createDirectorys(path, ftpClient);
                }
                // 设置被动模式，开通一个端口来传输数据
                ftpClient.enterLocalPassiveMode();
                // 上传文件
                flag = ftpClient.storeFile(new String(fileName.getBytes(localCharset), serverCharset), fis);
            } catch (Exception e) {
                LOGGER.error("本地文件上传FTP失败", e);
            } finally {
                IOUtils.closeQuietly(fis);
                closeConnect(ftpClient);
            }
        }
        return flag;
    }

    /**
     * 远程文件上传到FTP服务器
     *
     * @param ftpPath    FTP服务器文件相对路径，例如：test/123
     * @param remotePath 远程文件路径，例如：http://www.baidu.com/xxx/xxx.jpg
     * @param fileName   上传到FTP服务的文件名，例如：test.jpg
     * @return boolean 成功返回true，否则返回false
     */
    //public boolean uploadRemoteFile(String ftpPath, String remotePath, String fileName) {
    //    // 登录
    //    FTPClient ftpClient=login();
    //    boolean flag = false;
    //    if (ftpClient != null) {
    //        CloseableHttpClient httpClient = HttpClients.createDefault();
    //        CloseableHttpResponse response = null;
    //        try {
    //            // 远程获取文件输入流
    //            HttpGet httpget = new HttpGet(remotePath);
    //            response = httpClient.execute(httpget);
    //            HttpEntity entity = response.getEntity();
    //            InputStream input = entity.getContent();
    //            ftpClient.setBufferSize(BUFFER_SIZE);
    //            // 设置编码：开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）
    //            if (FTPReply.isPositiveCompletion(ftpClient.sendCommand(OPTS_UTF8, "ON"))) {
    //                localCharset = CHARSET_UTF8;
    //            }
    //            ftpClient.setControlEncoding(localCharset);
    //            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
    //            String path = changeEncoding(BASE_PATH + ftpPath,ftpClient);
    //            // 目录不存在，则递归创建
    //            if (!ftpClient.changeWorkingDirectory(path)) {
    //                this.createDirectorys(path,ftpClient);
    //            }
    //            // 设置被动模式，开通一个端口来传输数据
    //            ftpClient.enterLocalPassiveMode();
    //            // 上传文件
    //            flag = ftpClient.storeFile(new String(fileName.getBytes(localCharset), serverCharset), input);
    //        } catch (Exception e) {
    //            LOGGER.error("远程文件上传FTP失败", e);
    //        } finally {
    //            closeConnect(ftpClient);
    //            try {
    //                httpClient.close();
    //            } catch (IOException e) {
    //                LOGGER.error("关闭流失败", e);
    //            }
    //            if (response != null) {
    //                try {
    //                    response.close();
    //                } catch (IOException e) {
    //                    LOGGER.error("关闭流失败", e);
    //                }
    //            }
    //        }
    //    }
    //    return flag;
    //}

    /**
     * 下载指定文件到本地
     *
     * @param ftpPath  FTP服务器文件相对路径，例如：test/123
     * @param fileName 要下载的文件名，例如：test.txt
     * @param savePath 保存文件到本地的路径，例如：D:/test
     * @return 成功返回true，否则返回false
     */
    public boolean downloadFile(String ftpPath, String fileName, String savePath) {
        // 登录
        FTPClient ftpClient = login();
        boolean flag = false;
        if (ftpClient != null) {
            try {
                String path = changeEncoding(BASE_PATH + ftpPath, ftpClient);
                // 判断是否存在该目录
                if (!ftpClient.changeWorkingDirectory(path)) {
                    LOGGER.error(BASE_PATH + ftpPath + "该目录不存在");
                    return flag;
                }
                ftpClient.enterLocalPassiveMode();  // 设置被动模式，开通一个端口来传输数据
                String[] fs = ftpClient.listNames();
                // 判断该目录下是否有文件
                if (fs == null || fs.length == 0) {
                    LOGGER.error(BASE_PATH + ftpPath + "该目录下没有文件");
                    return flag;
                }
                for (String ff : fs) {
                    String ftpName = new String(ff.getBytes(serverCharset), localCharset);
                    if (ftpName.equals(fileName)) {
                        File file = new File(savePath + '/' + ftpName);
                        try (OutputStream os = new FileOutputStream(file)) {
                            flag = ftpClient.retrieveFile(ff, os);
                        } catch (Exception e) {
                            LOGGER.error(e.getMessage(), e);
                        }
                        break;
                    }
                }
            } catch (IOException e) {
                LOGGER.error("下载文件失败", e);
            } finally {
                closeConnect(ftpClient);
            }
        }
        return flag;
    }

    /**
     * 下载该目录下所有文件到本地
     *
     * @param ftpPath  FTP服务器上的相对路径，例如：test/123
     * @param savePath 保存文件到本地的路径，例如：D:/test
     * @return 成功返回true，否则返回false
     */
    public boolean downloadFiles(String ftpPath, String savePath) {
        // 登录
        FTPClient ftpClient = login();
        boolean flag = false;
        if (ftpClient != null) {
            try {
                String path = changeEncoding(BASE_PATH + ftpPath, ftpClient);
                // 判断是否存在该目录
                if (!ftpClient.changeWorkingDirectory(path)) {
                    LOGGER.error(BASE_PATH + ftpPath + "该目录不存在");
                    return flag;
                }
                ftpClient.enterLocalPassiveMode();  // 设置被动模式，开通一个端口来传输数据
                String[] fs = ftpClient.listNames();
                // 判断该目录下是否有文件
                if (fs == null || fs.length == 0) {
                    LOGGER.error(BASE_PATH + ftpPath + "该目录下没有文件");
                    return flag;
                }
                for (String ff : fs) {
                    String ftpName = new String(ff.getBytes(serverCharset), localCharset);
                    File file = new File(savePath + '/' + ftpName);
                    try (OutputStream os = new FileOutputStream(file)) {
                        ftpClient.retrieveFile(ff, os);
                    } catch (Exception e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                }
                flag = true;
            } catch (IOException e) {
                LOGGER.error("下载文件失败", e);
            } finally {
                closeConnect(ftpClient);
            }
        }
        return flag;
    }

    /**
     * 获取该目录下所有文件,以字节数组返回
     *
     * @param ftpPath FTP服务器上文件所在相对路径，例如：test/123
     * @return Map<String, Object> 其中key为文件名，value为字节数组对象
     */
    public Map<String, byte[]> getFileBytes(String ftpPath) {
        // 登录
        FTPClient ftpClient = login();
        Map<String, byte[]> map = new HashMap<>();
        if (ftpClient != null) {
            try {
                String path = changeEncoding(BASE_PATH + ftpPath, ftpClient);
                // 判断是否存在该目录
                if (!ftpClient.changeWorkingDirectory(path)) {
                    LOGGER.error(BASE_PATH + ftpPath + "该目录不存在");
                    return map;
                }
                ftpClient.enterLocalPassiveMode();  // 设置被动模式，开通一个端口来传输数据
                String[] fs = ftpClient.listNames();
                // 判断该目录下是否有文件
                if (fs == null || fs.length == 0) {
                    LOGGER.error(BASE_PATH + ftpPath + "该目录下没有文件");
                    return map;
                }
                for (String ff : fs) {
                    try (InputStream is = ftpClient.retrieveFileStream(ff)) {
                        String ftpName = new String(ff.getBytes(serverCharset), localCharset);
                        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                        byte[] buffer = new byte[BUFFER_SIZE];
                        int readLength = 0;
                        while ((readLength = is.read(buffer, 0, BUFFER_SIZE)) > 0) {
                            byteStream.write(buffer, 0, readLength);
                        }
                        map.put(ftpName, byteStream.toByteArray());
                        ftpClient.completePendingCommand(); // 处理多个文件
                    } catch (Exception e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                }
            } catch (IOException e) {
                LOGGER.error("获取文件失败", e);
            } finally {
                closeConnect(ftpClient);
            }
        }
        return map;
    }

    /**
     * 根据名称获取文件，以字节数组返回
     *
     * @param ftpPath  FTP服务器文件相对路径，例如：test/123
     * @param fileName 文件名，例如：test.xls
     * @return byte[] 字节数组对象
     */
    public byte[] getFileBytesByName(String ftpPath, String fileName) {
        // 登录
        FTPClient ftpClient = login();
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        if (ftpClient != null) {
            try {
                String path = changeEncoding(BASE_PATH + ftpPath, ftpClient);
                // 判断是否存在该目录
                if (!ftpClient.changeWorkingDirectory(path)) {
                    LOGGER.error(BASE_PATH + ftpPath + "该目录不存在");
                    return byteStream.toByteArray();
                }
                ftpClient.enterLocalPassiveMode();  // 设置被动模式，开通一个端口来传输数据
                String[] fs = ftpClient.listNames();
                // 判断该目录下是否有文件
                if (fs == null || fs.length == 0) {
                    LOGGER.error(BASE_PATH + ftpPath + "该目录下没有文件");
                    return byteStream.toByteArray();
                }
                for (String ff : fs) {
                    String ftpName = new String(ff.getBytes(serverCharset), localCharset);
                    if (ftpName.equals(fileName)) {
                        try (InputStream is = ftpClient.retrieveFileStream(ff);) {
                            byte[] buffer = new byte[BUFFER_SIZE];
                            int len = -1;
                            while ((len = is.read(buffer, 0, BUFFER_SIZE)) != -1) {
                                byteStream.write(buffer, 0, len);
                            }
                        } catch (Exception e) {
                            LOGGER.error(e.getMessage(), e);
                        }
                        break;
                    }
                }
            } catch (IOException e) {
                LOGGER.error("获取文件失败", e);
            } finally {
                closeConnect(ftpClient);
            }
        }
        return byteStream.toByteArray();
    }

    /**
     * 获取该目录下所有文件,以输入流返回
     *
     * @param ftpPath FTP服务器上文件相对路径，例如：test/123
     * @return Map<String, InputStream>                                                               ,                                                                                                                               InputStream> 其中key为文件名，value为输入流对象
     */
    public Map<String, InputStream> getFileInputStream(String ftpPath) {
        // 登录
        FTPClient ftpClient = login();
        Map<String, InputStream> map = new HashMap<>();
        if (ftpClient != null) {
            try {
                String path = changeEncoding(BASE_PATH + ftpPath, ftpClient);
                // 判断是否存在该目录
                if (!ftpClient.changeWorkingDirectory(path)) {
                    LOGGER.error(BASE_PATH + ftpPath + "该目录不存在");
                    return map;
                }
                ftpClient.enterLocalPassiveMode();  // 设置被动模式，开通一个端口来传输数据
                String[] fs = ftpClient.listNames();
                // 判断该目录下是否有文件
                if (fs == null || fs.length == 0) {
                    LOGGER.error(BASE_PATH + ftpPath + "该目录下没有文件");
                    return map;
                }
                for (String ff : fs) {
                    String ftpName = new String(ff.getBytes(serverCharset), localCharset);
                    InputStream is = ftpClient.retrieveFileStream(ff);
                    map.put(ftpName, is);
                    ftpClient.completePendingCommand(); // 处理多个文件
                }
            } catch (IOException e) {
                LOGGER.error("获取文件失败", e);
            } finally {
                closeConnect(ftpClient);
            }
        }
        return map;
    }

    /**
     * 根据名称获取文件，以输入流返回
     *
     * @param ftpPath  FTP服务器上文件相对路径，例如：test/123
     * @param fileName 文件名，例如：test.txt
     * @return InputStream 输入流对象
     */
    public static InputStream getInputStreamByName(String ftpPath, String fileName) {
        // 登录
        FTPClient ftpClient = login();
        InputStream input = null;
        if (ftpClient != null) {
            try {
                String path = changeEncoding(BASE_PATH + ftpPath, ftpClient);
                // 判断是否存在该目录
                if (!ftpClient.changeWorkingDirectory(path)) {
                    LOGGER.error(BASE_PATH + ftpPath + "该目录不存在");
                    return input;
                }
                ftpClient.enterLocalPassiveMode();  // 设置被动模式，开通一个端口来传输数据
                String[] fs = ftpClient.listNames();
                // 判断该目录下是否有文件
                if (fs == null || fs.length == 0) {
                    LOGGER.error(BASE_PATH + ftpPath + "该目录下没有文件");
                    return input;
                }
                for (String ff : fs) {
                    String ftpName = new String(ff.getBytes(serverCharset), localCharset);
                    if (ftpName.equals(fileName)) {
                        input = ftpClient.retrieveFileStream(ff);
                        break;
                    }
                }
            } catch (IOException e) {
                LOGGER.error("获取文件失败", e);
            } finally {
                closeConnect(ftpClient);
            }
        }
        return input;
    }

    /**
     * 删除指定文件
     *
     * @param filePath 文件相对路径，例如：test/123/test.txt
     * @return 成功返回true，否则返回false
     */
    public boolean deleteFile(String filePath) {
        // 登录
        FTPClient ftpClient = login();
        boolean flag = false;
        if (ftpClient != null) {
            try {
                String path = changeEncoding(BASE_PATH + filePath, ftpClient);
                flag = ftpClient.deleteFile(path);
            } catch (IOException e) {
                LOGGER.error("删除文件失败", e);
            } finally {
                closeConnect(ftpClient);
            }
        }
        return flag;
    }

    /**
     * 删除目录下所有文件
     *
     * @param dirPath 文件相对路径，例如：test/123
     * @return 成功返回true，否则返回false
     */
    public boolean deleteFiles(String dirPath) {
        // 登录
        FTPClient ftpClient = login();
        boolean flag = false;
        if (ftpClient != null) {
            try {
                ftpClient.enterLocalPassiveMode();  // 设置被动模式，开通一个端口来传输数据
                String path = changeEncoding(BASE_PATH + dirPath, ftpClient);
                String[] fs = ftpClient.listNames(path);
                // 判断该目录下是否有文件
                if (fs == null || fs.length == 0) {
                    LOGGER.error(BASE_PATH + dirPath + "该目录下没有文件");
                    return flag;
                }
                for (String ftpFile : fs) {
                    ftpClient.deleteFile(ftpFile);
                }
                flag = true;
            } catch (IOException e) {
                LOGGER.error("删除文件失败", e);
            } finally {
                closeConnect(ftpClient);
            }
        }
        return flag;
    }

    /**
     * 连接FTP服务器
     */
    private static FTPClient login() {
        //String address=ftpProperties.getProperty("ftp.server");
        //int port=Integer.valueOf(ftpProperties.getProperty("ftp.port"));
        //String username=ftpProperties.getProperty("ftp.loginName");
        //String password=ftpProperties.getProperty("ftp.loginPassword");
        //String address = "118.25.100.74";
        String address = "120.92.138.210";
        int port = 21;
        String username = "ftpuser";
        //String username = "ftp";
        String password = "ftpuser12345";
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(address, port);
            ftpClient.login(username, password);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            //2.下载文件报Host attempting data connection ip address is not same as server
            //
            //这个错误只有当我获取文件的地址为内网ip才会出现这种情况，需要修改一个设置。setRemoteVerificationEnabled
            //
            //服务器会获取自身Ip地址和提交的host进行匹配，当不一致时报出以上异常。
            ftpClient.setRemoteVerificationEnabled(false);

            //进入被动模式
            //ftpClient.enterLocalPassiveMode();


            //ftpClient.enterRemoteActiveMode();


            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                closeConnect(ftpClient);
                LOGGER.error("FTP服务器连接失败");
            }
        } catch (Exception e) {
            LOGGER.error("FTP登录失败", e);
        }
        return ftpClient;
    }

    /**
     * 关闭FTP连接
     */
    private static void closeConnect(FTPClient ftpClient) {
        if (ftpClient != null && ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                LOGGER.error("关闭FTP连接失败", e);
            }
        }
    }

    /**
     * FTP服务器路径编码转换
     *
     * @param ftpPath FTP服务器路径
     * @return String
     */
    private static String changeEncoding(String ftpPath, FTPClient ftpClient) {
        String directory = null;
        try {
            if (FTPReply.isPositiveCompletion(ftpClient.sendCommand(OPTS_UTF8, "ON"))) {
                localCharset = CHARSET_UTF8;
            }
            directory = new String(ftpPath.getBytes(localCharset), serverCharset);
        } catch (Exception e) {
            LOGGER.error("路径编码转换失败", e);
        }
        return directory;
    }

    /**
     * 本地文件上传到FTP服务器
     *
     * @param ftpPath  FTP服务器文件相对路径，例如：test/123
     * @param content  文件内容
     * @param fileName 上传到FTP服务的文件名，例如：666.txt
     * @return boolean 成功返回true，否则返回false
     */
    public static boolean createFile(String ftpPath, String fileName, String content, FTPClient ftpClient) {
        boolean flag = false;
        if (ftpClient != null) {
            InputStream fis = null;
            try {
                ftpClient.setBufferSize(BUFFER_SIZE);
                // 设置编码：开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）
                if (FTPReply.isPositiveCompletion(ftpClient.sendCommand(OPTS_UTF8, "ON"))) {
                    localCharset = CHARSET_UTF8;
                }
                fis = new ByteArrayInputStream(content.getBytes(localCharset));
                ftpClient.setControlEncoding(localCharset);
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                String path = changeEncoding(BASE_PATH + ftpPath, ftpClient);
                // 目录不存在，则递归创建
                if (!ftpClient.changeWorkingDirectory(path)) {
                    createDirectorys(path, ftpClient);
                }
                // 设置被动模式，开通一个端口来传输数据
                ftpClient.enterLocalPassiveMode();
                // 上传文件
                flag = ftpClient.storeFile(new String(fileName.getBytes(localCharset), serverCharset), fis);
            } catch (Exception e) {
                LOGGER.error("本地文件上传FTP失败", e);
            } finally {
                IOUtils.closeQuietly(fis);
                closeConnect(ftpClient);
            }
        }
        return flag;
    }

    /**
     * 本地上传文件到FTP服务器
     *
     * @param filePath 远程文件路径FTP
     */
    public static boolean appendFile(String filePath, String fileName, String content, FTPClient ftpClient) {
        boolean flag = false;
        if (ftpClient != null) {
            InputStream fis = null;
            try {
                ftpClient.setBufferSize(BUFFER_SIZE);
                // 设置编码：开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）
                if (FTPReply.isPositiveCompletion(ftpClient.sendCommand(OPTS_UTF8, "ON"))) {
                    localCharset = CHARSET_UTF8;
                }
                fis = new ByteArrayInputStream(("\n" + content).getBytes(localCharset));
                ftpClient.setControlEncoding(localCharset);
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                String path = changeEncoding(BASE_PATH + filePath, ftpClient);
                ftpClient.changeWorkingDirectory(path);
                // 设置被动模式，开通一个端口来传输数据
                ftpClient.enterLocalPassiveMode();
                // 追加文件
                FTPFile[] ftpFiles = ftpClient.listFiles();
                for (FTPFile file : ftpFiles) {
                    String ftpName = file.getName();
                    if (ftpName.equals(fileName)) {
                        ftpClient.setRestartOffset(file.getSize());
                        ftpName = new String(file.getName().getBytes(localCharset), serverCharset);
                        flag = ftpClient.appendFile(ftpName, fis);
                    }
                }
            } catch (Exception e) {
                LOGGER.error("文件追加失败", e);
            } finally {
                IOUtils.closeQuietly(fis);
                closeConnect(ftpClient);
            }
        }
        return flag;
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath 远程文件路径FTP
     * @throws IOException
     */
    public static Boolean isFileExist(String filePath, String fileName, FTPClient ftpClient) {
        if (ftpClient != null) {
            try {
                String path = changeEncoding(BASE_PATH + filePath, ftpClient);
                // 判断是否存在该目录
                if (!ftpClient.changeWorkingDirectory(path)) {
                    LOGGER.error(BASE_PATH + filePath + "该目录不存在");
                    return false;
                }
                ftpClient.enterLocalPassiveMode();  // 设置被动模式，开通一个端口来传输数据
                String[] fs = ftpClient.listNames();
                // 判断该目录下是否有文件
                if (fs == null || fs.length == 0) {
                    LOGGER.error(BASE_PATH + filePath + "该目录下没有文件");
                    return false;
                }
                for (String ff : fs) {
                    String ftpName = new String(ff.getBytes(serverCharset), localCharset);
                    if (ftpName.equals(fileName)) {
                        return true;
                    }
                }
            } catch (IOException e) {
                LOGGER.error("获取文件失败", e);
            }
        }
        return false;
    }

    /**
     * 创建 日志
     *
     * @param filePath
     * @param fileName
     * @param content
     */
    public static void createLogFile(String filePath, String fileName, String content) {
        FTPClient ftpClient = login();
        if (isFileExist(filePath, fileName, ftpClient)) {
            appendFile(filePath, fileName, content, ftpClient);
        } else {
            createFile(filePath, fileName, content, ftpClient);
        }
    }

    /**
     * 去服务器的FTP路径下上读取文件
     *
     * @param ftpPath
     * @param fileName
     * @return
     */
    public static String readFile(String ftpPath, String fileName) throws Exception {
        StringBuffer result = new StringBuffer();
        InputStream in = getInputStreamByName(ftpPath, fileName);
        if (in != null) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, localCharset));
            String str = null;
            try {
                while ((str = br.readLine()) != null) {
                    result.append(StringUtils.isNotBlank(result.toString()) ? System.lineSeparator() + str : str);
                }
            } catch (IOException e) {
                LOGGER.error("文件读取错误。");
                e.printStackTrace();
                return "文件读取失败，请联系管理员.";
            }
        } else {
            LOGGER.error("in为空，不能读取。");
            return "文件读取失败，请联系管理员.";
        }
        return result.toString();
    }

    /**
     * * 在服务器上递归创建目录
     * *
     * * @param dirPath 上传目录路径
     * * @return
     */
    private static void createDirectorys(String dirPath, FTPClient ftpClient) {
        try {
            if (!dirPath.endsWith("/")) {
                dirPath += "/";
            }
            String directory = dirPath.substring(0, dirPath.lastIndexOf("/") + 1);
            ftpClient.makeDirectory("/");
            int start = 0;
            int end = 0;
            if (directory.startsWith("/")) {
                start = 1;
            } else {
                start = 0;
            }
            end = directory.indexOf("/", start);
            while (true) {
                String subDirectory = new String(dirPath.substring(start, end));
                if (!ftpClient.changeWorkingDirectory(subDirectory)) {
                    if (ftpClient.makeDirectory(subDirectory)) {
                        ftpClient.changeWorkingDirectory(subDirectory);
                    } else {
                        LOGGER.info("创建目录失败");
                        return;
                    }
                }
                start = end + 1;
                end = directory.indexOf("/", start);
                //检查所有目录是否创建完毕
                if (end <= start) {
                    break;
                }
            }
        } catch (
                Exception e) {
            LOGGER.error("上传目录创建失败", e);
        }
    }

    public static void main(String[] args) throws Exception {

        FTPClient login = login();
        String root = login.printWorkingDirectory();
        System.out.println(root);
        System.out.println(login.stat());
        System.out.println(login.getStatus());

        FTPFile[] ftpFiles = login.listFiles();
        //FTPFile[] ftpFiles = login.listFiles(BASE_PATH);
        for (FTPFile ftpFile : ftpFiles) {
            //System.out.println(ftpFile.);
            System.out.println(ftpFile);
            System.out.println(ftpFile.getName());
            System.out.println(ftpFile.getSize());
            System.out.println(ftpFile.getSize());
        }

        //String path = ftpProperties.getProperty("ftp.path");

        appendFile("test/2019/03-01", "test.log", "中文字符串", login);
//        createFile("log/2019/03-01","测试文件.txt","文件内容",login());

//        System.out.println(isFileExist("/log/2019/03-01","test.log",login()));
        String fileContent = readFile("log/2019/03-01", "test.log");
        System.out.println(fileContent);
    }
}
 

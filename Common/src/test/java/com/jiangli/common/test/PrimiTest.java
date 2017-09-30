package com.jiangli.common.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.security.CodeSource;
import java.util.*;

/**
 * @author Jiangli
 * @date 2017/7/13 13:38
 */
public class PrimiTest {
        private long startTs;

    /**
     * Map with primitive type name as key and corresponding primitive type as
     * value, for example: "int" -> "int.class".
     */
    private static final Map<String, Class<?>> primitiveTypeNameMap = new HashMap<String, Class<?>>(16);
    /**
     * Map with primitive wrapper type as key and corresponding primitive type
     * as value, for example: Integer.class -> int.class.
     */
    private static final Map<Class<?>, Class<?>> primitiveWrapperTypeMap = new HashMap<Class<?>, Class<?>>(8);

    static {
        primitiveWrapperTypeMap.put(Boolean.class, boolean.class);
        primitiveWrapperTypeMap.put(Byte.class, byte.class);
        primitiveWrapperTypeMap.put(Character.class, char.class);
        primitiveWrapperTypeMap.put(Double.class, double.class);
        primitiveWrapperTypeMap.put(Float.class, float.class);
        primitiveWrapperTypeMap.put(Integer.class, int.class);
        primitiveWrapperTypeMap.put(Long.class, long.class);
        primitiveWrapperTypeMap.put(Short.class, short.class);

        Set<Class<?>> primitiveTypeNames = new HashSet<Class<?>>(16);
        primitiveTypeNames.addAll(primitiveWrapperTypeMap.values());
        primitiveTypeNames.addAll(Arrays
                .asList(new Class<?>[]{boolean[].class, byte[].class, char[].class, double[].class,
                        float[].class, int[].class, long[].class, short[].class}));
        for (Iterator<Class<?>> it = primitiveTypeNames.iterator(); it.hasNext(); ) {
            Class<?> primitiveClass = (Class<?>) it.next();
            primitiveTypeNameMap.put(primitiveClass.getName(), primitiveClass);
        }
    }

        @Before
        public void before() {
            startTs = System.currentTimeMillis();
            System.out.println("----------before-----------");
            System.out.println();
        }

        @After
        public void after() {
            long cost = System.currentTimeMillis() - startTs;
            System.out.println("----------after-----------:cost:"+cost+" ms");
            System.out.println();
        }

    private static final int RND_PORT_START = 30000;
    private static final int RND_PORT_RANGE = 10000;

    public static int getRandomPort() {
        return RND_PORT_START + new Random().nextInt(RND_PORT_RANGE);
    }

    public static int getAvailablePort() {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket();
            ss.bind(null);
            return ss.getLocalPort();
        } catch (IOException e) {
            return getRandomPort();
        } finally {
            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static int getAvailablePort(int port) {
        if (port <= 0) {
            return getAvailablePort();
        }
        for (int i = port; i < 65535; i++) {
            ServerSocket ss = null;
            try {
                ss = new ServerSocket(i);
                return i;
            } catch (IOException e) {
                // continue
            } finally {
                if (ss != null) {
                    try {
                        ss.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
        return port;
    }

        @Test
        public void test_ads() {
//
            System.out.println(primitiveWrapperTypeMap);
            System.out.println(primitiveTypeNameMap);

            Socket socket = new Socket();
            System.out.println(socket.getLocalAddress().getHostAddress());
            SocketAddress addr = new InetSocketAddress("www.baidu.com", 80);
            try {
                socket.connect(addr, 1000);
               String  host = socket.getLocalAddress().getHostAddress();
               System.out.println(host);
               System.out.println(socket.getLocalAddress());
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println(getAvailablePort(20882));
            System.out.println(getVersion(PrimiTest.class,"1.0.0"));
        }


    public static String getVersion(Class<?> cls, String defaultVersion) {
        try {
//            Manifest-Version: 1.0
//            Archiver-Version: Plexus Archiver
//            Created-By: Apache Maven
//            Built-By: DELL-13
//            Build-Jdk: 1.8.0
//            Specification-Title: dubbo
//            Specification-Version: 2.5.5
//            Specification-Vendor: Alibaba
//            Implementation-Title: dubbo
//            Implementation-Version: 2.5.5
//            Implementation-Vendor-Id: com.alibaba
//            Implementation-Vendor: Alibaba
            // 首先查找MANIFEST.MF规范中的版本号
            String version = cls.getPackage().getImplementationVersion();
            if (version == null || version.length() == 0) {
                version = cls.getPackage().getSpecificationVersion();
            }
            if (version == null || version.length() == 0) {
                // 如果规范中没有版本号，基于jar包名获取版本号
                CodeSource codeSource = cls.getProtectionDomain().getCodeSource();
                if (codeSource == null) {
                } else {
                    String file = codeSource.getLocation().getFile();
                    if (file != null && file.length() > 0 && file.endsWith(".jar")) {
                        file = file.substring(0, file.length() - 4);
                        int i = file.lastIndexOf('/');
                        if (i >= 0) {
                            file = file.substring(i + 1);
                        }
                        i = file.indexOf("-");
                        if (i >= 0) {
                            file = file.substring(i + 1);
                        }
                        while (file.length() > 0 && !Character.isDigit(file.charAt(0))) {
                            i = file.indexOf("-");
                            if (i >= 0) {
                                file = file.substring(i + 1);
                            } else {
                                break;
                            }
                        }
                        version = file;
                    }
                }
            }
            // 返回版本号，如果为空返回缺省版本号
            return version == null || version.length() == 0 ? defaultVersion : version;
        } catch (Throwable e) { // 防御性容错
            // 忽略异常，返回缺省版本号
            return defaultVersion;
        }
    }
}

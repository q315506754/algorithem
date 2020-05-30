package com.jiangli.nio;

import com.jiangli.common.utils.PathUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class NIOOperDemo {
    public void readFileByIO(String fileName) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(fileName);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = fis.read(buffer)) != -1) {
                System.out.write(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static void print(Buffer allocate) {
        System.out.println("position:"+allocate.position());
        System.out.println("limit:"+allocate.limit());
        System.out.println("capacity:"+allocate.capacity());
    }


    public void readByNIO(String file) {
        //第一步 获取通道
        FileInputStream fis = null;
        FileChannel channel = null;
        try {
            fis = new FileInputStream(file);
            channel = fis.getChannel();
            //文件内容的大小
            int size = (int) channel.size();

            //第二步 指定缓冲区
            StringBuilder sb = new StringBuilder();
            ByteBuffer buffer = ByteBuffer.allocate(256);
            int countSize = 0;

            while (countSize < size) {
                System.out.println("----------loop----------------");
//            ByteBuffer buffer = ByteBuffer.allocate(2560);
                //第三步 将通道中的数据读取到缓冲区中
                channel.read(buffer);

                Buffer bf = buffer.flip();

                System.out.println("...bf...");
                print(bf);
                System.out.println("...bf...");
                System.out.println("...buffer...");
                print(buffer);
                System.out.println("...buffer...");
                System.out.println(bf==buffer);

                System.out.println("limt:" + bf.limit());
                countSize += bf.limit();
                System.out.println("countSize:" + countSize);
                System.out.println("size:" + size);
                byte[] bt = buffer.array();
                String strByBuffer = new String(bt, 0, bf.limit());
//            System.out.println(strByBuffer);
                sb.append(strByBuffer);

                buffer.clear();
            }

            System.out.println(sb.toString());
            buffer = null;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                channel.close();
                fis.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }

    }

    /**
     * 利用NIO将内容输出到文件中
     *
     * @param file
     */
    public void writeFileByNIO(String file) {
        FileOutputStream fos = null;
        FileChannel fc = null;
        ByteBuffer buffer = null;
        try {
            fos = new FileOutputStream(file);
            //第一步 获取一个通道
            fc = fos.getChannel();
            //buffer=ByteBuffer.allocate(1024);
            //第二步 定义缓冲区
            String s = "Hello World 2";
            ArrayList<String> list = new ArrayList<>(100);
            int n = 100;
            while (n-- > 0) {
                list.add(n + ":" + s);
            }

            s = list.stream().reduce((a, b) -> a + "\r\n" + b).get();
            buffer = ByteBuffer.wrap(s.getBytes());
            //将内容写到缓冲区
            fos.flush();
            fc.write(buffer);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                fc.close();
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        NIOOperDemo nood = new NIOOperDemo();
        String fileName = "c:\\a\\hello.txt";
        //String fileName = "d:\\hello.txt";
        PathUtil.ensureFile(fileName);
        //nood.readFileByIO(fileName);
        nood.writeFileByNIO(fileName);

        nood.readByNIO(fileName);
    }


}
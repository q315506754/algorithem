package com.jiangli.log.analyser.core;

import java.io.*;

/**
 * @author Jiangli
 * @date 2017/3/1 15:28
 */
public class ConsolePrintHandler implements Handler{


    @Override
    public void handle(File file) {
        try {
            FileInputStream fin = new FileInputStream(file);

            BufferedReader bf = new BufferedReader(new InputStreamReader(fin));
            String line;

            System.out.println("handle file."+file.getName());
            while ((line = bf.readLine()) != null) {
//                System.out.println(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

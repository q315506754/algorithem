package com.jiangli.log.analyser.core;

import com.jiangli.log.analyser.feature.ExceptionFeature;
import com.jiangli.log.analyser.feature.Feature;

import java.io.*;
import java.util.*;

/**
 * @author Jiangli
 * @date 2017/3/1 15:28
 */
public class ExceptionOutputHandler implements Handler{
    private static final String SPLIT_ENTER = "\r\n";
    private Feature feature = new ExceptionFeature();
    private String outputName;
    private Map<String, CollectionObj> firstLineExcepMap = new TreeMap<>();

    public ExceptionOutputHandler(String outputName) {
        this.outputName = outputName;
    }

    class CollectionObj implements Comparable{
        int count =1;
        String exceptionStr ;
        String firstLine ;

        @Override
        public int compareTo(Object o) {
            if (!(o instanceof CollectionObj)) {
                return 1;
            }

            CollectionObj other = (CollectionObj) o;
            return this.count > other.count? -1:1;
        }
    }

    @Override
    public void handle(File file) {
        try {
            FileInputStream fin = new FileInputStream(file);

            BufferedReader bf = new BufferedReader(new InputStreamReader(fin));
            String line;

            System.out.println("handling file."+file.getName());

           StringBuilder exceptionSb = null;
            boolean featureStarted = false;
            String firstLine = null;
            while ((line = bf.readLine()) != null) {
                if (!featureStarted && feature.featureStarts(line)) {
                    exceptionSb = new StringBuilder();
                    featureStarted = true;
                    firstLine = line;
                }
                else if (featureStarted && feature.featureEnds(line)) {
                    featureStarted = false;
                    exceptionSb.append(line);
                    exceptionSb.append(SPLIT_ENTER);
                    String exceptionStr = exceptionSb.toString();
//                    System.out.println(exceptionStr);

                    CollectionObj colObj = firstLineExcepMap.get(firstLine);
                    if (colObj == null) {
                        CollectionObj obj = new CollectionObj();
                        obj.exceptionStr = exceptionStr;
                        obj.firstLine = firstLine;
                        firstLineExcepMap.put(firstLine,obj);
                    } else {
                        colObj.count++;
                    }
                }

                if (featureStarted) {
                    exceptionSb.append(line);
                    exceptionSb.append(SPLIT_ENTER);
                }

            }

            fin.close();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    public void outputResults() {
        try {
            //write
            FileOutputStream fos = new FileOutputStream(outputName);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

            List<CollectionObj> tList = new ArrayList<>(firstLineExcepMap.size());
            tList.addAll(firstLineExcepMap.values());
            Collections.sort(tList);

            for (CollectionObj collectionObj : tList) {
                    String fLine = collectionObj.firstLine;
                    CollectionObj obj = firstLineExcepMap.get(fLine);
                    bw.write("总计出现次数:"+obj.count+ SPLIT_ENTER);
                    bw.write(obj.exceptionStr);
                    bw.write("------------------------------------------------------------------------------------");
                    bw.write(SPLIT_ENTER);
                    bw.write(SPLIT_ENTER);
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

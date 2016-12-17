package com.jiangli.crawler.webmagic.six;

import com.jiangli.common.utils.DateUtil;
import com.jiangli.common.utils.PathUtil;
import com.jiangli.crawler.webmagic.six.mapper.TableMapper;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Jiangli on 2016/12/15.
 */
public class Conf {
//    public final static String startTableUrl = "http://www.sis001.com/forum/forumdisplay.php?fid=58&filter=type&typeid=1255&page=1";
    //all youma asia
//    public final static String startTableUrl = "http://www.sis001.com/forum/forum-58-1.html";

    //all wuma asia
    public final static String startTableUrl = "http://www.sis001.com/forum/forum-25-1.html";


    public final static String startDetailUrl = "http://www.sis001.com/forum/viewthread.php?tid=9665239&extra=page%3D4%26amp%3Bfilter%3Dtype%26amp%3Btypeid%3D1255";

    public final static String listRegex = "forum/forum";
    public final static String detailRegex = "forum/viewthread.php";

    public final static String createTimeShouldGreaterThanThis = "2016-01-01";
    public  static Long createTimeGTE ;

    public final static String task_directory = "E:\\Downloads\\task";
    public final static String file_keys = "keys.txt";
    public final static String file_keys_exclude = "keys_exclude.txt";

    public final static Set<String> keyList = new HashSet<>();
    public final static Set<String> keyExcludeList = new HashSet<>();

    public  static TableMapper mapper ;

    static {
        try {
            createTimeGTE = DateUtil.getDate_yyyy_MM_dd(createTimeShouldGreaterThanThis).getTime();


            readToSetByLine(file_keys, keyList);
            System.out.println("keys:"+keyList);
            readToSetByLine(file_keys_exclude, keyExcludeList);
            System.out.println("key exclude:"+keyExcludeList);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void readToSetByLine(String txt, Set<String> set) throws IOException {
        String keyFilePath = PathUtil.buildPath(task_directory, txt);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(keyFilePath)));
        String line;
        while ((line = br.readLine()) != null) {
            if (!StringUtils.isEmpty(line)) {
                set.add(line.trim());
            }
        }
        br.close();
    }


    public static void main(String[] args) {

    }
}

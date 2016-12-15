package com.jiangli.crawler.webmagic.six;

/**
 * Created by Jiangli on 2016/12/15.
 */
public class Conf {
    public final static String startTableUrl = "http://www.sis001.com/forum/forumdisplay.php?fid=58&filter=type&typeid=1255&page=4";
    public final static String startDetailUrl = "http://www.sis001.com/forum/viewthread.php?tid=9665239&extra=page%3D4%26amp%3Bfilter%3Dtype%26amp%3Btypeid%3D1255";

    public final static String listRegex = "forum/forumdisplay.php";
    public final static String detailRegex = "forum/viewthread.php";

    public final static String task_directory = "E:\\Downloads\\task";
    public final static String file_keys = "keys.txt";
}

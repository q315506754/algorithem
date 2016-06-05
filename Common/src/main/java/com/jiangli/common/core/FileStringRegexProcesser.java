package com.jiangli.common.core;

/**
 * Created by Jiangli on 2016/6/5.
 */
public class FileStringRegexProcesser  implements FileStringProcesser{
    private String regex;
    private String value;

    public FileStringRegexProcesser(String regex, String value) {
        this.regex = regex;
        this.value = value;
    }

    @Override
    public String process(String line) {
        return line.replaceAll(regex,value);
    }
}
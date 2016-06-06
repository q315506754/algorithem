package com.jiangli.common.core;

/**
 * Created by Jiangli on 2016/6/5.
 */
public class FileStringRegexDynamicProcesser extends FileStringRegexProcesser{

    public FileStringRegexDynamicProcesser(String regex, String value) {
        super(regex, value);
    }
    private String phValue = "";//placeholder value

    public void setPhValue(String phValue) {
        this.phValue = phValue;
    }

    @Override
    public String process(String line) {
        String realValue = value.replaceAll("<value>",this.phValue);
        return line.replaceAll(regex,realValue) ;
    }
}
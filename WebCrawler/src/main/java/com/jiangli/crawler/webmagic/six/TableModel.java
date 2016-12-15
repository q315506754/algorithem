package com.jiangli.crawler.webmagic.six;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by Jiangli on 2016/12/15.
 */
public class TableModel {
    private String type;
    private String title;
    private String url;
    private String createTime;
    private String lastEndorseTime;
    private String size;
    private String mediaType;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastEndorseTime() {
        return lastEndorseTime;
    }

    public void setLastEndorseTime(String lastEndorseTime) {
        this.lastEndorseTime = lastEndorseTime;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    @Override
    public String toString() {
        return  JSONObject.toJSONString(this);
    }
}

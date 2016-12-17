package com.jiangli.crawler.webmagic.six.model;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by Jiangli on 2016/12/15.
 */
public class TableModel {
    private String type;
    private String matchKey;
    private String id;
    private String title;
    private String url;
    private String createTime;
    private String lastEndorseTime;
    private String size;
    private String mediaType;
    private Integer marked;

    public Integer getMarked() {
        return marked;
    }

    public void setMarked(Integer marked) {
        this.marked = marked;
    }

    public String getMatchKey() {
        return matchKey;
    }

    public void setMatchKey(String matchKey) {
        this.matchKey = matchKey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

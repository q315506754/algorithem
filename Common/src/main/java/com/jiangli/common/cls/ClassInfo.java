package com.jiangli.common.cls;

/**
 * @author Jiangli
 * @date 2017/7/5 8:54
 */
public class ClassInfo {
    private String basePath;
    private String relativePath;
    private String clsName;
    private Class cls;

    @Override
    public String toString() {
        return "ClassInfo{" +
                "basePath='" + basePath + '\'' +
                ", relativePath='" + relativePath + '\'' +
                ", clsName='" + clsName + '\'' +
                ", cls=" + cls +
                '}';
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getClsName() {
        return clsName;
    }

    public void setClsName(String clsName) {
        this.clsName = clsName;
    }

    public Class getCls() {
        return cls;
    }

    public void setCls(Class cls) {
        this.cls = cls;
    }
}

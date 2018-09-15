package com.jiangli.netty.pojo;

/**
 * @author Jiangli
 * @date 2018/9/13 16:40
 */
public class POJO {
    private int version;
    private String name;
    private String cmd;

    public int getVersion() {
        return version;
    }

    public POJO setVersion(int version) {
        this.version = version;
        return this;
    }

    public String getName() {
        return name;
    }

    public POJO setName(String name) {
        this.name = name;
        return this;
    }

    public String getCmd() {
        return cmd;
    }

    public POJO setCmd(String cmd) {
        this.cmd = cmd;
        return this;
    }

    @Override
    public String toString() {
        return "POJO{" +
                "version=" + version +
                ", name='" + name + '\'' +
                ", cmd='" + cmd + '\'' +
                '}';
    }

    public int getLength() {
        return 4+ this.name.getBytes().length + this.cmd.getBytes().length;
    }

}

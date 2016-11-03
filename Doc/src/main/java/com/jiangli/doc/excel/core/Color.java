package com.jiangli.doc.excel.core;

import net.sf.json.JSONObject;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/1/15 0015 14:33
 */
public class Color {
    private short back = -1;
    private short fore = -1;
    private String rgb;

    public String getRgb() {
        return rgb;
    }

    public void setRgb(String rgb) {
        this.rgb = rgb;
    }

    public short getBack() {
        return back;
    }

    public void setBack(short back) {
        this.back = back;
    }

    public short getFore() {
        return fore;
    }

    public void setFore(short fore) {
        this.fore = fore;
    }

    @Override
    public String toString() {
        return JSONObject.fromObject(this).toString();
    }
}

package com.jiangli.common.utils;

import java.util.LinkedList;
import java.util.List;

public class LayerCheck {
    private int index;
    private String[] layer;
    private Handler handle;
    private List<String> errorBuilder = new LinkedList<String>();

    public LayerCheck(int index, Handler handle,
                      String... layer) {
        this.index = index;
        this.layer = layer;
        this.handle = handle;
    }

    public String buildLayerString() {
        String ret = "";
        for (int i = 0; i < layer.length; i++) {
            ret += layer[i];
            if (i < layer.length - 1) {
                ret += "——";
            }
        }
        return ret;
    }

    public void check() {
        if (this.handle.handle(this)) {
            System.out.println(index + ":" + buildLayerString() + " [√]");
        } else {
            System.out.println(index + ":" + buildLayerString() + " [×]");

            for (String s : errorBuilder) {
                System.out.println("      " + s);
            }
        }
    }

    public void error(String str) {
        errorBuilder.add(str);
    }
}
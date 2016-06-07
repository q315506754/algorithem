package com.jiangli.swing;

import javax.swing.*;

/**
 * Created by Jiangli on 2016/6/7.
 */
public interface BtnObjFActionListener<V> {
    void actionPerformed(AbstractButton com,V val,Object obj,String field);
}

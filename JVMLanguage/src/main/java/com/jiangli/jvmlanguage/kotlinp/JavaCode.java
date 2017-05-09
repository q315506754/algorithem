package com.jiangli.jvmlanguage.kotlinp;

import java.util.Collection;
import java.util.Iterator;

/**
 * press ctrl+alt+shift+K convert from java file to kotlin file
 */
public class JavaCode {
    public String toJSON(Collection<Integer> collection) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Iterator<Integer> iterator = collection.iterator();
        while (iterator.hasNext()) {
            Integer element = iterator.next();
            sb.append(element);
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
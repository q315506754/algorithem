package com.jiangli.common.utils;

import java.util.Collection;
import java.util.List;

/**
 * @author Jiangli
 * @date 2017/4/20 10:51
 */
public class CollectionUtil {
    public static <T> T get(List<T> list, int idx){
        if(isEmpty(list)){
            return null;
        }
        if (idx>=0 && idx<list.size()) {
            return list.get(idx);
        }
        return null;
    }

    public static int size(Collection collection){
       if(isEmpty(collection)){
            return 0;
       }
        return collection.size();
    }
    public static int sizeIter(Iterable iterable){
       if(iterable==null){
            return 0;
       }
       int count=0;
        for (Object o : iterable) {
            count++;
        }
        return count;
    }

    public static boolean isEmpty(Collection collection){
        return collection==null || collection.size()==0;
    }
}

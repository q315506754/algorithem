package com.jiangli.jdk.v1_8;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/14 0014 17:25
 */
public interface CommonInfv1_8 {
    String function(String param);

    default void print(String name) {
        System.out.println("this is default func implementation.."+name);
    }

}

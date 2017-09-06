package com.jiangli.commons;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;

/**
 * Created by Jiangli on 2017/9/6.
 */
public class ProfileCheckTest {
    public static void main(String[] args) {
        Context context = new ContextBase();
        Command command = new ProfileCheck();
        try {
            command.execute(context);
        } catch (Exception e) {
           e.printStackTrace();
        }

        Profile profile = (Profile) context.get(Profile.PROFILE_KEY);
        System.out.println(profile);
//        assertNotNull("Missing Profile", profile);
    }
}

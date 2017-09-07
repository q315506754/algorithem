package com.jiangli.commons.chain;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

/**
 * @author Jiangli
 * @date 2017/9/7 18:40
 */
public class CommandCheck implements Command {
    @Override
    public boolean execute(Context context) throws Exception {
        System.out.println("check:"+context);
        return false;
    }
}

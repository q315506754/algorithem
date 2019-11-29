package com.jiangli.bytecode.instrument;

import com.jiangli.bytecode.ByteDto;
import com.jiangli.bytecode.ByteDtoSub;

import java.lang.instrument.Instrumentation;

/**
 * @author Jiangli
 * @date 2019/11/28 17:39
 */
public class MyAgentMain {

    public static  ByteDtoSub BYTE_DTO_SUB = new ByteDtoSub();

    public static void agentmain(String args, Instrumentation inst) {
        try {
            System.out.println("agentmain.run44.." + args);

            inst.addTransformer(new MyTransformer(),true);

            System.out.println("agent before...");
            BYTE_DTO_SUB = new ByteDtoSub();
            //inst.retransformClasses(MyAgentMain.class);
            inst.retransformClasses(ByteDto.class);

            System.out.println("agent done...");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("agent failed...");
        }

    }

}

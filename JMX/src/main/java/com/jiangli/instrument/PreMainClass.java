package com.jiangli.instrument;

import com.jiangli.instrument.transformer.SrcCodeTransformer;

import java.lang.instrument.Instrumentation;

/**
 * @author Jiangli
 * @date 2019/1/23 14:59
 */
public class PreMainClass {
    public static void premain(String agentArgs, Instrumentation inst){
        System.out.println("premain:"+agentArgs);
        System.out.println("inst:"+inst);

        //inst.addTransformer(new InjarClassTransformer());
        inst.addTransformer(new SrcCodeTransformer());
    }
}

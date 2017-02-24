package com.jiangli.springboot.controller;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;

/**
 * @author Jiangli
 * @date 2017/2/24 14:38
 */
@Component
public class ShutDownListener implements ExitCodeGenerator {
    @Override
    public int getExitCode() {
        System.out.println("ShutDownListener.getExitCode");
        return -3;
    }
}

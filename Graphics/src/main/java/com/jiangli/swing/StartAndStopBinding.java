package com.jiangli.swing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jiangli on 2016/6/6.
 */
public class StartAndStopBinding {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private AbstractButton startCom;
    private AbstractButton stopCom;
    private boolean startedClicking;
    private BindingCallBack logic;

    public StartAndStopBinding(AbstractButton startCom, AbstractButton stopCom, BindingCallBack logic) {
        this.startCom = startCom;
        this.stopCom = stopCom;
        this.logic = logic;

        startBinding();
    }

    public void startBinding(){
        logger.debug("startBinding");

        this.startCom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread startedClickingThread = new Thread() {

                    @Override
                    public void run() {
                        startedClicking = true;
                        try {
                            while (startedClicking) {
                                logic.run();
                            }
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                };
                startedClickingThread.start();
            }
        });

        this.stopCom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (startedClicking) {
                    startedClicking = false;
                }else {
                }
            }
        });
    }

}

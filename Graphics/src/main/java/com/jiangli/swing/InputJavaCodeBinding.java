package com.jiangli.swing;

import com.jiangli.common.core.FileStringRegexDynamicProcesser;
import com.jiangli.common.utils.FileUtil;
import com.jiangli.common.utils.PathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.JTextComponent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

/**
 * Created by Jiangli on 2016/6/6.
 */
public class InputJavaCodeBinding {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private JTextComponent key;
    private FileStringRegexDynamicProcesser processer;
    private Class srcCodeCls;

    public InputJavaCodeBinding(Class srcCodeCls, FileStringRegexDynamicProcesser processer) {
        this.processer = processer;
        this.srcCodeCls = srcCodeCls;
    }

    public InputJavaCodeBinding(JTextComponent key, Class srcCodeCls, FileStringRegexDynamicProcesser processer) {
        this.key = key;
        this.srcCodeCls = srcCodeCls;
        this.processer = processer;
        this.key.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                String text = key.getText();
                processer.setPhValue(text);
                refresh();
            }
        });
    }

    public void refresh(){
        logger.debug("start refresh...");
        String src_java_code_path = PathUtil.getSRC_JAVA_Code_Path(srcCodeCls);
        File file = new File(src_java_code_path);
        File  processedSrc = FileUtil.processAndReplace(file, processer);
        logger.debug("refresh over...");
    }
}

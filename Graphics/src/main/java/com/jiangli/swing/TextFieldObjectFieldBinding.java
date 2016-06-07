package com.jiangli.swing;

import com.jiangli.common.utils.MethodUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.JTextComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/7 0007 10:04
 */
public class TextFieldObjectFieldBinding<T> {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private JTextComponent key;
    private Object obj;
    private String  field;
    private ObjStringFormatter<T>  objStringFormatter;
    protected KeyListener[] keyListeners;

    public TextFieldObjectFieldBinding(JTextComponent key, Object obj, String field, ObjStringFormatter<T> objStringFormatter,KeyListener... actionListeners) {
        this.key = key;
        this.obj = obj;
        this.field = field;
        this.objStringFormatter = objStringFormatter;
        this.keyListeners = actionListeners;

        reBind();
    }

    protected void setKeyListeners(KeyListener[] keyListeners) {
        this.keyListeners = keyListeners;
    }

    public void reBind() {
        try {
            Object o = MethodUtil.invokeGetter(obj, field);
            String t = this.objStringFormatter.parseIntoString((T) o);
            key.setText(t);
            logger.debug("init text value:"+t);
        } catch (Exception e) {
            e.printStackTrace();
        }

        KeyListener[] exsistKL = key.getKeyListeners();
        for (KeyListener keyListener : exsistKL) {
            key.removeKeyListener(keyListener);
        }

        key.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                String text = key.getText();
                T valueFromString = TextFieldObjectFieldBinding.this.objStringFormatter.getValueFromString(text);
                logger.debug("parse text value:"+valueFromString);
                try {
                     MethodUtil.invokeSetter(obj, field, valueFromString);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                logger.debug("invoking keyListeners....");
                if (keyListeners != null) {
                    for (KeyListener actionListener : keyListeners) {
                        logger.debug("keyReleased...."+actionListener);
                        actionListener.keyReleased(e);
                    }
                    logger.debug("invoked size...."+keyListeners.length);
                } else {
                    logger.debug("no keyListeners....");
                }
            }
        });
    }
}

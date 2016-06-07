package com.jiangli.swing;

import com.jiangli.common.core.ObjectToObjectFieldBinding;
import com.jiangli.common.utils.MethodUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.JTextComponent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/7 0007 10:04
 */
public class TextFObjectFRefershBinding<T> extends TextFieldObjectFieldBinding{
    protected ObjectToObjectFieldBinding refreshBinding;

    private TextFObjectFRefershBinding(JTextComponent key, Object obj, String field, ObjStringFormatter objStringFormatter, KeyListener... actionListeners) {
        super(key, obj, field, objStringFormatter, actionListeners);
    }

    public TextFObjectFRefershBinding(JTextComponent key, Object obj, String field, ObjStringFormatter objStringFormatter, ObjectToObjectFieldBinding refreshBinding, KeyListener... actionListeners) {
        this(key, obj, field, objStringFormatter);
        this.refreshBinding = refreshBinding;

        //init first
        logger.debug("first refresh");
        refreshBinding.refresh();

        int length = 1;

        if (actionListeners != null) {
            length = length + 1;
        }

        KeyListener[] listeners = new KeyListener[length];

        if (actionListeners != null) {
            for (int i = 0; i < actionListeners.length; i++) {
                listeners[i+1] = actionListeners[i];
            }
        }

        listeners[0] = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                refreshBinding.refresh();
            }
        };

        setKeyListeners(listeners);
    }


}

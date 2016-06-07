package com.jiangli.swing;

import com.jiangli.common.core.ValueDecorator;
import com.jiangli.common.utils.MethodUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;

/**
 * Created by Jiangli on 2016/6/7.
 */
public class AbstractButtonToObjFieldBinding<D> {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected AbstractButton button;
    protected Object objIns;
    protected String field;
    private ValueDecorator<AbstractButton,D> decorator;
    private BtnObjFActionListener<D>[] listeners;

    public AbstractButtonToObjFieldBinding(AbstractButton button, Object objIns, String field, ValueDecorator<AbstractButton, D> decorator, BtnObjFActionListener<D>... listeners) {
        this.button = button;
        this.objIns = objIns;
        this.field = field;
        this.decorator = decorator;
        this.listeners = listeners;

        bind();
    }

    public void bind() {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    logger.debug("actionPerformed..");
                    Class<?> aClass = objIns.getClass();
                    Method setter = MethodUtil.getSetter(aClass, field);
                    D decorate = decorator.decorate(button);
                    setter.invoke(objIns, decorate);

                    logger.debug("start invoke listeners..");
                    if (listeners!=null) {
                        for (BtnObjFActionListener listener : listeners) {
                            listener.actionPerformed(button,decorate,objIns,field);
                        }
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}

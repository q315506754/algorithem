package com.jiangli.swing;

import com.jiangli.common.core.ValueDecorator;
import com.jiangli.common.utils.MethodUtil;

import javax.swing.*;

/**
 * Created by Jiangli on 2016/6/7.
 */
public class SelectableButtonToObjFieldBinding extends AbstractButtonToObjFieldBinding<Boolean>{

    public SelectableButtonToObjFieldBinding(AbstractButton button, Object objIns, String field,BtnObjFActionListener<Boolean>... listeners) {
        super(button, objIns, field, new ValueDecorator<AbstractButton, Boolean>() {

            @Override
            public Boolean decorate(AbstractButton srcVal) {
                return srcVal.isSelected();
            }
        }, listeners);

        try {
            button.setSelected((Boolean) MethodUtil.invokeGetter(objIns,field));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

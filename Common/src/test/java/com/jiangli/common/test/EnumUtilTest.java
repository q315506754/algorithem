package com.jiangli.common.test;

import com.jiangli.common.model.Department;
import com.jiangli.common.utils.EnumUtil;
import org.junit.Test;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/20 0020 11:13
 */
public class EnumUtilTest {
    @Test
    public void func() {
        Department byOrdinal = EnumUtil.getSpecialByOrdinal(Department.class, 3);
//        Department.values();
        System.out.println(byOrdinal);
    }

}

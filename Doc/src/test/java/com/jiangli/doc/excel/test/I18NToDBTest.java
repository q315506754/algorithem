package com.jiangli.doc.excel.test;

import com.jiangli.common.utils.CommonUtil;
import com.jiangli.doc.excel.core.Excel;
import com.jiangli.doc.excel.impl.ExcelToModelParser97_2003;
import com.jiangli.doc.excel.inf.ExcelToModelParser;
import com.jiangli.doc.excel.test.us.I18NModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Jiangli
 * @date 2016/11/1 9:11
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:beans*"})
public class I18NToDBTest {
    @Autowired
    private DataSource dataSource;

    String excelFile = "C:\\Users\\DELL-13\\Desktop\\2016-10-27国际化.xls";

    @Test
    public void func() throws Exception {
        File file = new File(excelFile);
        Excel.ExcelVersion version = Excel.version(file);
        System.out.println(version);

        ExcelToModelParser<I18NModel> parser = new ExcelToModelParser97_2003("Sheet1", I18NModel.class);
        List<I18NModel> parse = parser.parse(file);
        System.out.println(parse);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource) ;
        List<Map<String ,Object>> list = jdbcTemplate.query("SELECT * FROM test.TBL_I18N_ITEMS_TREENITY ", new ColumnMapRowMapper()) ;
        System.out.println(list);

        List<I18NModel> toBeAdded = new LinkedList<>();
        for (I18NModel i18NModel : parse) {
            String i18nKey = i18NModel.getI18nKey();

            boolean exists = isExists(list, i18nKey);

            if (!exists) {
                toBeAdded.add(i18NModel);
            }
        }

        System.out.println(toBeAdded);
    }

    private boolean isExists(List<Map<String, Object>> list, String i18nKey) {
        boolean exists = false;
        for (Map<String, Object> map : list) {
            String mapVal = CommonUtil.nullToEmpty(map.get("I18N_KEY"));
            if (mapVal.equals(i18nKey)) {
                exists = true;
                break;
            }
        }
        return exists;
    }
}

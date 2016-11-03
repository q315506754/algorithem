package com.jiangli.doc.excel.test.us;

import com.jiangli.doc.excel.anno.FromExcel;
import com.jiangli.doc.excel.anno.ToSql;
import com.jiangli.doc.excel.enums.ExcelValType;
import com.jiangli.doc.excel.enums.SqlType;
import net.sf.json.JSONObject;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/1/15 0015 13:25
 */
public class I18NModel {
    @FromExcel(columnName = "ID", valType = ExcelValType.STRING)
    @ToSql(columnName = "orderid", valType = SqlType.VARCHAR)
    private String id;

    @FromExcel(columnName = "I18N_KEY", valType = ExcelValType.STRING)
    @ToSql(columnName = "cusName", valType = SqlType.VARCHAR)
    private String i18nKey;

    @FromExcel(columnName = "CHINESE(中文）", valType = ExcelValType.STRING)
    @ToSql(columnName = "liveName", valType = SqlType.VARCHAR)
    private String chinese;

    @FromExcel(columnName = "ENGLISH（英文）", valType = ExcelValType.STRING)
    @ToSql(columnName = "english", valType = SqlType.VARCHAR)
    private String entName;

    @FromExcel(columnName = "REMARK", valType = ExcelValType.STRING)
    @ToSql(columnName = "remark", valType = SqlType.VARCHAR)
    private String remark;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getI18nKey() {
        return i18nKey;
    }

    public void setI18nKey(String i18nKey) {
        this.i18nKey = i18nKey;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return JSONObject.fromObject(this).toString();
    }
}

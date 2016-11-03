package com.jiangli.doc.excel.inf;


import com.jiangli.doc.excel.enums.CMDType;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/1/15 0015 9:38
 */
public interface ExcelToDBParser extends ExcelParser {

    public void setCMDType(CMDType type);

    public void enableModifyDateMode(String checkinDateStart, String checkinDateEnd);

    public void enableRedOnlyMode();

    public void enableNotWriteMode();

    public String queryParseId();

}

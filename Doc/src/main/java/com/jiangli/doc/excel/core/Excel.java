/**
 *
 */
package com.jiangli.doc.excel.core;

import com.jiangli.doc.excel.Exception.UnkownExcelSuffixException;
import com.jiangli.doc.excel.impl.ExcelParser2007;
import com.jiangli.doc.excel.impl.ORMExcelParser2007;
import com.jiangli.doc.excel.impl.PropertyExcelParser2007;
import com.jiangli.doc.excel.inf.ExcelToDBParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.math.BigDecimal;

/**
 * @author JiangLi
 *
 *         CreateTime 2014-12-3 上午9:56:03
 */
public class Excel {
    private final static Logger logger = LoggerFactory.getLogger(Excel.class);

    public static ExcelVersion version(String fileName) throws UnkownExcelSuffixException {
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (suffix.equalsIgnoreCase("xls")) {
            return ExcelVersion.version97_2003;
        }
        if (suffix.equalsIgnoreCase("xlsx")) {
            return ExcelVersion.version2007_;
        }
        throw new UnkownExcelSuffixException(suffix);
    }

    public static ExcelVersion version(File file) throws UnkownExcelSuffixException {
        String fileName = file.getName();
        return version(fileName);
    }

    public static String forMatByteSize(int size) {
        BigDecimal setScale = new BigDecimal(size * 1.0 / 1024 / 1024).setScale(2, BigDecimal.ROUND_CEILING);
        return setScale.toString() + "M";
    }

    public static ExcelToDBParser getParser(ExcelVersion version) {
        if (version == ExcelVersion.version2007_) {
            return new ExcelParser2007();
        }
//        if (version == ExcelVersion.version97_2003) {
//            return new ExcelParser97_2003();
//        }
        return null;
    }

    public static PropertyExcelParser2007 getPropParser(ExcelVersion version) {
        if (version == ExcelVersion.version2007_) {
            return new PropertyExcelParser2007();
        }
//        if (version == ExcelVersion.version97_2003) {
//            return new ExcelParser97_2003();
//        }
        return null;
    }

    public static ExcelToDBParser getORMParser(ExcelVersion version) {
        if (version == ExcelVersion.version2007_) {
            return new ORMExcelParser2007();
        }
//        if (version == ExcelVersion.version97_2003) {
//            return new ExcelParser97_2003();
//        }
        return null;
    }

    public static enum ExcelVersion {
        version97_2003, version2007_
    }

}

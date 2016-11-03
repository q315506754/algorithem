package com.jiangli.doc.excel.impl;

import com.jiangli.doc.excel.Exception.ExcelParsingException;
import com.jiangli.doc.excel.core.OrderCmp;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.util.*;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/1/18 0018 14:03
 */
public class ORMExcelComparator extends ORMExcelParser2007 {

    @Override
    protected void infoNoSuchExcelMetaInfo(String errorMsg) {

    }

    @Override
    public JSONObject parse(File excelFile) throws ExcelParsingException {
        JSONObject ret = new JSONObject();
        JSONArray arr = new JSONArray();

        if (excelFile == null || !excelFile.exists()) {
            throw new ExcelParsingException("不能对空文件或不存在的文件解析");
        }

        Iterator<Row> rowIterator = getRowIterator(excelFile);
        logger.debug("lastRowNum:" + lastRowNum);

        //meta
        parseMetaInfo(OrderCmp.class);

        final Row head = rowIterator.next();
        parseHeadMap(head);

        int count = 1;
        List<String> duplicateOrderIds = new LinkedList<String>();
        List<String> orderIds = new LinkedList<String>();
        Map<String, OrderCmp> mapRs = new HashMap<String, OrderCmp>();
        while (rowIterator.hasNext()) {
            count++;

//            logger.debug("current row:" + count + " / " + lastRowNum + "-----------------------------------------------");

            try {
                final Row curRow = rowIterator.next();
                OrderCmp one = new OrderCmp();
                parseRowToObject(curRow, one);

//                logger.debug("one:" + one );
                final String orderId = one.getOrderId();
                orderIds.add(orderId);

                if (mapRs.get(orderId) != null) {
                    duplicateOrderIds.add(orderId);
                    continue;
                }

                mapRs.put(orderId, one);

            } catch (Exception e) {
                e.printStackTrace();

                JSONObject one = new JSONObject();
                one.put("row", count);
                one.put("e", e.getMessage());
                arr.add(one);
            }
        }

        ret.put("errors", arr);
        ret.put("rs", mapRs);
        ret.put("rsSize", mapRs.size());
        ret.put("orderIds", orderIds);
        ret.put("orderIdsSize", orderIds.size());
        ret.put("duplicateOrderIds", duplicateOrderIds);
        ret.put("duplicateOrderIdsSize", duplicateOrderIds.size());
        return ret;
    }
}

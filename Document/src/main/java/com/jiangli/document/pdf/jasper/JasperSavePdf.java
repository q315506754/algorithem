package com.jiangli.document.pdf.jasper;

import com.jiangli.common.utils.PathUtil;
import net.sf.jasperreports.engine.*;
import net.sf.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/15 0015 17:46
 */
public class JasperSavePdf {

    public static void main(String[] args) throws Exception {
        String document = PathUtil.getProjectPath("Document").getProjectBasePath();
        String buildPath = PathUtil.buildPath(document, "src/main/java/com/jiangli/document/pdf/jasper");
        System.out.println(buildPath);

//        String pdfFilePath = "C:\\Users\\Administrator\\Desktop\\t\\";
        String pdfFilePath = buildPath;
        String pdfName = "saved.pdf";
        String modelName = "FAX_RES_ToHotelOrder.jrxml";
        String pdfPath = pdfFilePath + pdfName;
        String modelPath = pdfFilePath + modelName;


        JSONObject message = JSONObject.fromObject("{createTime=\"2016/06/05\", fromCell=\"\", toName=\"测试/Ted\", fromFax=\"021-33275150,021-60741226\", toTel=\"0580-3866666，05803866667\", OrderType=\"取消单\", roomNum=\"1\", fromEmail=\"reservation@bingdian.com\", toCompany=\"舟山希尔顿酒店\", toFax=\"reservation.zhoushan@hilton.com\", orderId=\"11463436\", fromTel=\"021-60741225\", loginName=\"多途操作部\", liveName=\"温珍珍\", checkInDate=\"2016/06/05\", checkOutDate=\"2016/06/06\", cusLike=\"\", code=\"预付价含2早\", roomRate=\"06/05-06/06 单价RMB￥560元/晚/含2早，共计RMB￥560元。\", operator=\"5594a77fe4b06268883eed44\", toCell=\"13524779333/13857201465\", operatorIp=\"192.168.6.27\", payWay=\"月结\", hotel=\"测试希尔顿酒店\", room=\"测试双床房\"}");
        message.put("toFax", "60741226");

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.putAll(message);
        FileInputStream fis = new FileInputStream(new File(modelPath));
        JasperReport jasperReport = JasperCompileManager.compileReport(fis);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, new JREmptyDataSource());

        File pdFile = new File(pdfPath);
        JasperExportManager.exportReportToPdfFile(jasperPrint, pdFile.getAbsolutePath());


    }

}

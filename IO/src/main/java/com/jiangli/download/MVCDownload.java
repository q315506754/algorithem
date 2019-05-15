package com.jiangli.download;

/**
 * @author Jiangli
 * @date 2019/4/24 13:39
 */
public class MVCDownload {
    /**
     * 下载文件
     */
    //@RequestMapping(value = "/downloadFile")
    //public void downLoadFile(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "fileUrl", required = false) String fileUrl, HttpServletResponse response) {
    //    BufferedInputStream bis = null;
    //    BufferedOutputStream bos = null;
    //    OutputStream output = null;
    //    try {
    //        logger.info("downLoadFileStart:" + fileUrl);
    //        response.setContentType("application/octet-stream; charset=UTF-8");
    //        String ext = fileUrl.substring(fileUrl.lastIndexOf("."));
    //        name = name + ext;
    //        name = URLDecoder.decode(name, "UTF-8");
    //        response.setHeader("Content-Disposition", "attachment;fileName=\"" + new String(name.getBytes("GBK"), "ISO8859-1") + "\"");
    //        URL url = new URL(fileUrl);
    //        bis = new BufferedInputStream(url.openStream());
    //        output = response.getOutputStream();
    //        bos = new BufferedOutputStream(output);
    //        logger.info("downLoadFileCopyStream:" + fileUrl);
    //        byte[] buff = new byte[2048];
    //        int bytesRead;
    //        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
    //            logger.info("downLoadFileCopyStreamDetail:" + bytesRead);
    //            bos.write(buff, 0, bytesRead);
    //        }
    //        logger.info("downLoadFileEnd:" + fileUrl);
    //    } catch (Exception e) {
    //        logger.error("downLoadFileError:" + fileUrl + ":error:" + e.getMessage());
    //        e.printStackTrace();
    //    } finally {
    //        try {
    //            if (bos != null) {
    //                bos.flush();
    //                bos.close();
    //            }
    //            if (bis != null) {
    //                bis.close();
    //            }
    //            if (output != null) {
    //                output.close();
    //            }
    //        } catch (IOException e) {
    //            e.printStackTrace();
    //        }
    //    }
    //}
}

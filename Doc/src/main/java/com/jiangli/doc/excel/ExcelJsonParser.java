package com.jiangli.doc.excel;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/3/30 0030 17:15
 */
public class ExcelJsonParser {
    private Map<String, String> meta = new LinkedHashMap<String, String>();
    private JSONArray data;

    public ExcelJsonParser(JSONArray data) {
        this.data = data;
    }

    public void config(String key, String title) {
        meta.put(key, title);
    }

    private String nullToEmpty(Object obj) {
        return obj == null ? "" : obj.toString().trim();
    }

    public void formatCSV(String lineBreak, String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();

            BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));

            StringBuilder head = getHeadString();
            bf.write(head.toString());
            bf.write(lineBreak);

            for (Object o : data) {
                JSONObject one = (JSONObject) o;
//                if (one.containsKey(JoinQueryBuilder.ERRORS_KEY)) {
//                    continue;
//                }

                StringBuilder line = new StringBuilder();

                for (String key : meta.keySet()) {
                    final Object value = one.get(key);
                    line.append(nullToEmpty(value));
                    line.append(",");
                }
                line.deleteCharAt(line.length() - 1);
                bf.write(line.toString());
                bf.write(lineBreak);
            }
            bf.flush();
            bf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private StringBuilder getHeadString() {
        StringBuilder head = new StringBuilder();
        for (String key : meta.keySet()) {
            head.append(nullToEmpty(meta.get(key)));
            head.append(",");
        }
        head.deleteCharAt(head.length() - 1);
        return head;
    }
}

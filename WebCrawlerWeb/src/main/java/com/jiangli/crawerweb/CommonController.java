package com.jiangli.crawerweb;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.*;

/**
 * @author Jiangli
 *
 *         CreatedTime  2015/6/9 0009 10:39
 */
@Controller
@RequestMapping(value = "/")
public class CommonController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private HttpSession session;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    @Qualifier("basicDataSource")
    private DataSource dataSource;

    /**
     * http://localhost:8081/autoc/get
     *
     * @return
     */
//    @RequestMapping(value = "/data", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json;charset=utf-8")
    @RequestMapping(value = "/data", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    JSONArray data() {
        JSONArray arr = new JSONArray();
        System.out.println("starting query...");

        try {
            Connection cnnection = dataSource.getConnection();
//            System.out.println(cnnection);
            Statement statement = cnnection.createStatement();
//            ResultSet resultSet = statement.executeQuery("select * from Six limit 10");
            ResultSet resultSet = statement.executeQuery("select * from Six where marked=0");

//            ResultSetMetaData metaData = resultSet.getMetaData();
//            System.out.println(metaData.getClass());

            String[] stringFields = new String[]{"id","title","type","matchKey","createTime","url"};

            while (resultSet.next()) {
                JSONObject obj = new JSONObject();

                for (String stringField : stringFields) {
                    obj.put(stringField, resultSet.getString(stringField));
                }
                obj.put("sid", resultSet.getInt("sid"));
                obj.put("marked", resultSet.getInt("marked"));
                arr.add(obj);
            }

            resultSet.close();
            cnnection.close();
            statement.close();

            System.out.println(arr);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return arr;
    }

    @RequestMapping(value = "/mark", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    JSONArray mark(String sid,Integer marked) {
        JSONArray arr = new JSONArray();

        try {
            Connection cnnection = dataSource.getConnection();
            System.out.println(cnnection);
            PreparedStatement statement = cnnection.prepareStatement("update Six set marked=? where sid=?");
            statement.setInt(1,marked);
            statement.setString(2,sid);

            boolean execute = statement.execute();

            statement.close();
            cnnection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arr;
    }


}

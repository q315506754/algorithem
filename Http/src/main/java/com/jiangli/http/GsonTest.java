package com.jiangli.http;

import com.google.gson.Gson;
import com.jiangli.common.utils.ModelUtil;

import java.util.Map;

/**
 * Created by Jiangli on 2017/8/13.
 */
public class GsonTest {
    public static void main(String[] args) {
        Gson gson= new Gson();
        System.out.println(gson);

        System.out.println(gson.toJson(ModelUtil.generatePersons(1)));
        System.out.println(gson.toJson(ModelUtil.generatePersons(1).get(0)));

        Map map = gson.fromJson("{\"age\":0,\"name\":\"我名叫张0\",\"state\":\"状态0\"}", Map.class);
        System.out.println(map);

    }
}

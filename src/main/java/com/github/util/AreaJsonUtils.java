package com.github.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by leolin on 7/20/2018.
 */
public class AreaJsonUtils {

    public static JSONObject areaJson = null;
    public static Map<String,Object> areaMap = null;
    public static Map<String,String> areaMapRevert = new HashMap<>();

    public AreaJsonUtils() {
        StringBuffer ereas = new StringBuffer();
        InputStream fis = this.getClass().getResourceAsStream("/area_data.json");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis));
        String tmp = null;
        try {
            while((tmp = bufferedReader.readLine()) != null){
                ereas.append(tmp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        areaJson = JSONObject.parseObject(ereas.toString());
        areaMap = JSONPath.paths(areaJson);

        for(String s : areaMap.keySet()){
            String value = areaMap.get(s).toString();
            areaMapRevert.put(value,s.replace("/",""));
        }

        System.out.println(areaMapRevert);
    }

    /**
     * 获取城市名称对应的值
     * @param value
     * @return
     */
    public static String getAreaCode(String value){
        return areaMapRevert.get(value);
    }

    public static void main(String[] args) {
        try {
            new AreaJsonUtils();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

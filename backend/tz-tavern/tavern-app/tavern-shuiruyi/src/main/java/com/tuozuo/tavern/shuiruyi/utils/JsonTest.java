package com.tuozuo.tavern.shuiruyi.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.tuozuo.tavern.shuiruyi.model.AreaInfo;

import java.io.*;
import java.util.List;


public class JsonTest {

    //读取json文件
    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static List<AreaInfo> getAreaInfo() {
        List<AreaInfo> areaInfoList = Lists.newArrayList();
        String path = "D:\\项目\\tuozuo.git\\backend\\tz-tavern\\tavern-app\\tavern-shuiruyi\\src\\main\\resources\\area.json";
        String s = readJsonFile(path);
        JSONArray areas = JSONArray.parseArray(s);//构建JSONArray数组
        for (int i = 0; i < areas.size(); i++) {
            AreaInfo areaInfo = new AreaInfo();
            JSONObject key = areas.getJSONObject(i);
            String pCode = key.getString("value");
            String pName = key.getString("label");
            areaInfo.setAreaCode(pCode);
            areaInfo.setAreaLevel("province");
            areaInfo.setAreaName(pName);
            areaInfoList.add(areaInfo);
            JSONArray city = key.getJSONArray("children");
            if(city != null){
                for (int j = 0; j < city.size(); j++) {
                    JSONObject js1 = city.getJSONObject(j);
                    String cCode = js1.getString("value");
                    String cName = js1.getString("label");
                    areaInfo = new AreaInfo();
                    areaInfo.setAreaLevel("city");
                    areaInfo.setAreaName(cName);
                    areaInfo.setAreaCode(cCode);
                    areaInfo.setAreaParentCode(pCode);
                    areaInfoList.add(areaInfo);
                    JSONArray district = js1.getJSONArray("children");
                    if(district != null){
                        for (int k = 0; k < district.size(); k++) {
                            JSONObject js2 = district.getJSONObject(k);
                            String dCode = js2.getString("value");
                            String dName = js2.getString("label");
                            areaInfo = new AreaInfo();
                            areaInfo.setAreaLevel("district");
                            areaInfo.setAreaName(dName);
                            areaInfo.setAreaCode(dCode);
                            areaInfo.setAreaParentCode(cCode);
                            areaInfoList.add(areaInfo);
                        }
                    }
                }
            }




        }

        return areaInfoList;
    }

}
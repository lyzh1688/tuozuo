package com.tuozuo.tavern.organ.biz.util;

import com.google.common.collect.Lists;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/24 <br>
 */
public class FileUtils {

    public static List<String> getWordsFromFile(String path) throws IOException {
        List<String> resultList = Lists.newArrayList();
        BufferedReader reader = null;
        try {
            reader = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8);
            String str = null;
            while ((str = reader.readLine()) != null) {
                resultList.add(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }
        return resultList;
    }

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
}

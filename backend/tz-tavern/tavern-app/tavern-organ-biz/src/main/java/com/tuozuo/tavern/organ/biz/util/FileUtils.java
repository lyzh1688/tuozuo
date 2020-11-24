package com.tuozuo.tavern.organ.biz.util;

import com.google.common.collect.Lists;

import java.io.BufferedReader;
import java.io.IOException;
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
            reader = Files.newBufferedReader(Paths.get(path), StandardCharsets.ISO_8859_1);
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

}

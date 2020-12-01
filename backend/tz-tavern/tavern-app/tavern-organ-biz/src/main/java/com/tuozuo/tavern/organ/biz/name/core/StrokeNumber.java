package com.tuozuo.tavern.organ.biz.name.core;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/12/1 <br>
 */
@Component
public class StrokeNumber implements InitializingBean {

    private Map<String, Integer> strokeDictMap = Maps.newHashMap();
    private Map<String, List<String>> splitDictMap = Maps.newHashMap();

    @Value("${stroke.path:/opt/tuozuo/generateName/data/stroke.dat}")
    private String strokeDatPath;
    @Value("${stroke.path:/opt/tuozuo/generateName/data/chaizi-ft.dat}")
    private String chaziFtDatPath;


    private void initStrokeDitMap() throws IOException {
        BufferedReader reader = null;
        try {
            reader = Files.newBufferedReader(Paths.get(strokeDatPath), StandardCharsets.UTF_8);
            String str = null;
            while ((str = reader.readLine()) != null) {
                String[] strokes = str.split("|");
                this.strokeDictMap.putIfAbsent(strokes[1], Integer.parseInt(strokes[2]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }
    }

    private void initSplitDitMap() throws IOException {
        BufferedReader reader = null;
        try {
            reader = Files.newBufferedReader(Paths.get(strokeDatPath), StandardCharsets.UTF_8);
            String str = null;
            while ((str = reader.readLine()) != null) {
                String[] strokes = str.split("\\s");
                if (strokes.length < 2) {
                    continue;
                }
                List<String> tmpList = Lists.newArrayList();
                for (int i = 1; i < strokes.length; i++) {
                    tmpList.add(strokes[i]);
                }
                this.splitDictMap.putIfAbsent(strokes[1], tmpList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }
    }

   /* def get_stroke_number(word):
    total = 0
            for i in word:
            if "一" in i:
    total += 1
    elif "二" in i:
    total += 2
    elif "三" in i:
    total += 3
    elif "四" in i:
    total += 4
    elif "五" in i:
    total += 5
    elif "六" in i:
    total += 6
    elif "七" in i:
    total += 7
    elif "八" in i:
    total += 8
    elif "九" in i:
    total += 9
    elif "十" in i:
    total += 10
            else:
    total += stroke_dic[i]
            return get_final_number(word, total)*/
    public int getStrokeNumber(String word){
        int total = 0;


        return 0;
    }



    @Override
    public void afterPropertiesSet() throws Exception {
        this.initSplitDitMap();
        this.initStrokeDitMap();
    }
}

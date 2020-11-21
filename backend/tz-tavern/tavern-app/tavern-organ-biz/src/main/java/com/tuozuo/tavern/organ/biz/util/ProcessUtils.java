package com.tuozuo.tavern.organ.biz.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/20 <br>
 */
public class ProcessUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessUtils.class);

    public static List<String> getProcessResult(Process process) throws IOException {
        List<String> resultList = Lists.newArrayList();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(process.getInputStream(),"GBK"));
            String line;
            while ((line = in.readLine()) != null) {
                if (StringUtils.isNoneEmpty(line)) {
                    resultList.add(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            in.close();
        }
        return resultList;
    }


    public static boolean isError(Process process) throws IOException {
        return getError(process) != 0;
    }

    public static int getError(Process process) throws IOException {
        BufferedReader in = null;
        String line;
        int sum = 0;
        try {
            in = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = in.readLine()) != null) {
                LOGGER.error(line);
                sum += line.length();
            }
        } catch (Exception e) {

        } finally {
            in.close();
        }
        return sum;
    }

    public static String getErrorMsg(Process process) throws IOException {
        BufferedReader in = null;
        String line;
        StringBuilder sb = new StringBuilder();
        try {
            in = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = in.readLine()) != null) {
                sb.append(line.trim()).append("\n");
            }
        } catch (Exception e) {

        } finally {
            in.close();
        }
        return sb.toString();
    }

    // 判断一个字符串是否含有数字
    public static boolean hasDigit(String content) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches()) {
            flag = true;
        }
        return flag;
    }
}

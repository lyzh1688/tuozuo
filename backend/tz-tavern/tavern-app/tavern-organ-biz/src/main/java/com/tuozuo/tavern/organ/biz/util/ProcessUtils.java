package com.tuozuo.tavern.organ.biz.util;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/20 <br>
 */
public class ProcessUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessUtils.class);

    public static List<String> getProcessResult(Process process) throws IOException {
        List<String> resultList = Lists.newArrayList();
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            resultList.add(line);
        }
        return resultList;
    }


    public static boolean isError(Process process) throws IOException {
        return getError(process) != 0;
    }

    public static int getError(Process process) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String line;
        int sum = 0;
        while ((line = in.readLine()) != null) {
            LOGGER.error(line);
            sum += line.length();
        }
        return sum;
    }

    public static String getErrorMsg(Process process) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = in.readLine()) != null) {
            sb.append(line.trim()).append("\n");
        }
        return sb.toString();
    }


}

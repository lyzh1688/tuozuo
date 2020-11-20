package com.tuozuo.tavern.organ.biz.executor;

import com.google.common.collect.Lists;
import com.tuozuo.tavern.organ.biz.exeception.ExecuteException;
import com.tuozuo.tavern.organ.biz.util.ProcessUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/20 <br>
 */
@Repository
public class PythonExecutor implements Executor {

    private static final String cmd = "python";
    private static final Logger LOGGER = LoggerFactory.getLogger(PythonExecutor.class);


    @Override
    public List<String> executor(String... args) throws ExecuteException {

        String cmds = complete(args);
        long time = System.currentTimeMillis();
        Process process;
        try {
            process = Runtime.getRuntime().exec(cmds);
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            LOGGER.error("执行异常：", e);
            throw new ExecuteException();
        }
        List<String> resultList = Lists.newArrayList();
        try {
            if (ProcessUtils.isError(process)) {
                LOGGER.debug("执行器={} 标识={} 错误信息={}", args[0], args[1], ProcessUtils.getErrorMsg(process));
                return resultList;
            }
            resultList = ProcessUtils.getProcessResult(process);
        } catch (IOException e) {
            LOGGER.debug("执行器={} 标识={} 耗时={}ms", args[0], args[1], time);
        }
        time = System.currentTimeMillis() - time;
        LOGGER.debug("执行器={} 标识={} 耗时={}ms", args[0], args[1], time);

        return resultList;
    }


    private String complete(String... args) {
        List<String> list = Lists.newArrayList();
        list.add(cmd);
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                list.add(args[0]);
            }
        }
        String[] temp = new String[list.size()];
        return StringUtils.join(list.toArray(temp), " ");
    }


}

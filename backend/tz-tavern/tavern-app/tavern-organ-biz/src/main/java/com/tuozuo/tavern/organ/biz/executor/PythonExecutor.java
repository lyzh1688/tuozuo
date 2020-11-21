package com.tuozuo.tavern.organ.biz.executor;

import com.google.common.collect.Lists;
import com.tuozuo.tavern.organ.biz.exeception.ExecuteException;
import com.tuozuo.tavern.organ.biz.util.ProcessUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/20 <br>
 */
@Repository
public class PythonExecutor implements Executor {

    private static final String cmd = "python";
    private static final Logger LOGGER = LoggerFactory.getLogger(PythonExecutor.class);


    /* @Override
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
                 LOGGER.error("执行器={} 标识={} 错误信息={}", args[0], args[1], ProcessUtils.getErrorMsg(process));
 //                return resultList;
             }
             resultList = ProcessUtils.getProcessResult(process);
         } catch (IOException e) {
             LOGGER.error("执行器={} 标识={} 耗时={}ms", args[0], args[1], time);
         }
         time = System.currentTimeMillis() - time;
         LOGGER.error("执行器={} 标识={} 耗时={}ms", args[0], args[1], time);

         return resultList;
     }*/
    @Override
    public List<String> executor(String... args) throws ExecuteException, IOException {

        String cmds = complete(args);
        long time = System.currentTimeMillis();
        Process process = Runtime.getRuntime().exec(cmds);
        List<String> resultList = Lists.newArrayList();
        try {
            resultList = ProcessUtils.getProcessResult(process);
            //获取进程的标准输入流
            final InputStream is1 = process.getInputStream();
            //获取进城的错误流
            final InputStream is2 = process.getErrorStream();
            //启动两个线程，一个线程负责读标准输出流，另一个负责读标准错误流
            new Thread() {
                public void run() {
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(is1));
                    try {
                        String line1 = null;
                        while ((line1 = br1.readLine()) != null) {
                            if (line1 != null) {
                            }
                        }
                    } catch (IOException e) {
//                        e.printStackTrace();
                    } finally {
                        try {
                            is1.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();

            new Thread() {
                public void run() {
                    BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));
                    try {
                        String line2 = null;
                        while ((line2 = br2.readLine()) != null) {
                            if (line2 != null) {
                            }
                        }
                    } catch (IOException e) {
//                        e.printStackTrace();
                    } finally {
                        try {
                            is2.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();

            //可能导致进程阻塞，甚至死锁
            int ret = process.waitFor();
            if (ret == 1) {
                LOGGER.error("执行器={} 标识={} 错误信息={}", args[0], args[2], ProcessUtils.getErrorMsg(process));
                return new ArrayList<>();
            } else {
                LOGGER.info("执行器={} 标识={} ", args[0], args[2]);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                process.getErrorStream().close();
                process.getInputStream().close();
                process.getOutputStream().close();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
        time = System.currentTimeMillis() - time;
        LOGGER.error("执行器={} 标识={} 耗时={}ms", args[0], args[2], time);
        return resultList;
    }


    private String complete(String... args) {
        List<String> list = Lists.newArrayList();
        list.add(cmd);
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                list.add(args[i]);
            }
        }
        String[] temp = new String[list.size()];
        return StringUtils.join(list.toArray(temp), " ");
    }


}

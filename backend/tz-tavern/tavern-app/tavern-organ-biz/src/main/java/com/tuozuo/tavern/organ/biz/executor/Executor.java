package com.tuozuo.tavern.organ.biz.executor;

import com.tuozuo.tavern.organ.biz.exeception.ExecuteException;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/20 <br>
 */
public interface Executor {


    /**
     * 获取执行结果
     *
     * @param args 执行参数
     * @return 执行结果
     * @throws ExecuteException 执行异常
     */
    List<String> executor(String... args) throws ExecuteException;

}

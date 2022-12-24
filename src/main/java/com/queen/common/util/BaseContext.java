package com.queen.common.util;

/**
 * @author
 * @date 2022/12/01/19:12
 */

public class BaseContext {

    private  static ThreadLocal threadLocal =  new ThreadLocal<Long>();


    public static void setThreadLocal(Long id) {
        threadLocal.set(id);
    }

    public static Long getId() {
        Long o = (Long) threadLocal.get();
        return o;
    }

}

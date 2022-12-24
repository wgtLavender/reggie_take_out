package com.queen.common;

import lombok.Data;

/**
 * @author
 * @date 2022/11/27/11:55
 */
@Data
public class R<T> {

    private Integer code;

    private T data;

    private String msg;

    public static <T> R<T> success(T t) {
        R<T> r = new R<>();
        r.data = t;
        r.code = 1;
        r.setMsg("success");
        return r;
    }

    public static <T> R<T> error(String t) {
        R<T> r = new R<>();
        r.code = 0;
        r.msg = t;
        return r;
    }


}

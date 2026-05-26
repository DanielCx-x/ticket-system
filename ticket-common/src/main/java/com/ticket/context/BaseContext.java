package com.ticket.context;

/**
 * 保存当前请求线程中的用户信息。
 *
 * ThreadLocal 的作用：
 * 每个请求线程都有自己独立的一份 userId，互不影响。
 */
public class BaseContext {

    private static final ThreadLocal<Long> CURRENT_ID = new ThreadLocal<>();

    private BaseContext() {
    }

    public static void setCurrentId(Long id) {
        CURRENT_ID.set(id);
    }

    public static Long getCurrentId() {
        return CURRENT_ID.get();
    }

    public static void removeCurrentId() {
        CURRENT_ID.remove();
    }
}

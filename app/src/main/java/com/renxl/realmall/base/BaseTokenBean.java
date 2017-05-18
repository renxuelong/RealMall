package com.renxl.realmall.base;

/**
 * Created by renxl
 * On 2017/5/16 15:50.
 */

public class BaseTokenBean<T> {

    /**
     * 数据状态码
     * <p>1 成功  非 1 失败</p>
     */
    private int status;
    private String message;
    private String token;
    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public T getT() {
        return data;
    }

    public void setT(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseTokenBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                ", data=" + data +
                '}';
    }
}

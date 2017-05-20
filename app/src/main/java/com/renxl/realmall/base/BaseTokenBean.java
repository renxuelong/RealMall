package com.renxl.realmall.base;

/**
 * Created by renxl
 * On 2017/5/16 15:50.
 */

public class BaseTokenBean<T> extends BaseBean {

    private String token;
    private T data;

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

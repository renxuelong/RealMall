package com.renxl.realmall.base;

/**
 * Created by renxl
 * On 2017/5/20 10:58.
 */

public class BaseBean {

    /**
     * 数据状态码
     * <p>1 成功  非 1 失败</p>
     */
    int status;
    String message;

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
}

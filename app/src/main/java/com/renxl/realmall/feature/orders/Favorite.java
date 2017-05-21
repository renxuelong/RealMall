package com.renxl.realmall.feature.orders;

import com.renxl.realmall.feature.category.Wares;

/**
 * Created by renxl
 * On 2017/5/21 14:19.
 */

public class Favorite {
    private long id;
    private long userId;
    private long wareId;
    private String createTime;
    private Wares wares;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getWareId() {
        return wareId;
    }

    public void setWareId(long wareId) {
        this.wareId = wareId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Wares getWares() {
        return wares;
    }

    public void setWares(Wares wares) {
        this.wares = wares;
    }
}

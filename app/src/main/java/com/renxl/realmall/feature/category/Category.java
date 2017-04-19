package com.renxl.realmall.feature.category;

/**
 * Created by renxl
 * On 2017/4/18 13:54.
 */

public class Category {
    /**
     * id : 1
     * name : 热门推荐
     * sort : 1
     */

    private long id;
    private String name;
    private int sort;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}

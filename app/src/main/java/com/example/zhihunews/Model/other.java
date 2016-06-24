package com.example.zhihunews.Model;

/**
 * Created by Lxq on 2016/6/8.
 */

public class other {

    // 该主题日报编号
    private int id;
    //主题日报名称
    private String name;
    //供显示的图片地址
    private String thumbnail;
    //主题日报的介绍
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

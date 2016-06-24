package com.example.zhihunews.Model.PhoenixNews;

import java.util.List;

/**
 * Created by Lxq on 2016/6/17.
 */

public class Beauty {

    String id, title,thumbnail;
    List<BeautyImage> img;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<BeautyImage> getImg() {
        return img;
    }

    public void setImg(List<BeautyImage> img) {
        this.img = img;
    }
}

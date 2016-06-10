package com.example.zhihunews.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lxq on 2016/6/7.
 */

public class ThemeStory implements Serializable {

    private int id ;
    private String title;
    private List<String> images;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}

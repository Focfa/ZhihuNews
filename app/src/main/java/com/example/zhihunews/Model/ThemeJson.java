package com.example.zhihunews.Model;

import java.util.List;

/**
 * Created by Lxq on 2016/6/7.
 */

public class ThemeJson {

    //theme name
    private String name;
    //首页大图
    private String background;
    //主题描述
    private String description;

    private List<ThemeStory> stories;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ThemeStory> getStories() {
        return stories;
    }

    public void setStories(List<ThemeStory> stories) {
        this.stories = stories;
    }
}

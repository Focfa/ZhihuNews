package com.example.zhihunews.Model.PhoenixNews;

import java.util.List;

/**
 * Created by Lxq on 2016/6/18.
 */

public class VideoJson {


    private int totalPage;
    private String currentPage;
    List<VideoItem> item;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public List<VideoItem> getItem() {
        return item;
    }

    public void setItem(List<VideoItem> item) {
        this.item = item;
    }
}

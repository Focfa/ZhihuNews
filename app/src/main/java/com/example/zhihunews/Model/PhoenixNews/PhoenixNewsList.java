package com.example.zhihunews.Model.PhoenixNews;

import java.util.List;

/**
 * Created by Lxq on 2016/6/16.
 */

public class PhoenixNewsList {

    private String listId,type,currentPage,totalPage;

    private  List<SingleItem>  item;

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public List<SingleItem> getItem() {
        return item;
    }

    public void setItem(List<SingleItem> item) {
        this.item = item;
    }
}

package com.example.zhihunews.Model;

import java.util.List;

/**
 * Created by Lxq on 2016/6/7.
 */

public class ThemesListJson {

    private int limit;
    private List<other> others;


    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<other> getOthers() {
        return others;
    }

    public void setOthers(List<other> others) {
        this.others = others;
    }

}

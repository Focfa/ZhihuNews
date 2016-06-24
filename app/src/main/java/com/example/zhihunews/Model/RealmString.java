package com.example.zhihunews.Model;

import io.realm.RealmObject;

/**
 * Created by Lxq on 2016/5/27.
 */
public class RealmString extends RealmObject {
    private String val;

    public RealmString() {
    }

    public RealmString(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}

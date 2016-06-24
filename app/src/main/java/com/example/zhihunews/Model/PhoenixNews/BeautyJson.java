package com.example.zhihunews.Model.PhoenixNews;

import java.util.List;

/**
 * Created by Lxq on 2016/6/17.
 */

public class BeautyJson {

    private Meta meta;
    List<Beauty> body;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Beauty> getBody() {
        return body;
    }

    public void setBody(List<Beauty> body) {
        this.body = body;
    }
}

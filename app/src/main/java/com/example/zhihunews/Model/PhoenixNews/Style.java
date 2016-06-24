package com.example.zhihunews.Model.PhoenixNews;

import java.util.List;

/**
 * Created by Lxq on 2016/6/16.
 */

public class Style {

    /*
    * "type":"slides","images":["http:\/\/d.ifengimg.com\/w155_h107_q75\/p3.
    * ifengimg.com\/a\/2016_25\/810cf44892da057_size56_w440_h58
    * 6.jpg","http:\/\/d.ifengimg.com\/w155_h107_q75\/p1.ifengimg.com\/
    * a\/2016_25\/44638c7ace14f67_size92_w440_h586.jpg","http:\/\/d.i
    * fengimg.com\/w155_h107_q75\/p2.ifengimg.com\/a\/2016_25\/8f6b527a701f250
    * _size55_w440_h587.jpg"]},
    *
    * */
    private String type;
    private List<String> images;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}

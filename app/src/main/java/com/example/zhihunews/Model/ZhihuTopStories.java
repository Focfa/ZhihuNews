package com.example.zhihunews.Model;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Lxq on 2016/5/23.
 */
public class ZhihuTopStories extends RealmObject {
    /*"image":"http://pic3.zhimg.com/351c8bf10ee7e23e6b620d3b340fa6f6.jpg",
      "type":0,
      "id":8339190,
      "ga_prefix":"052308",
      "title":"当时髦的 VR 碰上了宇宙，酷炫程度一不小心就爆表了"
    *
    */
    private int id;
    private int type;
    private String ga_prefix;
    private String title;
    private String image;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}

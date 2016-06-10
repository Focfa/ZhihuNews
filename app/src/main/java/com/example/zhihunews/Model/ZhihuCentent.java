package com.example.zhihunews.Model;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Lxq on 2016/5/26.
 */
public class ZhihuCentent extends RealmObject {

    @PrimaryKey
    private int id;
    private String body;
    private String image_source;
    private String title;
    private String image;
    private String share_url;
    private RealmList<RealmString> css;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
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

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public RealmList<RealmString> getCss() {
        return css;
    }

    public void setCss(RealmList<RealmString> css) {
        this.css = css;
    }
}

package com.example.zhihunews.Model.PhoenixNews;

/**
 * Created by Lxq on 2016/6/17.
 */

public class SingleItem {
    /*
    * {"thumbnail":"http:\/\/d.ifengimg.com\/w132_h94_q75\/p1.ifengimg.com\
    * /ifengimcp\/pic\/20160613\/07bdb9ffe9ca246d1af4_size12_w28
    * 0_h200.jpg","online":"1","title":"孟建柱看望美国司法部部长 转达习近平慰问",
    * "source":"凤凰卫视","updateTime":"2016\/06\/13
    * 13:34:36","id":"http:\/\/api.iclient.ifeng.com\/ipadtestdoc?aid=110131044",
    * "documentId":"imcp_110131044","type":"doc","hasVideo"
    * :true,"commentsUrl":"http:\/\/wap.ifeng.com\/news.jsp?aid=110131044",
    * "comments":"5","commentsall":"183","link":{"type":"doc","url":"http:\/
    * \/api.iclient.ifeng.com\/ipadtestdoc?aid=110131044"}}
    *
    * */


    private String thumbnail,title,updateTime,id,commentsUrl,commentsall,source,type;
    private Style style;

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommentsUrl() {
        return commentsUrl;
    }

    public void setCommentsUrl(String commentsUrl) {
        this.commentsUrl = commentsUrl;
    }

    public String getCommentsall() {
        return commentsall;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setCommentsall(String commentsall) {
        this.commentsall = commentsall;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

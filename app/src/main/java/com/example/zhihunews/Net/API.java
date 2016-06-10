package com.example.zhihunews.Net;

/**
 * Created by Lxq on 2016/5/22.
 */
public class API {
    public static final int TYPE_LATEST = 0;
    public static final int TYPE_BEFORE = 1;
    //zhihu API
    //主url，后面加上内容id则可查看具体文章内容
    public static final String BASE_URL_ZHIHU = "http://news.at.zhihu.com/api/4/news/";
    //最新热闻
    public static final String NEWS_LATEST = "http://news-at.zhihu.com/api/4/news/latest";
    //过往热闻
    public static final String NEW_BEFORE = "http://news.at.zhihu.com/api/4/news/before/";
    //启动图片
    public static final String START_IMAGE = "http://news-at.zhihu.com/api/4/start-image/1080*1920";
    //主题日报列表
    public static final String NEWS_THEMES = "http://news-at.zhihu.com/api/4/themes";
    //主题内容列表，获取方式后面加上在主题日报列表获得的主题id,再在其后加上/before/id(某天的最后id，可查看过往主题内容列表)
    public static final String THEME_CONTENT_LISTS = "http://news-at.zhihu.com/api/4/theme/";

    public static final String HOT_NEWS = "http://news-at.zhihu.com/api/3/news/hot";
}

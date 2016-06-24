package com.example.zhihunews.Net;

/**
 * Created by Lxq on 2016/5/22.
 */
public class API {


    public static final int TYPE_LATEST = 0;
    public static final int TYPE_BEFORE = 1;

    public static final String TYPE_SYLB = "headline";//头条
    public static final String TYPE_KJ = "science"; //科技
    public static final String TYPE_FC = "housing";//房产
    public static final String TYPE_QC = "car";//汽车
    public static final String TYPE_LY = "travel";//旅游
    public static final String TYPE_YL = "showbiz ";//娱乐
    public static final String TYPE_BEAUTY = "beauty";//美女
    public static final String TYPE_PET = "pet";//萌物
    public static final String TYPE_FUN = "fun";//fun来了
    public static final String TYPE_JS = "military";//军事
    public static final String TYPE_DS = "read";//读书
    public static final String TYPE_DY = "movie";//电影
    public static final String TYPE_TY = "sports";//体育
    public static final String TYPE_YY = "music";//音乐
    public static final String TYPE_JK = "geeker";//极客
    public static final String TYPE_XS = "novel";//小说
    public static final String TYPE_TV = "TvSHow";//电视娱乐
    public static final String TYPE_SP = "video";//电视娱乐


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



    //PhoenixNewsAPI
    public static final String BASE_URL_PHOENIX = "http://api.iclient.ifeng.com/";
    //头条,page后加上1则为最新，加上2...加载更多
    public static final String HEADLINE_PHOENIX = "http://api.iclient.ifeng.com/ClientNews?id=SYLB10,SYDT10&page=";
    //科技
    public static final String SLIENCE_PHOENIX = "http://api.iclient.ifeng.com/ClientNews?id=KJ123,FOCUSKJ123&page=";
    //房产
    public static final String HOUSING_PHOENIX = "http://api.iclient.ifeng.com/ClientNews?id=FC81,FOCUSFC81&page=";
    //汽车
    public static final String CAR_PHOENIX = "http://api.iclient.ifeng.com/ClientNews?id=QC45,FOCUSQC45&page=";
    //旅游
    public static final String TRAVEL_PHOENIX = "http://api.iclient.ifeng.com/ClientNews?id=LY67,FOCUSLY67&page=";
    //娱乐
    public static final String SHOWBIZ_PHOENIX = "http://api.iclient.ifeng.com/ClientNews?id=YL53,FOCUSYL53&page=";
    //美女 古风：type=beauty_classic  清纯：type=beauty_pure  萌妹：beauty_adorable  气质：beauty_sublimate
    // 清新：beauty_fresh   阳光：beauty_sunshine
    public static final String BEAUTY_PHOENIX = "http://api.3g.ifeng.com/clientShortNews?type=beauty&page=";
    //fun来了
    public static final String FUN_PHOENIX = "http://api.3g.ifeng.com/ClientNews?id=DZPD,FOCUSDZPD&page=";
    //萌物
    public static final String PET_PHOENIX = "http://api.3g.ifeng.com/clientShortNews?type=pet&page=";
    //视频
    public static final String VEDIO_PHOENIX = "http://api.iclient.ifeng.com/ifengvideoList?&page=";
    //军事  uid 必选，值大于1   antion=auto /up/down
    public static final String MILITARY_PHOENIX = "http://api.iclient.ifeng.com/ClientNews?id=JS83,FOCUSJS83&uid=10&aciton=";
    //体育
    public static final String SPORTS_PHOENIX = "http://api.iclient.ifeng.com/ClientNews?id=TY43,FOCUSTY43,TYLIVE&page=";

    //读书    uid 必选，值大于1   antion=auto /up/down
    public static final String READ_PHOENIX = "http://api.iclient.ifeng.com//ClientNews?id=DS57,FOCUSDS57&uid=16&action=";

    //电影
    public static final String MOVIE_PHOENIX = "http://api.iclient.ifeng.com/ClientNews?id=DYPD&uid=122&action=";

    //音乐
    public static final String MUSIC_PHOENIX = "http://api.iclient.ifeng.com/clientChannelNewsSearch?k=音乐&page=";

    //极客
    public static final String GEEKER_PHOENIX = "http://api.iclient.ifeng.com/clientChannelNewsSearch?k=极客&page=";

    //电视娱乐
    public static final String TVSHOW_PHOENIX = "http://api.iclient.ifeng.com/clientChannelNewsSearch?k=电视娱乐&page=";

    //小说,返回html
    public static final String NOVEL_PHOENIX = "http://iyc.ifeng.com/ycfornews/index.shtml";

    public static final String LIVE_PHOENIX = "http://zv.3gv.ifeng.com/live/zhongwen.m3u8";

}

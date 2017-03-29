package com.study.whutwf.doubanmovie.support;

/**
 * Created by whutwf on 17-3-22.
 */

public class Constants {

    public Constants() {}

    //双括号”{{}}”,用来初始化，使代码简洁易读。
    //第一层括弧实际是定义了一个匿名内部类 (Anonymous Inner Class)，
    //第二层括弧实际上是一个实例初始化块 (instance initializer block)，这个块在内部匿名类构造时被执行。
//    public static HashMap<String, String> paramsHashMap = new HashMap<String, String>() {
//        {
//            paramsHashMap.put(Constants.Params.DOUBAN_MOVIE_START, "");
//            paramsHashMap.put(Constants.Params.DOUBAN_MOVIE_TAG, "");
//            paramsHashMap.put(Constants.Params.DOUBAN_MOVIE_COUNT, "");
//            paramsHashMap.put(Constants.Params.DOUBAN_MOVIE_QUERY, "");
//        }
//    };

    public static final class Urls {
        public static final String DOUBAN_MOVIE_API = "https://api.douban.com/v2/movie/";
        //后加?start={page}
        public static final String DOUBAN_MOVIE_TOP250 = DOUBAN_MOVIE_API + "top250";
        public static final String DOUBAN_MOVIE_SEARCH = DOUBAN_MOVIE_API + "search";
        //需要权限
        //正在上映
        public static final String DOUBAN_MOVIE_IN_THEATERS = DOUBAN_MOVIE_API + "in_theaters";
        public static final String DOUBAN_MOVIE_COMING_SOON = DOUBAN_MOVIE_API + "coming_soon";
        //口碑榜
        public static final String DOUBAN_MOVIE_WEEKLY = DOUBAN_MOVIE_API + "weekly";
        //新片榜
        public static final String DOUBAN_MOVIE_NEW_MOVIES = DOUBAN_MOVIE_API + "new_movies";

    }

    public static final class Params {
        public static final String DOUBAN_MOVIE_START = "start";
        public static final String DOUBAN_MOVIE_QUERY = "q";
        public static final String DOUBAN_MOVIE_TAG = "tag";
        public static final String DOUBAN_MOVIE_COUNT = "count";
    }

    public static final class HelpStrings {
        public static final String SHARE_FROME_DOUBAN_MOVIE = "分享来自豆瓣电影";
    }

    public static final class PackageInformation {
        public static final String DOUBAN_PACKAGE_NAME = "com.douban.frodo";
    }
}

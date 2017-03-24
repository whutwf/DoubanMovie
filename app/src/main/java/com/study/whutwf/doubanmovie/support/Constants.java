package com.study.whutwf.doubanmovie.support;

/**
 * Created by whutwf on 17-3-22.
 */

public class Constants {

    public Constants() {}

    public static final class Urls {
        //后加?start={page}
        public static final String DOUBAN_MOVIE_TOP250 = "https://api.douban.com/v2/movie/top250";
    }

    public static final class Params {
        public static final String DOUBAN_MOVIE_TOP250_START = "start";
    }
}

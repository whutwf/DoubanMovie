package com.study.whutwf.doubanmovie.db;

/**
 * Created by whutwf on 17-3-31.
 */

public class MovieItemDbSchema {


    public static final class MovieItemDb {

        public static final class DbBaseSettings {
            public static final int VERSION = 1;

            public static final String DATABASE_NAME = "movieItemBase.db";

            public static final String TABLE_TOP250 = "movie_top250";
            public static final String TABLE_IN_THEATERS = "movie_in_theaters";
            public static final String TABLE_COMING_SOON = "movie_coming_soon";
            public static final String TABLE_PAGE_INFO = "movie_page_info";

            public static final String TABLE_SEARCH = "movie_search";
        }


        public static final class MovieItemCols {
            public static final String ID = "id";
            public static final String TITLE = "title";
            public static final String YEAR = "year";
            public static final String ALT = "alt";
            public static final String IMAGE_SMALL = "image_small";
            public static final String IMAGE_LARGE = "image_large";
            public static final String IMAGE_MEDIUM = "image_medium";
            public static final String AVERAGE_RATING = "average_rating";
            public static final String ORIGINAL_TITLE = "original_title";

            public static final String TAG = "tag";
            public static final String PAGE_EVEERY_COUNT = "every_count";
            public static final String PAGE_TOTAL = "page_total";
        }

        public static final class SqlString {
            private static String baseString = "(" +
                    "_id integer primary key autoincrement," + " " +
                    MovieItemCols.ID + "," + " " +
                    MovieItemCols.TITLE + "," + " " +
                    MovieItemCols.ORIGINAL_TITLE + "," + " " +
                    MovieItemCols.ALT + "," + " " +
                    MovieItemCols.AVERAGE_RATING + "," + " " +
                    MovieItemCols.YEAR + "," + " " +
                    MovieItemCols.IMAGE_LARGE + "," + " " +
                    MovieItemCols.IMAGE_MEDIUM + "," + " " +
                    MovieItemCols.IMAGE_SMALL +
                    ")";

            public static final String TOP250 = "create table" + " " +
                    DbBaseSettings.TABLE_TOP250 + baseString;

            public static final String IN_THEATERS = "create table" + " " +
                    DbBaseSettings.TABLE_IN_THEATERS + baseString;

            public static final String COMING_SOON = "create table" + " " +
                    DbBaseSettings.TABLE_COMING_SOON + baseString;

            public static final String PAGE_INFO = "create table" + " " +
                    DbBaseSettings.TABLE_PAGE_INFO + "(" +
                    "_id integer primary key autoincrement," + " " +
                    MovieItemCols.TAG + "," + " " +
                    MovieItemCols.PAGE_EVEERY_COUNT + "," + " " +
                    MovieItemCols.PAGE_TOTAL +
                    ")";
        }
    }
}

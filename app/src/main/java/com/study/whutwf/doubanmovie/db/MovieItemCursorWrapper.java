package com.study.whutwf.doubanmovie.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.study.whutwf.doubanmovie.db.MovieItemDbSchema.MovieItemDb;
import com.study.whutwf.doubanmovie.support.Constants;

import java.util.HashMap;

/**
 * Created by whutwf on 17-3-31.
 */

public class MovieItemCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public MovieItemCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public HashMap<String, String> getPageInfo() {
        String count = getString(getColumnIndex(MovieItemDb.MovieItemCols.PAGE_EVEERY_COUNT));
        String total = getString(getColumnIndex(MovieItemDb.MovieItemCols.PAGE_TOTAL));

        HashMap<String, String> pageInfoHashMap = new HashMap<>();
        pageInfoHashMap.put(Constants.Params.DOUBAN_MOVIE_TOTAL, total);
        pageInfoHashMap.put(Constants.Params.DOUBAN_MOVIE_COUNT, count);
        return pageInfoHashMap;
    }
}

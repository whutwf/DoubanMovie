package com.study.whutwf.doubanmovie.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.study.whutwf.doubanmovie.bean.MovieItem;
import com.study.whutwf.doubanmovie.db.MovieItemCursorWrapper;
import com.study.whutwf.doubanmovie.db.MovieItemDbSchema.MovieItemDb;

/**
 * Created by whutwf on 17-3-31.
 */

public class MovieDbUtils {

    //ContentValues键值存储类,只用于SQLite数据
    //负责处理数据库的写入和更新操作的辅助类
    public static ContentValues getContentValues(MovieItem movieItem) {
        ContentValues values = new ContentValues();
        values.put(MovieItemDb.MovieItemCols.ID, movieItem.getId());
        values.put(MovieItemDb.MovieItemCols.TITLE, movieItem.getTitle());
        values.put(MovieItemDb.MovieItemCols.ORIGINAL_TITLE, movieItem.getOriginalTitle());
        values.put(MovieItemDb.MovieItemCols.ALT, movieItem.getAlt());
        values.put(MovieItemDb.MovieItemCols.YEAR, movieItem.getYear());
        values.put(MovieItemDb.MovieItemCols.AVERAGE_RATING, movieItem.getRating());
        values.put(MovieItemDb.MovieItemCols.IMAGE_SMALL, movieItem.getImageUrls().get(0));
        values.put(MovieItemDb.MovieItemCols.IMAGE_MEDIUM, movieItem.getImageUrls().get(1));
        values.put(MovieItemDb.MovieItemCols.IMAGE_LARGE, movieItem.getImageUrls().get(2));

        return values;
    }

    public static ContentValues getContentValues(String tag, String count, String total) {
        ContentValues values = new ContentValues();
        values.put(MovieItemDb.MovieItemCols.TAG, tag);
        values.put(MovieItemDb.MovieItemCols.PAGE_EVEERY_COUNT, count);
        values.put(MovieItemDb.MovieItemCols.PAGE_TOTAL, total);
        return values;
    }

    public static boolean insert(SQLiteDatabase db, String tableName, ContentValues values) {

        /**
         * 第二个参数的作用
         * * @param nullColumnHack optional; may be <code>null</code>.
         *            SQL doesn't allow inserting a completely empty row without
         *            naming at least one column name.  If your provided <code>values</code> is
         *            empty, no column names are known and an empty row can't be inserted.
         *            If not set to null, the <code>nullColumnHack</code> parameter
         *            provides the name of nullable column name to explicitly insert a NULL into
         *            in the case where your <code>values</code> is empty.
         */
        if (db.insert(tableName, null, values) != -1) {
            return true;
        } else {
            return false;
        }
    }

    public static MovieItemCursorWrapper queryAll(SQLiteDatabase db, String tableName,
                                                  String whereClause, String[] whereArgs) {
        Cursor cursor = db.query(
                tableName,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new MovieItemCursorWrapper(cursor);
    }

    public static MovieItemCursorWrapper queryWithSql(SQLiteDatabase db, String sql, String[] selectionArgs) {
        /**
         * 避免SQL注入
         * rawQuery("SELECT id, name FROM people WHERE name = ? AND id = ?", new String[] {"David", "2"});
         *  * @param selectionArgs You may include ?s in where clause in the query,
         *     which will be replaced by the values from selectionArgs. The
         *     values will be bound as Strings.
         */
        return new MovieItemCursorWrapper(db.rawQuery(sql, selectionArgs));
    }
}

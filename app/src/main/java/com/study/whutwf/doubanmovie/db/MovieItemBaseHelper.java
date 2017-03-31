package com.study.whutwf.doubanmovie.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.study.whutwf.doubanmovie.db.MovieItemDbSchema.MovieItemDb;


/**
 * Created by whutwf on 17-3-31.
 */

public class MovieItemBaseHelper extends SQLiteOpenHelper {

    private String mSqlString;

    public MovieItemBaseHelper(Context context, String sqlString) {
        super(context, MovieItemDb.DbBaseSettings.DATABASE_NAME, null, MovieItemDb.DbBaseSettings.VERSION);
        this.mSqlString = sqlString;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(mSqlString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

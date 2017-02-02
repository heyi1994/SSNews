package com.heyi.UniversityNews.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by heyi on 2017/1/14.
 */

public class UserInfoDataBase extends SQLiteOpenHelper {
    public UserInfoDataBase(Context context) {
        super(context, "user_info_database.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table info(id integer primary key autoincrement," +
                "name varchar(255) not null,age integer not null,phone varchar(255) not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

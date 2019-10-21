package com.android.bookrecode;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {
    //SQLiteDatabase的一个帮助类， 用来管理数据库的创建和版本的更新
    private static final String db_name = "myDataBase";//自定义的数据库名；
    private static final int version =1;//版本号

    public MyOpenHelper(Context context) {
        super(context, db_name, null, version);
    }

    public void onCreate(SQLiteDatabase db) {
        String  sql = "create table mybook(" +              //表名设置为mybook
                "ids integer PRIMARY KEY autoincrement," +   //设置id自增
                "bookname text," +                    //设置书名为文本类型
                "state text," +                       //设状态为文本类型
                "thought text,"+                      //设感言为文本类型
                "times text)";                        //设置时间为文本类型

        db.execSQL(sql);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub,版本更新时调用
    }
}

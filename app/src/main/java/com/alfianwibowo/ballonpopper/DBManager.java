package com.alfianwibowo.ballonpopper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

public class DBManager {

    private SQLiteDatabase sqlDB;
    private static final String DBName = "nilai_tertinggi";
    private static final String TName = "nilai";
    public static final String Nama = "Nama";
    public static final String Nilai = "Nilai";
    private static final int DBVersion = 1;

    private static final String CreateTable = "Create table IF NOT EXISTS " +TName+
            "(ID integer PRIMARY KEY AUTOINCREMENT,"+ Nama+
            " text,"+ Nilai + " int);";
    
    static class DatabaseHelperUser extends SQLiteOpenHelper {

        Context context;
        DatabaseHelperUser(Context context) {
            super(context,DBName,null,DBVersion);
            this.context=context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CreateTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("Drop table IF EXISTS "+ TName);
            onCreate(db);
        }
    }

    public DBManager(Context context) {
        DatabaseHelperUser db = new DatabaseHelperUser(context);
        sqlDB=db.getWritableDatabase();
    }

    void Insert(ContentValues values) {
        sqlDB.insert(TName, "", values);
    }

    public Cursor Query(String[] Projection, String Selection, String[] SelectionArgs, String SortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TName);

        return queryBuilder.query(sqlDB, Projection, Selection, SelectionArgs,
                null, null, SortOrder+" DESC");

    }

}

package com.quotescollection.quotesdata.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.quotescollection.quotesdata.models.CategoryListModel;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "quotescollectiondb";
    private static final String TABLE_Users = "quotesdetails";
    private static final String KEY_ID = "id";
    private static final String KEY_QUOTES = "quotes";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_CATEGORY_NAME = "cname";
    private static final String FAV_QUOTES_TABLE = "favquotes";
    private static final String FAV_ID = "id";
    private static final String FAV_QUOTES = "quotes";
    private static final String FAV_AUTHOR = "author";
    private static final String FAV_CATEGORY_NAME = "cname";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table quotesdetails (id integer primary key ,cname string,quotes string,author string)");

        db.execSQL("create table favquotes (id integer primary key ,cname string,quotes string,author string)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        db.execSQL("DROP TABLE IF EXISTS " + FAV_QUOTES_TABLE);
        onCreate(db);

    }

    public void insertQuotesDetails(String c_name, String quotes, String author) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_CATEGORY_NAME, c_name);
        contentValues.put(KEY_QUOTES, quotes);
        contentValues.put(KEY_AUTHOR, author);
        db.insert(TABLE_Users, null, contentValues);
    }

    public void insertFavQuotes( String quotes, String author, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FAV_QUOTES, quotes);
        contentValues.put(FAV_AUTHOR, author);
        contentValues.put(FAV_CATEGORY_NAME, category);
        db.insert(FAV_QUOTES_TABLE, null, contentValues);
    }
    public void RemoveFev(String quest) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FAV_QUOTES_TABLE, FAV_QUOTES+"=?", new String[]{quest});
    }
    public ArrayList<CategoryListModel> getFavQuotes() {
        ArrayList<CategoryListModel> data = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from " + FAV_QUOTES_TABLE, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(FAV_ID));
                String quotes = cursor.getString(cursor.getColumnIndex(FAV_QUOTES));
                String author = cursor.getString(cursor.getColumnIndex(FAV_AUTHOR));
                String category = cursor.getString(cursor.getColumnIndex(FAV_CATEGORY_NAME));
                data.add(new CategoryListModel(quotes,author,0,category));
            } while (cursor.moveToNext());
        }
        return data;
    }
}

package ru.samsung.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
public class DBQuotes{
    private static final String DATABASE_NAME = "quotes.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Quotes";

    // Название столбцов
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "Title";

    // Номера столбцов
    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_TITLE = 1;

    private SQLiteDatabase mDataBase;
    private OpenHelper mOpenHelper;

    public DBQuotes(Context context) {
        mOpenHelper = new OpenHelper(context);
        try {
            mDataBase = mOpenHelper.getWritableDatabase();
        }
        catch (SQLiteException ex){
            mDataBase = mOpenHelper.getReadableDatabase();
        }
    }


    public long insert(String quote, String bookName){
        mOpenHelper.createIfNotExist(bookName);
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_TITLE, quote);
        return mDataBase.insert(bookName, null, cv);
    }


private class OpenHelper extends SQLiteOpenHelper {
    // Данные базы данных и таблиц


        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT); ";
            db.execSQL(query);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

        public void createIfNotExist(String bookName) {
            mDataBase.execSQL("CREATE TABLE IF NOT EXISTS "+bookName + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT); ");
        }
    }
}
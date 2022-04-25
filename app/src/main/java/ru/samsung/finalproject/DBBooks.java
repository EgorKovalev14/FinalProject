package ru.samsung.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBBooks {

    private static final String DATABASE_NAME = "books.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Books";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_BOOK = "BookName";


    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_BOOK = 1;


    private SQLiteDatabase mDataBase;
    private OpenHelper mOpenHelper;

    public DBBooks(Context context) {
        mOpenHelper = new OpenHelper(context);
        try {
            mDataBase = mOpenHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            mDataBase = mOpenHelper.getReadableDatabase();
        }
    }


    public long insert(String bookName) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_BOOK, bookName);
        return mDataBase.insert(TABLE_NAME, null, cv);
    }

    public BookFromDB select(long id) {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        mCursor.moveToFirst();
        String book = mCursor.getString(NUM_COLUMN_BOOK);
        return new BookFromDB(book);

    }

    public ArrayList<BookFromDB> selectAll() {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, null, null, null, null, null);

        ArrayList<BookFromDB> arr = new ArrayList<BookFromDB>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                long id = mCursor.getLong(NUM_COLUMN_ID);
                String book = mCursor.getString(NUM_COLUMN_BOOK);
                arr.add(new BookFromDB(book));
            } while (mCursor.moveToNext());
        }
        return arr;
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
                    COLUMN_BOOK + " TEXT);";
            db.execSQL(query);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}

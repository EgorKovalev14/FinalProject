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

    private static final int DATABASE_VERSION = 14;
    private static final String TABLE_NAME = "Books";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_BOOK = "BookName";
    private static final String COLUMN_CONTENT_ID = "ContentId";
    private static final String COLUMN_SCROLL = "Scroll";

    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_BOOK = 1;
    private static final int NUM_COLUMN_CONTENT_ID = 2;
    private static final int NUM_COLUMN_SCROLL = 3;



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

    public void delete(long id) {
        mDataBase.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] { String.valueOf(id) });
    }


    public long insert(String bookName, int content_id, int scroll) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_BOOK, bookName);
        cv.put(COLUMN_CONTENT_ID, content_id);
        cv.put(COLUMN_SCROLL, scroll);
        return mDataBase.insert(TABLE_NAME, null, cv);
    }
    public int update(BookFromDB bookFromDB) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_BOOK, bookFromDB.getBookFromDBName());
        return mDataBase.update(TABLE_NAME, cv, COLUMN_ID + " = ?",new String[] { String.valueOf(bookFromDB.getId())});
    }
    public BookFromDB select(long id) {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        mCursor.moveToFirst();
        int bid = mCursor.getInt(NUM_COLUMN_ID);
        String book = mCursor.getString(NUM_COLUMN_BOOK);
        int content_id = mCursor.getInt(NUM_COLUMN_CONTENT_ID);
        int scroll = mCursor.getInt(NUM_COLUMN_SCROLL);
        return new BookFromDB(bid, book, content_id, scroll);

    }

    public ArrayList<BookFromDB> selectAll() {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, null, null, null, null, null);

        ArrayList<BookFromDB> arr = new ArrayList<BookFromDB>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                int id1 = mCursor.getInt(NUM_COLUMN_ID);
                String book = mCursor.getString(NUM_COLUMN_BOOK);
                int content_id = mCursor.getInt(NUM_COLUMN_CONTENT_ID);
                int scroll = mCursor.getInt(NUM_COLUMN_SCROLL);
                arr.add(new BookFromDB(id1, book, content_id, scroll));
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
                    COLUMN_BOOK + " TEXT, " + COLUMN_CONTENT_ID + " INTEGER, " + COLUMN_SCROLL + " INTEGER);";
            db.execSQL(query);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}

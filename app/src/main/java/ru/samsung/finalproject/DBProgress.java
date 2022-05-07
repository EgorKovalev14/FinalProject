package ru.samsung.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBProgress {

    private static final String P_DATABASE_NAME = "progress.db";

    private static final int P_DATABASE_VERSION = 0;
    private static final String P_TABLE_NAME = "Progress";

    private static final String P_COLUMN_ID = "_id";
    private static final String P_COLUMN_PROGRESS = "ListScroll" ;

    private static final int P_NUM_COLUMN_ID = 0;
    private static final int P_NUM_COLUMN_SCROLL = 1;

    private SQLiteDatabase mDataBase;
    private OpenHelper mOpenHelper;

    public DBProgress(Context context) {
        mOpenHelper = new OpenHelper(context);
        try {
            mDataBase = mOpenHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            mDataBase = mOpenHelper.getReadableDatabase();
        }
    }

    public void delete(long id) {
        mDataBase.delete(P_TABLE_NAME, P_COLUMN_ID + " = ?", new String[] { String.valueOf(id) });
    }


    public long insert(int progress) {
        ContentValues cv = new ContentValues();
        cv.put(P_COLUMN_PROGRESS, progress);
        return mDataBase.insert(P_TABLE_NAME, null, cv);
    }
    public int update(ProgressFromDB progressFromDB) {
        ContentValues cv=new ContentValues();
        cv.put(P_COLUMN_PROGRESS, progressFromDB.getProgress());
        return mDataBase.update(P_TABLE_NAME, cv, P_COLUMN_ID + " = ?",new String[] { String.valueOf(progressFromDB.getId())});
    }
    public ProgressFromDB select(long id) {
        Cursor mCursor = mDataBase.query(P_TABLE_NAME, null, P_COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        mCursor.moveToFirst();
        int bid = mCursor.getInt(P_NUM_COLUMN_ID);
        int prog = mCursor.getInt(P_NUM_COLUMN_SCROLL);
        return new ProgressFromDB(bid, prog);

    }

    public ArrayList<ProgressFromDB> selectAll() {
        Cursor mCursor = mDataBase.query(P_TABLE_NAME, null, null, null, null, null, null);

        ArrayList<ProgressFromDB> arr = new ArrayList<ProgressFromDB>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                int id1 = mCursor.getInt(P_NUM_COLUMN_ID);
                int prog = mCursor.getInt(P_NUM_COLUMN_SCROLL);
                arr.add(new ProgressFromDB(id1, prog));
            } while (mCursor.moveToNext());
        }
        return arr;
    }


    private class OpenHelper extends SQLiteOpenHelper {
        // Данные базы данных и таблиц


        OpenHelper(Context context) {
            super(context, P_DATABASE_NAME, null, P_DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + P_TABLE_NAME + " (" +
                    P_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    P_COLUMN_PROGRESS + " INTEGER);";
            db.execSQL(query);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + P_TABLE_NAME);
            onCreate(db);
        }
    }
}


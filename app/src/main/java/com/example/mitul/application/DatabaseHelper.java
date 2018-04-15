package com.example.mitul.application;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "Application.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "user";
    public static final String COLUMN1 = "id";
    public static final String COLUMN2 = "name";
    public static final String COLUMN3 = "email";
    public static final String COLUMN4 = "password";

    private SQLiteDatabase sqLiteDatabase;

    private static final String TABLE_CREATE = "create table user (id integer primary key not null , " +
        "name text not null, email text not null, password text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //simple constructor
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.sqLiteDatabase = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String TABLE_DROP = "DROP TABLE IF EXISTS "+TABLE_NAME;
        sqLiteDatabase.execSQL(TABLE_DROP);
        this.onCreate(sqLiteDatabase);
    }

    public void insertContact(UserInfo userInfo){

        sqLiteDatabase = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        String query = "select * from user";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        int count = cursor.getCount();

        contentValues.put(COLUMN1, count);
        contentValues.put(COLUMN2, userInfo.get_name());
        contentValues.put(COLUMN3, userInfo.get_email());
        contentValues.put(COLUMN4, userInfo.get_password());

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
    }

    public String searchPassword(String email){
        sqLiteDatabase = this.getReadableDatabase();
        String FETCH_DATA = "select email,password from "+ TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(FETCH_DATA, null);

        String _email_, _password_;
        _password_ = "Not Found";
        if(cursor.moveToFirst()){
            do {
                _email_ = cursor.getString(0);

                if (_email_.equals(email)){
                    _password_ = cursor.getString(1);
                    break;
                }
            }while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return _password_;
    }
}

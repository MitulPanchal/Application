package com.example.mitul.application;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "mydb.db";
    private static final int DB_VERSION = 3;
    String DB_PATH = null;
    private final Context context;
    private SQLiteDatabase sqLiteDatabase;
    public static final String sourceStation = "source_station";

    public DataHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
        this.DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
        Log.e("Path",DB_PATH);
        try {
            createDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createDatabase() throws IOException{
        boolean dbExist = checkDatabase();

        if(dbExist){
            copyDatabase();
            Log.d("TAG", "Database Already exist");
            //openDatabase();
        }
        else{
            this.getReadableDatabase();
            try{
                Log.d("TAG","Database Copy Successfully");
                copyDatabase();
            }
            catch (IOException e){
                throw new Error("Error copying Database");
            }
        }
    }

    private boolean checkDatabase(){
        SQLiteDatabase checkDB = null;
        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath,null, SQLiteDatabase.OPEN_READWRITE);
        }
        catch (SQLException e){

        }
        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null;
    }

    private void copyDatabase() throws IOException{

        InputStream inputStream = context.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream outputStream = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;

        while((length = inputStream.read(buffer))> 0){
            outputStream.write(buffer, 0 ,length);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    public void openDatabase(){
        String myPATH = DB_PATH + DB_NAME;
        sqLiteDatabase = SQLiteDatabase.openDatabase(myPATH, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public List<StationInfo> getAllStation(){
        List<StationInfo> temp = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try {
            c = db.rawQuery("select station_id,station_name from station",null);
            if(c == null) return null;

            c.moveToFirst();
            do{
                StationInfo stationInfo = new StationInfo(c.getInt(c.getColumnIndex("station_id")),c.getString(c.getColumnIndex("station_name")));
                temp.add(stationInfo);
            }while (c.moveToNext());
            c.close();
        }
        catch (Exception e){

        }
        db.close();
        return temp;
    }

    public List<BusInfo> getAllBus(){
        List<BusInfo> temp = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try {
            c = db.rawQuery("select bus_no,bus_name from bus",null);
            if(c == null) return null;

            c.moveToFirst();
            do{
                BusInfo busInfo = new BusInfo(c.getInt(c.getColumnIndex("bus_no")),c.getString(c.getColumnIndex("bus_name")));
                temp.add(busInfo);
                Log.d("Database","Bus data");
            }while (c.moveToNext());
            c.close();
        }
        catch (Exception e){

        }
        db.close();
        return temp;
    }

    public String sourceStation(String _sourceStation_){
        sqLiteDatabase = this.getReadableDatabase();
        String query = "select station_name from station"; // Alternate query = "select source_station from route"
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        String _sourceStation = null;
        if(cursor.moveToFirst()){
            do{
                _sourceStation = cursor.getString(0);
                if(_sourceStation.equals(_sourceStation_)){
                    break;
                }
            }while(cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return _sourceStation;
    }

    public String destinationStation(String _destinationStation_){
        sqLiteDatabase = this.getReadableDatabase();
        String query = "select station_name from station"; // Alternate query = "select destination_station from route"
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        String _destinationStation = null;
        if(cursor.moveToFirst()){
            do{
                _destinationStation = cursor.getString(0);
                if(_destinationStation.equals(_destinationStation_)){
                    break;
                }
            }while(cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return _destinationStation;
    }

    public synchronized void close() {
        if(sqLiteDatabase != null)
            sqLiteDatabase.close();
        super.close();

    }

    public double longitudeStation(String _StationName_){
        sqLiteDatabase = this.getReadableDatabase();
        String query = "select station_name,longitude from station";
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        double _longitudeStation = 0;
        String _StationName;
        if(cursor.moveToFirst()){
            do{
                _StationName = cursor.getString(0);
                if(_StationName.equals(_StationName_)){
                    _longitudeStation = cursor.getDouble(1);
                    break;
                }
            }while(cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return _longitudeStation;
    }

    public double latitudeStation(String _StationName_){
        sqLiteDatabase = this.getReadableDatabase();
        String query = "select station_name,latitude from station";
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        double _latitudeStation = 0;
        String _StationName;
        if(cursor.moveToFirst()){
            do{
                _StationName = cursor.getString(0);
                if(_StationName.equals(_StationName_)){
                    _latitudeStation = cursor.getDouble(1);
                    break;
                }
            }while(cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return _latitudeStation;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion >= oldVersion){
            try{
                copyDatabase();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}

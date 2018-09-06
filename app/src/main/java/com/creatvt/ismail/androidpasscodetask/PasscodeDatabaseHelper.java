package com.creatvt.ismail.androidpasscodetask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class PasscodeDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "passcode_db.sqlite";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "passcode";
    private static final String PASSCODE_NAME = "passcode_name";
    private static final String PASSCODE_TYPE = "passcode_type";
    private static final String PASSCODE = "passcode";
    private static final String START_TIME = "start_time";
    private static final String END_TIME = "end_time";
    private static final String PASSCODE_DAY = "passcode_day";
    public PasscodeDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "create table " + TABLE_NAME +
                                                "(" +
                                                    PASSCODE_NAME + " text primary key, " +
                                                    PASSCODE_TYPE + " text, " +
                                                    START_TIME + " timestamp, " +
                                                    END_TIME + " timestamp, " +
                                                    PASSCODE + " text, " +
                                                    PASSCODE_DAY + " text " +
                                                ")";

        db.execSQL(createTableQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addPasscode(Passcode passcode){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(PASSCODE_NAME,passcode.getPasscodeName());
        contentValues.put(PASSCODE_TYPE,passcode.getPasscodeType());
        contentValues.put(START_TIME,passcode.getStartTime());
        contentValues.put(END_TIME,passcode.getEndTime());
        contentValues.put(PASSCODE,passcode.getPasscode());
        contentValues.put(PASSCODE_DAY,passcode.getPasscodeDay());

        try {

            long noOfRows = db.insert(TABLE_NAME, null, contentValues);

            db.close();
            return noOfRows > 0;
        }
        catch (Exception exception){
            return false;
        }
    }

    public boolean deletePasscode(String name){
        SQLiteDatabase database = getWritableDatabase();

        int deletedRow = database.delete(TABLE_NAME, PASSCODE_NAME + " like '" + name + "'",  null);

        if (deletedRow > 0) {
            return true;
        }
        return false;
    }

    public List<Passcode> getPasscodes(){
        List<Passcode> passcodes = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String query = "select * from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);

        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(PASSCODE_NAME));
            String type = cursor.getString(cursor.getColumnIndex(PASSCODE_TYPE));
            String day = cursor.getString(cursor.getColumnIndex(PASSCODE_DAY));
            long start = cursor.getLong(cursor.getColumnIndex(START_TIME));
            long end = cursor.getLong(cursor.getColumnIndex(END_TIME));
            String passcode = cursor.getString(cursor.getColumnIndex(PASSCODE));

            Passcode passcodeData = new Passcode(name,type,start,end,day,passcode);
            passcodes.add(passcodeData);

        }

        cursor.close();
        db.close();
        return passcodes;

    }

}

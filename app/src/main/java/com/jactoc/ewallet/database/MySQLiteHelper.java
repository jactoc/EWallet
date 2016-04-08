package com.jactoc.ewallet.database;

import android.content.Context;
import android.util.Log;
import com.jactoc.ewallet.EWalletApp;
import com.jactoc.ewallet.sharedPreferences.BasePreferences;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {

    //TAG
    private static final String TAG = "MySQLiteHelper";

    private static final String DATABASE_NAME = "ewallet.db";
    private static final int DATABASE_VERSION = 1;

    //Device table values
    public static final String TABLE_CARD = "CARD";
    public static final String PK = "ZPK";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_NUMBER = "NUMBER";
    public static final String COLUMN_EXPIRATION_DATE = "EXPDATE";
    public static final String COLUMN_CCV = "CCV";
    public static final String COLUMN_STATUS = "STATUS";
    public static final String COLUMN_PICTURE = "PICTURE";
    public static final String COLUMN_COLOR = "COLOR";


    // Account Database creation sql statement
    private static final String TABLE_CARD_CREATION = "CREATE TABLE IF NOT EXISTS "
            + TABLE_CARD + " ( " + PK + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME +  " VARCHAR, " + COLUMN_NUMBER +  " VARCHAR, "
            + COLUMN_EXPIRATION_DATE + " INTEGER, " + COLUMN_CCV + " INTEGER, " + COLUMN_STATUS + " INTEGER, "
            + COLUMN_PICTURE + " VARCHAR, " + COLUMN_COLOR + " VARCHAR ); ";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(TABLE_CARD_CREATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "Updating database.");
        db.execSQL("drop table if exists " + TABLE_CARD_CREATION);
        onCreate(db);
        new BasePreferences(EWalletApp.getAppContext()).setIsDatabaseVersionUp(true);
    }

} //end
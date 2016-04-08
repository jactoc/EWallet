/**
 * Dive Communications Inc.
 * @author  Gafar Aminou
 * @since   2014
 */
package com.jactoc.ewallet.database;
import net.sqlcipher.database.SQLiteDatabase;

public class DatabaseManager {

    private int mOpenCounter;

    private static DatabaseManager instance;
    private static MySQLiteHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;

    public static synchronized void initializeInstance(MySQLiteHelper helper) {
        if (instance == null) {
            instance = new DatabaseManager();
            mDatabaseHelper = helper;
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException(DatabaseManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }

        return instance;
    }

    public synchronized SQLiteDatabase openDatabase(String password) {
        mOpenCounter++;
        if(mOpenCounter == 1) {
            // Opening new database
            mDatabase = mDatabaseHelper.getWritableDatabase(password);      //use SQLite Encrypt Tools to decrypte
        }
        return mDatabase;
    }

    public synchronized void closeDatabase() {
        mOpenCounter--;
        if(mOpenCounter == 0) {
            // Closing database
            mDatabase.close();

        }
    }

} //end
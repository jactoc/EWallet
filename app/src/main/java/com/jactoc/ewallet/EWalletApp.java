package com.jactoc.ewallet;

import android.app.Application;
import android.content.Context;

import com.jactoc.ewallet.database.QuerySource;
import com.jactoc.ewallet.sharedPreferences.BasePreferences;

import net.sqlcipher.database.SQLiteDatabase;

/**
 * Created by jactoc on 2016-03-19.
 */
public class EWalletApp extends Application {

    private static Context context;
    private static QuerySource dataSource;

    public void onCreate(){
        super.onCreate();

        //context
        context = getApplicationContext();

        //SQL
        SQLiteDatabase.loadLibs(getApplicationContext());
        dataSource = new QuerySource(getAppContext());
        if(new BasePreferences(getAppContext()).getLoggedStatus() && new BasePreferences(getAppContext()).getPassword() != null)
            dataSource.open(new BasePreferences(getAppContext()).getPassword());
    }

    public static Context getAppContext() {
        return EWalletApp.context;
    }
    public static QuerySource getDataSource() {
        return dataSource;
    }

} //end
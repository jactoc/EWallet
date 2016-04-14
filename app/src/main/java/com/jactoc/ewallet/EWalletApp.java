package com.jactoc.ewallet;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import com.jactoc.ewallet.database.QuerySource;
import com.jactoc.ewallet.sharedPreferences.BasePreferences;
import com.jactoc.ewallet.util.Util;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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
        if(new BasePreferences(getAppContext()).getLoggedStatus() && new BasePreferences(getAppContext()).getPassword() != null) {
            if(Util.isAndroidM()) {
                checkRuntimePermissions();
            } else {
                dataSource.open(new BasePreferences(getAppContext()).getPassword());
            }
        }
    }

    public static Context getAppContext() {
        return EWalletApp.context;
    }
    public static QuerySource getDataSource() {
        return dataSource;
    }


    private void checkRuntimePermissions() {
        List<String> permissionsList = new ArrayList<>();
        addPermission(permissionsList, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(permissionsList.size() == 0) {
            dataSource.open(new BasePreferences(getAppContext()).getPassword());
        }
    }
    @TargetApi(Build.VERSION_CODES.M)
    private void addPermission(List<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
        }
    }

} //end
package com.jactoc.ewallet.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import com.jactoc.ewallet.constant.Constant;

/**
 * Created by jactoc on 2016-03-19.
 */
public class BasePreferences {

    private SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public BasePreferences(Context context) {
        preferences = context.getSharedPreferences(Constant.MY_PREFS, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void removeAll(){
        editor.remove(Constant.LOGGED);
        editor.remove(Constant.IS_DATABASE_VERSION_UPDATED);
        editor.remove(Constant.PASSWORD);
        editor.remove(Constant.REMEMBER_PASSWORD);
        editor.commit();
    }

    //USER IS LOGGED
    public boolean getLoggedStatus() {
        return preferences.getBoolean(Constant.LOGGED, false);
    }
    public void setLogged(boolean isLogged) {
        editor.putBoolean(Constant.LOGGED, isLogged);
        editor.commit();
    }
    //DATABASE
    public boolean getIsDatabaseVersionUp() {
        return preferences.getBoolean(Constant.IS_DATABASE_VERSION_UPDATED, false);
    }
    public void setIsDatabaseVersionUp(boolean isDatabaseVersionUp) {
        editor.putBoolean(Constant.IS_DATABASE_VERSION_UPDATED, isDatabaseVersionUp);
        editor.commit();
    }

    //PASSWORD
    public String getPassword() {
        return preferences.getString(Constant.PASSWORD, null);
    }
    public void setPassword(String password) {
        editor.putString(Constant.PASSWORD, password);
        editor.commit();
    }
    public boolean getIsRememberPassword() {
        return preferences.getBoolean(Constant.REMEMBER_PASSWORD, false);
    }
    public void setIsRememberPassword(boolean isRememberPassword) {
        editor.putBoolean(Constant.REMEMBER_PASSWORD, isRememberPassword);
        editor.commit();
    }

} //end

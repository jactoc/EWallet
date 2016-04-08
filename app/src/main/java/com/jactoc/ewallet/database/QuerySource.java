package com.jactoc.ewallet.database;

import android.content.ContentValues;
import android.content.Context;
import com.jactoc.ewallet.EWalletApp;
import com.jactoc.ewallet.beans.CreditCard;
import com.jactoc.ewallet.sharedPreferences.BasePreferences;
import com.jactoc.ewallet.util.DoneCallback;
import net.sqlcipher.Cursor;
import net.sqlcipher.SQLException;
import net.sqlcipher.database.SQLiteDatabase;
import java.util.ArrayList;

/**
 * Created by jactoc on 2016-03-19.
 */
public class QuerySource {

    // Database fields
    private SQLiteDatabase database;
    private static String TAG = "DiveDataSource";

    private static String GET_ALL_CARDS =
            "SELECT " + MySQLiteHelper.PK + " , " + MySQLiteHelper.COLUMN_NAME + " , " +  MySQLiteHelper.COLUMN_NUMBER + " , "
                    + MySQLiteHelper.COLUMN_EXPIRATION_DATE + " , " + MySQLiteHelper.COLUMN_CCV + " , " +  MySQLiteHelper.COLUMN_STATUS + " , "
                    + MySQLiteHelper.COLUMN_PICTURE + " , " + MySQLiteHelper.COLUMN_COLOR
                    + " FROM " + MySQLiteHelper.TABLE_CARD;

    private static String DELETE_CARD =
            "DELETE FROM " + MySQLiteHelper.TABLE_CARD
                    + " WHERE " + MySQLiteHelper.COLUMN_NUMBER + "=";

    private static String DELETE_ALL =
            "DELETE FROM " + MySQLiteHelper.TABLE_CARD;

    public QuerySource(Context context) {
        DatabaseManager.initializeInstance(new MySQLiteHelper(context));
    }

    public void open(String password) throws SQLException {
        android.util.Log.v(TAG, "opening DB");
        database = DatabaseManager.getInstance().openDatabase(password);
    }

    public void close() {
        android.util.Log.v(TAG, "closing DB");
        DatabaseManager.getInstance().closeDatabase();
    }


    public void deleteCreditCard(CreditCard card) {
        android.util.Log.v(TAG, "deleteCreditCard");

        open(new BasePreferences(EWalletApp.getAppContext()).getPassword());
        String delete = DELETE_CARD + card.getNumber() + ";";
        database.execSQL(delete);
        close();
    }

    public void deleteAll() {
        android.util.Log.v(TAG, "deleteAll");

        open(new BasePreferences(EWalletApp.getAppContext()).getPassword());
        String delete = DELETE_ALL;
        database.execSQL(delete);
        close();
    }

    public void insertCreditCard(CreditCard card) {
        ContentValues values = new ContentValues();

        values.put(MySQLiteHelper.COLUMN_NUMBER, card.getNumber());
        values.put(MySQLiteHelper.COLUMN_NAME, card.getName());
        values.put(MySQLiteHelper.COLUMN_CCV, card.getCcv());
        values.put(MySQLiteHelper.COLUMN_COLOR, card.getColor());
        values.put(MySQLiteHelper.COLUMN_EXPIRATION_DATE, card.getExpiration_date());
        values.put(MySQLiteHelper.COLUMN_PICTURE, card.getPicture());
        values.put(MySQLiteHelper.COLUMN_STATUS, card.getStatus());
        database.insert(MySQLiteHelper.TABLE_CARD, null, values);
    }

    public void getAllCreditCards(String password, DoneCallback doneCallback) {
        android.util.Log.v(TAG, "getAllCreditCards");

        ArrayList<CreditCard> cards = new ArrayList<>();

        open(password);

        Cursor cursor = database.rawQuery(GET_ALL_CARDS, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            CreditCard card = cursorToCard(cursor);
            cards.add(card);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();

        close();

        //return
        if(doneCallback != null)
            doneCallback.done(cards);
    }

    private CreditCard cursorToCard(Cursor cursor) {
        CreditCard card = new CreditCard();
        card.setName(String.valueOf(cursor.getString(1)));
        card.setNumber(cursor.getString(2));
        card.setExpiration_date(cursor.getInt(3));
        card.setCcv(cursor.getInt(4));
        card.setStatus(cursor.getInt(5));
        card.setPicture(cursor.getString(6));
        card.setColor(cursor.getString(7));
        return card;
    }


} //end
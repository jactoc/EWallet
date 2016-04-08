package com.jactoc.ewallet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.jactoc.ewallet.sharedPreferences.BasePreferences;
import com.jactoc.ewallet.util.AnimCheckBox;

public class SettingsActivity extends AppCompatActivity {

    //context
    private Context context = this;

    //UI
    private AnimCheckBox checkboxSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Settings");

        checkboxSettings = (AnimCheckBox) findViewById(R.id.checkboxSettings);
        checkboxSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkboxSettings.isChecked()){
                    alertDialogPromptPassword(false);
                    checkboxSettings.setChecked(false);
                } else {
                    alertDialogPromptPassword(true);
                    checkboxSettings.setChecked(true);
                }
            }
        });
    }

    private void alertDialogPromptPassword(final boolean flag) {
        final EditText edittext = new EditText(this);

        new AlertDialog.Builder(this)
                .setCancelable(true)
                .setMessage("Enter the password")
                .setView(edittext)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int whichButton) {
                        if (new BasePreferences(context).getPassword().equals(edittext.getText().toString())) {
                            new BasePreferences(context).setIsRememberPassword(flag);
                            dialog.cancel();
                        } else {
                            edittext.setText("");
                            Toast.makeText(EWalletApp.getAppContext(), "Wrong Password. Try again.", Toast.LENGTH_SHORT).show();
                            alertDialogPromptPassword(flag);
                        }
                    }
                }).show();
    }


    public void DELETEALL(View view) {
        new AlertDialog.Builder(this)
                .setCancelable(true)
                .setMessage("Are you sure?")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int whichButton) {
                        new BasePreferences(context).removeAll();
                        EWalletApp.getDataSource().deleteAll();
                        Intent intent = new Intent(context, SplashActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

} //end
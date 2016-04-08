package com.jactoc.ewallet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.jactoc.ewallet.sharedPreferences.BasePreferences;
import com.jactoc.ewallet.util.AnimCheckBox;
import com.jactoc.ewallet.util.Util;

public class TutorialActivity extends AppCompatActivity {

    //context
    private Context context = this;

    //UI
    private EditText enterPassword;
    private Button confirm;
    private AnimCheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        setTitle("Welcome!");

        linkToUI();
    }

    private void linkToUI() {
        enterPassword = (EditText) findViewById(R.id.enterPassword);
        confirm = (Button) findViewById(R.id.CONFIRM);
        checkBox = (AnimCheckBox) findViewById(R.id.checkboxTutorial);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    checkBox.setChecked(false);
                } else {
                    checkBox.setChecked(true);
                }
            }
        });
    }


    public void CONFIRM(View view) {
        confirm.setEnabled(false);

        //delete spaces
        String password = enterPassword.getText().toString().replaceAll("\\s+","");

        if(Util.isOnlyCharAndNumbers(password)) {
            new BasePreferences(context).setPassword(password);
            if(checkBox.isChecked())
                new BasePreferences(context).setIsRememberPassword(true);
            else
                new BasePreferences(context).setIsRememberPassword(false);

            new BasePreferences(context).setLogged(true);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            new AlertDialog.Builder(this)
                    .setMessage("The Password chosen contains one or more symbols!")
                    .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            confirm.setEnabled(true);
                        }
                    }).show();
        }
    }

} //end
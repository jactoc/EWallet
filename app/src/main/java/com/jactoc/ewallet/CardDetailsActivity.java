package com.jactoc.ewallet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.jactoc.ewallet.database.QuerySource;
import io.card.payment.CreditCard;

public class CardDetailsActivity extends AppCompatActivity {

    private CreditCard result;
    private com.jactoc.ewallet.beans.CreditCard toInsertInDB = new com.jactoc.ewallet.beans.CreditCard();
    private QuerySource querySource;

    //UI
    private EditText name;
    private ImageView logo;
    private TextView number, exp_date, exp_date_text;
    private RelativeLayout cardLayout;
    private String[] icons;
    private String[] color;
    SeekBar seekBar;
    private boolean flagCredit = false;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);
        setTitle("Add new card");

        querySource = EWalletApp.getDataSource();

        icons = this.getResources().getStringArray(R.array.logo_icons);
        color = this.getResources().getStringArray(R.array.logo_colors);

        if (getIntent().getExtras() != null) {
            result = getIntent().getExtras().getParcelable("scanResult");
            flagCredit = true;
        }

        linkToUI();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void linkToUI() {
        cardLayout = (RelativeLayout) findViewById(R.id.cardLayout);
        name = (EditText) findViewById(R.id.name);
        name.setHint("Click here to insert a name");
        logo = (ImageView) findViewById(R.id.logo);
        number = (TextView) findViewById(R.id.number);
        exp_date = (TextView) findViewById(R.id.exp_date);
        exp_date_text = (TextView) findViewById(R.id.textViewExp);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBarSetup();

        //default
        toInsertInDB.setColor("white");

        //colors
        ImageView red = (ImageView) findViewById(R.id.red);
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardLayout.invalidate();
                cardLayout.setBackground(getResources().getDrawable(R.drawable.round_layout_red));
                toInsertInDB.setColor("red");
                changeToWhite(false);
            }
        });
        ImageView green = (ImageView) findViewById(R.id.green);
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardLayout.invalidate();
                cardLayout.setBackground(getResources().getDrawable(R.drawable.round_layout_green));
                toInsertInDB.setColor("green");
                changeToWhite(false);
            }
        });
        ImageView yellow = (ImageView) findViewById(R.id.yellow);
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardLayout.invalidate();
                cardLayout.setBackground(getResources().getDrawable(R.drawable.round_layout_yellow));
                toInsertInDB.setColor("yellow");
                changeToWhite(false);
            }
        });
        ImageView black = (ImageView) findViewById(R.id.black);
        black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardLayout.invalidate();
                cardLayout.setBackground(getResources().getDrawable(R.drawable.round_layout_black));
                toInsertInDB.setColor("black");
                changeToWhite(true);
            }
        });
        ImageView white = (ImageView) findViewById(R.id.white);
        white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardLayout.invalidate();
                cardLayout.setBackground(getResources().getDrawable(R.drawable.round_layout_white));
                toInsertInDB.setColor("white");
                changeToWhite(false);
            }
        });
        ImageView blue = (ImageView) findViewById(R.id.blue);
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardLayout.invalidate();
                cardLayout.setBackground(getResources().getDrawable(R.drawable.round_layout_blue));
                toInsertInDB.setColor("blue");
                changeToWhite(true);
            }
        });
        ImageView orange = (ImageView) findViewById(R.id.orange);
        orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardLayout.invalidate();
                cardLayout.setBackground(getResources().getDrawable(R.drawable.round_layout_orange));
                toInsertInDB.setColor("orange");
                changeToWhite(false);
            }
        });
        ImageView purple = (ImageView) findViewById(R.id.purple);
        purple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardLayout.invalidate();
                cardLayout.setBackground(getResources().getDrawable(R.drawable.round_layout_purple));
                toInsertInDB.setColor("purple");
                changeToWhite(true);
            }
        });

        if (flagCredit) {
            number.setText(result.getFormattedCardNumber());

            String exp = result.expiryMonth + "/" + result.expiryYear;
            String expOnlyDigits = result.expiryMonth + "" + result.expiryYear;
            exp_date.setText(exp);

            toInsertInDB.setNumber(result.cardNumber);
            toInsertInDB.setExpiration_date(Integer.valueOf(expOnlyDigits));
        } else {
            exp_date.setVisibility(View.INVISIBLE);
            exp_date_text.setVisibility(View.INVISIBLE);
            number.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogPromptNumber();
                }
            });
        }

        Spinner spinner = (Spinner) findViewById(R.id.spinnerLogo);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.logo_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    int idDraw = getResources().getIdentifier(icons[position], "drawable", getPackageName());
                    Drawable drawable = getResources().getDrawable(idDraw);
                    logo.setImageDrawable(drawable);

                    cardLayout.setBackgroundColor(Color.parseColor(color[position]));

                    toInsertInDB.setPicture(icons[position]);
                    toInsertInDB.setColor(color[position]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
    }

    private void changeToWhite(boolean flag) {
        if (flag) {
            name.setTextColor(Color.WHITE);
            exp_date.setTextColor(Color.WHITE);
            number.setTextColor(Color.WHITE);
            exp_date_text.setTextColor(Color.WHITE);
            name.setHintTextColor(Color.WHITE);
        } else {
            name.setTextColor(Color.BLACK);
            exp_date.setTextColor(Color.BLACK);
            number.setTextColor(Color.BLACK);
            exp_date_text.setTextColor(Color.BLACK);
            name.setHintTextColor(Color.BLACK);
        }
    }

    private void alertDialogPromptNumber() {
        final EditText edittext = new EditText(this);

        new AlertDialog.Builder(this)
                .setMessage("Enter Card Number")
                .setView(edittext)
                .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        number.setText(edittext.getText().toString());
                        exp_date.setText("");
                    }
                }).show();
    }

    public void CANCEL(View v) {
        new AlertDialog.Builder(this)
                .setTitle("Are you sure?")
                .setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        finish();
                    }
                })
                .setNegativeButton("DISMISS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).show();
    }

    public void CONFIRM(View v) {
        toInsertInDB.setName(name.getText().toString());
        toInsertInDB.setNumber(number.getText().toString().replaceAll("[^0-9]", ""));
        toInsertInDB.setCcv(0);
        if (exp_date.getText().equals(""))
            toInsertInDB.setExpiration_date(0);

        querySource.insertCreditCard(toInsertInDB);
        Toast.makeText(this, "Card successfully saved!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onBackPressed() {
        CANCEL(null);
    }

    private void seekBarSetup() {
        final int step = 25;
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = (Math.round(progress / step)) * step;
                seekBar.setProgress(progress);
                switch (toInsertInDB.getColor()) {
                    case "blue":
                        if(progress < 25) {
                            cardLayout.invalidate();
                            cardLayout.setBackground(getResources().getDrawable(R.drawable.round_layout_blue0));
                            toInsertInDB.setColor("blue0");
                        } else if(progress >= 25 && progress < 50) {
                            cardLayout.invalidate();
                            cardLayout.setBackground(getResources().getDrawable(R.drawable.round_layout_blue25));
                            toInsertInDB.setColor("blue25");
                        } else if(progress >= 50 && progress < 75) {
                            cardLayout.invalidate();
                            cardLayout.setBackground(getResources().getDrawable(R.drawable.round_layout_blue));
                            toInsertInDB.setColor("blue");
                        } else if(progress >= 75 && progress < 100){
                            cardLayout.invalidate();
                            cardLayout.setBackground(getResources().getDrawable(R.drawable.round_layout_blue75));
                            toInsertInDB.setColor("blue75");
                        } else {
                            cardLayout.invalidate();
                            cardLayout.setBackground(getResources().getDrawable(R.drawable.round_layout_blue100));
                            toInsertInDB.setColor("blue100");
                        }
                        break;
                    case "green":

                        break;
                    case "yellow":

                        break;
                    case "red":

                        break;
                    case "purple":

                        break;
                    case "orange":

                        break;
                    case "white":

                        break;
                    case "black":

                        break;
                }
            }

                @Override
                public void onStartTrackingTouch (SeekBar seekBar){

                }

                @Override
                public void onStopTrackingTouch (SeekBar seekBar){

                }
            }

            );
        }

    } //end

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "CardDetails Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.jactoc.ewallet/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "CardDetails Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.jactoc.ewallet/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
package com.jactoc.ewallet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.jactoc.ewallet.adapter.CardArrayAdapter;
import com.jactoc.ewallet.database.QuerySource;
import com.jactoc.ewallet.sharedPreferences.BasePreferences;
import com.jactoc.ewallet.util.DoneCallback;
import java.util.ArrayList;
import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

public class MainActivity extends AppCompatActivity {

    private static final int MY_SCAN_REQUEST_CODE = 100;
    private ArrayList<com.jactoc.ewallet.beans.CreditCard> cards = new ArrayList<>();
    private CardArrayAdapter adapter;
    private QuerySource querySource;
    private BasePreferences basePreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("My wallet");

        //shared pref
        basePreferences = new BasePreferences(this);

        //database
        querySource = EWalletApp.getDataSource();

        //adapter
        adapter = new CardArrayAdapter(this, cards);
        ListView listview = (ListView) this.findViewById(R.id.listView);
        listview.setAdapter(adapter);

        if(!basePreferences.getIsRememberPassword()) {
            alertDialogPromptPassword();
            adapter.notifyDataSetChanged();
        }
    }

    private void alertDialogPromptPassword() {
        final EditText edittext = new EditText(this);

        new AlertDialog.Builder(this)
            .setCancelable(false)
            .setMessage("Enter the password")
            .setView(edittext)
            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(final DialogInterface dialog, int whichButton) {
                    if (basePreferences.getPassword().equals(edittext.getText().toString())) {
                        final DoneCallback<ArrayList<Object>> doneCallback = new DoneCallback<ArrayList<Object>>() {
                            @Override
                            public void done(ArrayList<Object> result) {
                                cards.clear();
                                for (Object object : result) {
                                    if(object instanceof com.jactoc.ewallet.beans.CreditCard)
                                        cards.add((com.jactoc.ewallet.beans.CreditCard) object);
                                }
                                adapter.notifyDataSetChanged();
                            }

                        };
                        querySource.getAllCreditCards(basePreferences.getPassword(), doneCallback);
                        dialog.cancel();
                    } else {
                        edittext.setText("");
                        Toast.makeText(EWalletApp.getAppContext(), "Wrong Password. Try again.", Toast.LENGTH_SHORT).show();
                        alertDialogPromptPassword();
                    }
                }
            }).show();
    }

    @Override
    public void onResume() {
        update();
        super.onResume();
    }

    private void update() {
        //notify
        final DoneCallback<ArrayList<Object>> doneCallback = new DoneCallback<ArrayList<Object>>() {
            @Override
            public void done(ArrayList<Object> result) {
                cards.clear();
                for (Object object : result) {
                    if(object instanceof com.jactoc.ewallet.beans.CreditCard)
                        cards.add((com.jactoc.ewallet.beans.CreditCard) object);
                }
                adapter.notifyDataSetChanged();
            }

        };
        querySource.getAllCreditCards(basePreferences.getPassword(), doneCallback);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onPrepareOptionsMenu(Menu menu) {
        //  preparation code here
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add) {
            onScanPress();
        } else if(item.getItemId() == R.id.add2) {
            addOtherCard();
        }
        else {
            startActivity(new Intent(this, SettingsActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onScanPress() {
        Intent scanIntent = new Intent(this, CardIOActivity.class);

        // customize these values to suit your needs.
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false

        // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
        startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);
    }

    public void addOtherCard() {
        Intent intent = new Intent(this, CardDetailsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_SCAN_REQUEST_CODE) {
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

                Intent intent = new Intent(this, CardDetailsActivity.class);
                intent.putExtra("scanResult", scanResult);
                startActivity(intent);
            }
        }
        // else handle other activity results
    }


} //end
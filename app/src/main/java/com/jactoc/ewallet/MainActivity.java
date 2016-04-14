package com.jactoc.ewallet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.jactoc.ewallet.adapter.CardArrayAdapter;
import com.jactoc.ewallet.database.QuerySource;
import com.jactoc.ewallet.sharedPreferences.BasePreferences;
import com.jactoc.ewallet.util.DoneCallback;
import com.jactoc.ewallet.util.RoundedImageView;
import com.jactoc.ewallet.util.Util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

public class MainActivity extends AppCompatActivity {

    //context
    private Context context = this;

    //codes
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_LIBRARY = 2;
    private static final int MY_SCAN_REQUEST_CODE = 100;

    //data
    private ArrayList<com.jactoc.ewallet.beans.CreditCard> cards = new ArrayList<>();
    private CardArrayAdapter adapter;
    private QuerySource querySource;
    private BasePreferences basePreferences;
    private File imageFile;
    private static Bitmap profileBitmap;
    private static Bitmap bmCircle;
    private String tempPath;

    //UI
    private static ImageView profilePicture;
    private TextView name, address, issued, expires, number;
    private EditText editName, editAddress, editIssued, editExpires, editNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("My wallet");

        //shared pref
        basePreferences = new BasePreferences(this);

        //database
        querySource = EWalletApp.getDataSource();

        //UI
        linkToUI();

        //adapter
        adapter = new CardArrayAdapter(this, cards);
        ListView listview = (ListView) this.findViewById(R.id.listView);
        listview.setAdapter(adapter);

        if(!basePreferences.getIsRememberPassword()) {
            alertDialogPromptPassword();
            adapter.notifyDataSetChanged();
        }
    }

    private void linkToUI() {
        profilePicture = (ImageView) findViewById(R.id.picture);

        name = (TextView) findViewById(R.id.name);
        address = (TextView) findViewById(R.id.address);
        issued = (TextView) findViewById(R.id.issued);
        expires = (TextView) findViewById(R.id.expires);
        number = (TextView) findViewById(R.id.number);

        editName = (EditText) findViewById(R.id.editName);
        editAddress = (EditText) findViewById(R.id.editAddress);
        editIssued = (EditText) findViewById(R.id.editIssued);
        editExpires = (EditText) findViewById(R.id.editExpires);
        editNumber = (EditText) findViewById(R.id.editNumber);
    }

    private void enableFunctions() {
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillField(0);
            }
        });
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillField(1);
            }
        });
        issued.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillField(2);
            }
        });
        expires.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillField(3);
            }
        });
        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillField(4);
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            enableFunctions();
        }
    }

    private void fillField(int selection) {
        switch (selection) {
            case 0:

                break;
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
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
        } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
            tempPath = imageFile.getPath();

            //load bitmap
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            profileBitmap = BitmapFactory.decodeFile(tempPath, bitmapOptions);

            //Rotate image. it seems some device such samsung only put a exif tag orientation but not actually rotate image.
            try {
                ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL);
                int rotate = 0;
                switch(orientation){
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotate = 270;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotate = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotate = 90;
                        break;
                }

                if(rotate!=0){
                    int profileBitmapWidth= profileBitmap.getWidth();
                    int profileBitmapHeight = profileBitmap.getHeight();

                    Matrix matrix = new Matrix();
                    matrix.preRotate(rotate);
                    profileBitmap = Bitmap.createBitmap(profileBitmap, 0, 0, profileBitmapWidth, profileBitmapHeight, matrix, true);
                }
            }catch (IOException e){
                e.printStackTrace();
            }

            //manipulate
            profileBitmap = Util.createProfilePictureFromCamera(profileBitmap, 250);

        } else if (requestCode == REQUEST_IMAGE_LIBRARY) {
            Bundle extras = data.getExtras();
            if(extras != null){

                //create a file to write bitmap data
                File f = new File(context.getCacheDir(), "tempProfilePicture.png");
                try {
                    f.createNewFile();
                    //Convert bitmap to byte array
                    Bitmap bitmap = (Bitmap) extras.get("data");
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);

                    byte[] bitmapdata = bos.toByteArray();

                    //write the bytes in file
                    FileOutputStream fos = new FileOutputStream(f);
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();
                } catch (IOException e){
                    e.printStackTrace();
                }

                tempPath = f.getPath();
                BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                profileBitmap = BitmapFactory.decodeFile(tempPath, bitmapOptions);
            }
        }
        if (profileBitmap != null) {
            setProfileImage(profileBitmap);
        }
    }

    private static void setProfileImage(Bitmap bitmap) {
        bmCircle = RoundedImageView.getRoundBitmap(bitmap, 150, "#FFFFFF");
        profilePicture.setImageBitmap(bmCircle);
    }

    public void takePhoto(View v){
        final CharSequence[] items = {"Take Photo", "Use Existing"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        imageFile = new File(Environment.getExternalStorageDirectory(), Util.getCurrentTime() + ".jpg");
                        Uri photoPath = Uri.fromFile(imageFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoPath);
                        takePictureIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                    }
                } else if (items[item].equals("Use Existing")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    // ******** code for crop image
                    intent.putExtra("crop", "true");
                    intent.putExtra("aspectX", 1);
                    intent.putExtra("aspectY", 1);
                    intent.putExtra("outputX", 250);
                    intent.putExtra("outputY", 250);
                    startActivityForResult(Intent.createChooser(intent, "Select File"), REQUEST_IMAGE_LIBRARY);
                }
            }
        })
                .show()
                .setCanceledOnTouchOutside(true);
    }


} //end
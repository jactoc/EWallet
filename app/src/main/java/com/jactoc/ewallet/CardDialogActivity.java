package com.jactoc.ewallet;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.jactoc.ewallet.beans.CreditCard;
import java.util.EnumMap;
import java.util.Map;

public class CardDialogActivity extends Activity {

    //context
    final private Context context = this;

    //card
    private CreditCard card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //no background
        getWindow().setBackgroundDrawable(new ColorDrawable(0));

        //get card details
        Bundle bundle = getIntent().getExtras();
        card = (CreditCard) bundle.getSerializable("card");

        //UI
        showDetails();
    }
    
    private void showDetails() {
        setContentView(R.layout.activity_card_dialog);

        RelativeLayout row_layout = (RelativeLayout) findViewById(R.id.cardLayout);
        TextView name = (TextView) findViewById(R.id.name);
        TextView number = (TextView) findViewById(R.id.number);
        TextView exp_date = (TextView) findViewById(R.id.exp_date);
        TextView exp_date_text = (TextView) findViewById(R.id.textViewExp);
        
        String color = card.getColor();
        if(color.equals("red")) {
            row_layout.setBackground(context.getDrawable(R.drawable.round_layout_red));
            name.setTextColor(Color.BLACK);
            number.setTextColor(Color.BLACK);
        } else if(color.equals("blue")) {
            row_layout.setBackground(context.getDrawable(R.drawable.round_layout_blue));
            name.setTextColor(Color.WHITE);
            number.setTextColor(Color.WHITE);
        } else if(color.equals("yellow")) {
            row_layout.setBackground(context.getDrawable(R.drawable.round_layout_yellow));
            name.setTextColor(Color.BLACK);
            number.setTextColor(Color.BLACK);
        } else if(color.equals("green")) {
            row_layout.setBackground(context.getDrawable(R.drawable.round_layout_green));
            name.setTextColor(Color.WHITE);
            number.setTextColor(Color.WHITE);
        } else if(color.equals("purple")) {
            row_layout.setBackground(context.getDrawable(R.drawable.round_layout_purple));
            name.setTextColor(Color.BLACK);
            number.setTextColor(Color.BLACK);
        } else if(color.equals("orange")) {
            row_layout.setBackground(context.getDrawable(R.drawable.round_layout_orange));
            name.setTextColor(Color.BLACK);
            number.setTextColor(Color.BLACK);
        } else if(color.equals("white")) {
            row_layout.setBackground(context.getDrawable(R.drawable.round_layout_white));
            name.setTextColor(Color.BLACK);
            number.setTextColor(Color.BLACK);
        } else if(color.equals("black")) {
            row_layout.setBackground(context.getDrawable(R.drawable.round_layout_black));
            name.setTextColor(Color.WHITE);
            number.setTextColor(Color.WHITE);
        }

        name.setText(card.getName());
        number.setText(card.getNumber());
        if(card.getExpiration_date() != 0) {
            exp_date.setText(card.getExpiration_date());
        } else {
            exp_date_text.setVisibility(View.INVISIBLE);
        }

        row_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBarcode();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void showBarcode() {

        setContentView(R.layout.activity_barcode_dialog);

        // barcode image
        Bitmap bitmap;
        ImageView barcode = (ImageView) findViewById(R.id.barcode);

        try {
            bitmap = encodeAsBitmap(card.getNumber(), BarcodeFormat.CODE_128, 600, 300);
            barcode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        //barcode text
        TextView number = (TextView) findViewById(R.id.number);
        number.setText(card.getNumber());

        if(card.getCcv() != 0) {
            TextView ccv = (TextView) findViewById(R.id.ccv);
            ccv.setText(card.getCcv());
        }

        RelativeLayout barcode_layout = (RelativeLayout) findViewById(R.id.barcodeLayout);
        barcode_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetails();
            }
        });
    }


    /**************************************************************
     * getting from com.google.zxing.client.android.encode.QRCodeEncoder
     *
     * See the sites below
     * http://code.google.com/p/zxing/
     */

    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;

    Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
        String contentsToEncode = contents;
        if (contentsToEncode == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contentsToEncode);
        if (encoding != null) {
            hints = new EnumMap<>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }

} //end
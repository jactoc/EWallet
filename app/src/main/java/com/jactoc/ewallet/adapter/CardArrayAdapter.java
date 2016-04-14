package com.jactoc.ewallet.adapter;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jactoc.ewallet.CardDialogActivity;
import com.jactoc.ewallet.EWalletApp;
import com.jactoc.ewallet.MainActivity;
import com.jactoc.ewallet.R;
import com.jactoc.ewallet.beans.CreditCard;

public class CardArrayAdapter extends ArrayAdapter<CreditCard> {

    //context
    private final Context context;

    //list
    private ArrayList<CreditCard> cards;
    LayoutInflater mInflater;

    public CardArrayAdapter(Context context, ArrayList<CreditCard> cards) {
        super(context, R.layout.row_card_layout, cards);
        this.context = context;
        this.cards = cards;
        this.mInflater = LayoutInflater.from(context);
    }

    private static class ViewHolder {
        public ImageView photo;
        public RelativeLayout textInfo;
        public TextView name;
        public TextView number;
        public RelativeLayout semi_circle;
        public RelativeLayout row_layout;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder             = new ViewHolder();
            convertView            = mInflater.inflate(R.layout.row_card_layout, null);
            viewHolder.row_layout  = (RelativeLayout) convertView.findViewById(R.id.row_layout);
            viewHolder.photo       = (ImageView) convertView.findViewById(R.id.row_image);
            viewHolder.textInfo    = (RelativeLayout) convertView.findViewById(R.id.textInfo);
            viewHolder.name        = (TextView) convertView.findViewById(R.id.row_name);
            viewHolder.number      = (TextView) convertView.findViewById(R.id.row_number);
            viewHolder.semi_circle = (RelativeLayout) convertView.findViewById(R.id.semi_circle);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //background
        viewHolder.row_layout.invalidate();
        String color = cards.get(position).getColor();
        if(color.contains("red")) {
            viewHolder.row_layout.setBackground(context.getDrawable(R.drawable.custom_shape_red));
            viewHolder.name.setTextColor(Color.BLACK);
            viewHolder.number.setTextColor(Color.BLACK);
        } else if(color.contains("blue")) {
            viewHolder.row_layout.setBackground(context.getDrawable(R.drawable.custom_shape_blue));
            viewHolder.name.setTextColor(Color.WHITE);
            viewHolder.number.setTextColor(Color.WHITE);
        } else if(color.contains("yellow")) {
            viewHolder.row_layout.setBackground(context.getDrawable(R.drawable.custom_shape_yellow));
            viewHolder.name.setTextColor(Color.BLACK);
            viewHolder.number.setTextColor(Color.BLACK);
        } else if(color.contains("green")) {
            viewHolder.row_layout.setBackground(context.getDrawable(R.drawable.custom_shape_green));
            viewHolder.name.setTextColor(Color.WHITE);
            viewHolder.number.setTextColor(Color.WHITE);
        } else if(color.contains("purple")) {
            viewHolder.row_layout.setBackground(context.getDrawable(R.drawable.custom_shape_purple));
            viewHolder.name.setTextColor(Color.BLACK);
            viewHolder.number.setTextColor(Color.BLACK);
        } else if(color.contains("orange")) {
            viewHolder.row_layout.setBackground(context.getDrawable(R.drawable.custom_shape_orange));
            viewHolder.name.setTextColor(Color.BLACK);
            viewHolder.number.setTextColor(Color.BLACK);
        } else if(color.contains("white")) {
            viewHolder.row_layout.setBackground(context.getDrawable(R.drawable.custom_shape_white));
            viewHolder.name.setTextColor(Color.BLACK);
            viewHolder.number.setTextColor(Color.BLACK);
        } else if(color.contains("black")) {
            viewHolder.row_layout.setBackground(context.getDrawable(R.drawable.custom_shape_black));
            viewHolder.name.setTextColor(Color.WHITE);
            viewHolder.number.setTextColor(Color.WHITE);
        } else {
            viewHolder.name.setTextColor(Color.WHITE);
            viewHolder.number.setTextColor(Color.WHITE);
            viewHolder.row_layout.setBackground(context.getDrawable(R.drawable.custom_shape));
        }

        //semi circle
//        if(position > 0) {
//            viewHolder.semi_circle.invalidate();
//            String colorPre = cards.get(position-1).getColor();
//            if(colorPre.contains("red")) {
//                viewHolder.semi_circle.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ED1B2E")));
//            } else if(colorPre.contains("blue")) {
//                viewHolder.semi_circle.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#003399")));
//            } else if(colorPre.contains("yellow")) {
//                viewHolder.semi_circle.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffff00")));
//            } else if(colorPre.contains("green")) {
//                viewHolder.semi_circle.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0CA948")));
//            } else if(colorPre.contains("purple")) {
//                viewHolder.semi_circle.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0CA948")));
//            } else if(colorPre.contains("orange")) {
//                viewHolder.semi_circle.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff9800")));
//            } else if(colorPre.contains("white")) {
//                viewHolder.semi_circle.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
//            } else if(colorPre.contains("black")) {
//                viewHolder.semi_circle.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
//            } else {
//                viewHolder.semi_circle.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#7B3F00")));
//            }
//        } else {
//            viewHolder.semi_circle.setVisibility(View.INVISIBLE);
//        }

        if(cards.get(position).getPicture() != null){
            int idDraw = context.getResources().getIdentifier(cards.get(position).getPicture(), "drawable", context.getPackageName());
            Drawable drawable = context.getResources().getDrawable(idDraw);
            viewHolder.photo.setImageDrawable(drawable);

            viewHolder.row_layout.setBackgroundColor(Color.parseColor(cards.get(position).getColor()));
            viewHolder.name.setText(cards.get(position).getName());
            viewHolder.number.setText(cards.get(position).getNumber());
        } else {
            //no image only text
            viewHolder.name.setText(cards.get(position).getName());
            String number = "**** **** **** ";
            number += cards.get(position).getNumber().substring(cards.get(position).getNumber().length()-4);
            viewHolder.number.setText(number);
        }

        convertView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(context, CardDialogActivity.class);
                intent.putExtra("card", cards.get(position));
                context.startActivity(intent);
            }
        });
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                removeCard(cards.get(position));
                return true;
            }
        });

        return convertView;
    }

    public void removeCard(final CreditCard card) {
        new AlertDialog.Builder(context)
                .setTitle("Do you want to delete this card?")
                .setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        EWalletApp.getDataSource().deleteCreditCard(card);
                        cards.remove(card);
                        notifyDataSetChanged();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {}
                }).show();
    }

} //end
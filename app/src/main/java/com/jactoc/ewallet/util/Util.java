package com.jactoc.ewallet.util;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by jactoc on 2016-03-19.
 */
public class Util {
    public static String getBasePath() {
        return Environment.getExternalStorageDirectory()+"/Looper/";
    }

    public static void cleanUnusedMediaFiles(){
        String[] mediaFiles = new String[]{Util.getBasePath()};

        for(String mediaFile : mediaFiles){
            File file = new File(mediaFile);
            if(file.exists()){
                file.delete();
            }
        }
        File base = new File(getBasePath());
        if(!base.exists())
            base.mkdir();
    }

    public static boolean isAndroidM() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
            return true;
        else
            return false;
    }

    public static boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if(grantResults.length < 1){
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static boolean isOnlyChar(String name) {
        return name.matches("[a-zA-Z]+");
    }

    public static boolean isOnlyNumbers(String name) {
        return name.matches("[0-9]+");
    }

    public static boolean isOnlyCharAndNumbers(String name) {
        return name.matches("^[a-zA-Z0-9]*$");
    }

    public static Bitmap createProfilePictureFromCamera(Bitmap bitmapInput, int dimension){
        int dimensionForBitmap = getSquareCropDimensionForBitmap(bitmapInput);
        Bitmap square = ThumbnailUtils.extractThumbnail(bitmapInput, dimensionForBitmap, dimensionForBitmap);

        return Bitmap.createScaledBitmap(square, dimension, dimension, true);
    }

    //to calculate the dimensions of the bitmap.
    public static int getSquareCropDimensionForBitmap(Bitmap bitmap) {
        //If the bitmap is wider than it is tall use the height as the square crop dimension
        if (bitmap.getWidth() >= bitmap.getHeight()) {
            return bitmap.getHeight();
        }
        //If the bitmap is taller than it is wide use the width as the square crop dimension
        else {
            return bitmap.getWidth();
        }
    }

    public static String getCurrentTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

} //end

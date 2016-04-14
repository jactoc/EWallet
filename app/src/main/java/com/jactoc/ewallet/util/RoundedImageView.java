package com.jactoc.ewallet.util;

/**
 * Created by jactoc on 2016-04-14.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;
import com.jactoc.ewallet.EWalletApp;

public class RoundedImageView extends ImageView {

    public RoundedImageView(Context context) {
        super(context);
    }

    public RoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }

        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        Bitmap b = ((BitmapDrawable) drawable).getBitmap();
        Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);
        Bitmap roundBitmap = getRoundBitmap(bitmap, getWidth(), "#FFFFFF");
        canvas.drawBitmap(roundBitmap, 0, 0, null);
    }

    public static Bitmap getRoundBitmap(Bitmap bmp, int radius, String colorCode) {
        Bitmap sbmp;
        if (bmp.getWidth() != radius || bmp.getHeight() != radius) {
            float smallest = Math.min(bmp.getWidth(), bmp.getHeight());
            float factor = smallest / radius;
            sbmp = Bitmap.createScaledBitmap(bmp, (int)(bmp.getWidth() / factor), (int)(bmp.getHeight() / factor), false);
        } else {
            sbmp = bmp;
        }

        Bitmap output = Bitmap.createBitmap(radius, radius, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Paint stroke = new Paint();

        final Rect rect = new Rect(0, 0, radius, radius);

        paint.setAntiAlias(true);
        stroke.setAntiAlias(true);

        paint.setFilterBitmap(true);
        stroke.setFilterBitmap(true);

        paint.setDither(true);
        stroke.setDither(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor(colorCode));
        stroke.setColor(Color.parseColor(colorCode));
        stroke.setStyle(Paint.Style.STROKE);
        stroke.setStrokeWidth(4f);
        //image
        canvas.drawCircle(radius / 2f, radius / 2f, (radius / 2) - 1f, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(sbmp, rect, rect, paint);
        //external border
        canvas.drawCircle(radius / 2f, radius / 2f, (radius / 2f) - ((stroke.getStrokeWidth()/2) + .5f), stroke);

        return output;
    }

    public static Bitmap getSquaredBitmap(Bitmap bmp, int pixels, String colorCode) {
        Bitmap smbp;
        if (bmp.getWidth() != pixels || bmp.getHeight() != pixels) {
            float smallest = Math.min(bmp.getWidth(), bmp.getHeight());
            float factor = smallest / pixels;
            smbp = Bitmap.createScaledBitmap(bmp, (int)(bmp.getWidth() / factor), (int)(bmp.getHeight() / factor), false);
        } else {
            smbp = bmp;
        }

        Bitmap output = Bitmap.createBitmap(pixels, pixels, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        //change value 5 in case
        final int borderSizePx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) 5,
                EWalletApp.getAppContext().getResources().getDisplayMetrics());
        final int cornerSizePx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) 5,
                EWalletApp.getAppContext().getResources().getDisplayMetrics());

        final Paint paint = new Paint();

        final Rect rect = new Rect(0, 0, output.getWidth(), output.getHeight());
        final RectF rectF = new RectF(rect);

        // prepare canvas for transfer
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor(colorCode));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectF, cornerSizePx, cornerSizePx, paint);

        // draw bitmap
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(smbp, rect, rect, paint);

        // draw border
        paint.setColor(Color.parseColor(colorCode));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth((float) borderSizePx);
        canvas.drawRoundRect(rectF, cornerSizePx, cornerSizePx, paint);

        return output;
    }

}//end
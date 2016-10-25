package com.android.szparag.newadaptiveweather;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

import com.squareup.picasso.Transformation;

/**
 * Created by ciemek on 25/10/2016.
 */

public class PicassoColorTransformation implements Transformation {

    private int colorResId;
    private PorterDuff.Mode mode;

    public PicassoColorTransformation(int colorResId) {
        this(colorResId, PorterDuff.Mode.SRC_ATOP);
    }

    public PicassoColorTransformation(int colorResId, PorterDuff.Mode transformMode) {
        this.colorResId = colorResId;
        mode = transformMode;
    }


    @Override public Bitmap transform(Bitmap source) {

        int width = source.getWidth();
        int height = source.getHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColorFilter(new PorterDuffColorFilter(colorResId, mode));
        canvas.drawBitmap(source, 0, 0, paint);
        source.recycle();

        return bitmap;
    }

    @Override public String key() {
        return "ColorFilterTransformation(color=" + colorResId + ")";
    }
}

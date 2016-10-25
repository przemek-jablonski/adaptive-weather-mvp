package com.android.szparag.newadaptiveweather;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

import com.android.szparag.newadaptiveweather.utils.Computation;
import com.squareup.picasso.Transformation;

/**
 * Created by ciemek on 25/10/2016.
 */

public class PicassoSaturationTransformation implements Transformation {

    private float saturationValue = 0f;

    public PicassoSaturationTransformation(float saturationValue) {
        this.saturationValue = Computation.linearInterpolation(0f, 1f, saturationValue);
    }

    @Override
    public Bitmap transform(Bitmap source) {

        int width = source.getWidth();
        int height = source.getHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        ColorMatrix colorMatrixSaturation = new ColorMatrix();
        colorMatrixSaturation.setSaturation(saturationValue);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrixSaturation));
        canvas.drawBitmap(source, 0, 0, paint);
        source.recycle();

        return bitmap;
    }

    @Override
        public String key () {
        return "GrayscaleTransformation()";
    }
}

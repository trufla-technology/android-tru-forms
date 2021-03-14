package com.trufla.androidtruforms.models;

import android.graphics.Bitmap;

import androidx.annotation.Keep;

/**
 * Created by Marina Wageed on 09,March,2020
 * Trufla Technology,
 * Cairo, Egypt.
 */

@Keep
public class ImageModel
{
    private String imagePath;
    private Bitmap imageBitmap;
    private String base64;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }
}

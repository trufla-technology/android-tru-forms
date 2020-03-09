package com.trufla.androidtruforms.models;

import android.graphics.Bitmap;

/**
 * Created by Marina Wageed on 09,March,2020
 * Trufla Technology,
 * Cairo, Egypt.
 */

public class ImageModel
{
    private String imagePath;
    private Bitmap imageBitmap;

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
}

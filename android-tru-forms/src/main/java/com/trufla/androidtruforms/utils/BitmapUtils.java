package com.trufla.androidtruforms.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import androidx.annotation.Nullable;
import android.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;


public class BitmapUtils
{
    public static String downScaleImageAndConvertToWebPAsBase64(Context ctx, Uri imageUri, int width, int height) {
        String originalPath = imageUri.toString();
        Bitmap bitmap = BitmapFactory.decodeFile(originalPath);
        return encodeBase64Bitmap(width, height, bitmap);
    }

    @Nullable
    public static String encodeBase64Bitmap(int width, int height, Bitmap bitmap) {
        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();

        int newWidth = -1;
        int newHeight = -1;
        float multFactor = -1.0F;

        if (!(originalWidth < width && originalHeight < height)) { //don't scale up the bitmap
            if (originalHeight > originalWidth) {
                newHeight = height;
                multFactor = (float) originalWidth / (float) originalHeight;
                newWidth = (int) (newHeight * multFactor);
            } else if (originalWidth > originalHeight) {
                newWidth = width;
                multFactor = (float) originalHeight / (float) originalWidth;
                newHeight = (int) (newWidth * multFactor);
            } else if (originalHeight == originalWidth) {
                newHeight = height;
                newWidth = width;
            }
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
            bitmap.recycle();
            bitmap = resizedBitmap;
        }


        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.WEBP, 80, outputStream);
            outputStream.close();
            String encodedImage = Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP);
            return "data:image/jpeg;base64,"+encodedImage.replaceAll("\n","");
//            return Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap decodeBase64ToBitmap(String imgBase64)
    {
        String myNewImage = imgBase64.replace("data:image/jpeg;base64,", "");
        byte[] imageByteArray = Base64.decode(myNewImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
    }

    public static Bitmap loadBitmapFromPath(String path) {
        File imgFile = new File(path);
        if (imgFile.exists()) {

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            return myBitmap;

        }
        return null;

    }
}


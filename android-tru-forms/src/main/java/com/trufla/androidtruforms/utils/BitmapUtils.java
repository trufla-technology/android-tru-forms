package com.trufla.androidtruforms.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;

import androidx.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;


public class BitmapUtils {

    @Nullable
    public static String encodeBase64Bitmap(int width, int height, Bitmap bitmap) {
        if (bitmap != null) {
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

                } else {
                    newHeight = height;
                    newWidth = width;
                }
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);


                bitmap.recycle();
                bitmap = resizedBitmap;
            }
            try {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                outputStream.close();
                String encodedImage = Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP);
                return "data:image/jpeg;base64," + encodedImage.replaceAll("\n", "");
//            return Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String convertBitMapToBase64To(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(byteArray, Base64.NO_WRAP);
        return "data:image/jpeg;base64," + encodedImage.replaceAll("\n", "");
    }

    public static Bitmap decodeBase64ToBitmap(String imgBase64) {
        String regex = "(^data).*(,)";
        String myNewImage = imgBase64.replaceAll(regex, "");
        byte[] imageByteArray = Base64.decode(myNewImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static String getRealPathFromURI(Context inContext, Uri uri) {
        String path = "";
        if (inContext.getContentResolver() != null) {
            Cursor cursor = inContext.getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    public static Bitmap handleImageRotation(String filePath, Bitmap mBitmap)
    {
        File imgFile = new File(filePath);
        int angle = 0;

        if (imgFile.exists()) {
            try {
                ExifInterface exif = new ExifInterface(filePath);
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        angle = 90;
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_180:
                        angle = 180;
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_270:
                        angle = 270;
                        break;
                }

                Matrix imageMatrix = new Matrix();
                imageMatrix.setRotate(angle);

                // myBitmap.recycle();

                return Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(),
                        imageMatrix, true);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
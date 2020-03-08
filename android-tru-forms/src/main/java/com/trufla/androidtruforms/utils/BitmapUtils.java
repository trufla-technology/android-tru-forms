package com.trufla.androidtruforms.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
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

    public static String convertBitMapToBase64To(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, byteArrayOutputStream);
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

    public static Bitmap loadBitmapFromPath(String path) {
        File imgFile = new File(path);
        Bitmap myBitmap = null;
        int angle = 0;

        if (imgFile.exists()) {
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            try {
                ExifInterface exif = new ExifInterface(imgFile.getPath());
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

                return Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(), myBitmap.getHeight(),
                        imageMatrix, true);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

//        public static Bitmap loadBitmapFromPath2(String path) {
//        File imgFile = new File(path);
//        if (imgFile.exists())
//        {
//            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//            return myBitmap;
//        }
//        return null;
//    }

//    public static String downScaleImageAndConvertToWebPAsBase64(Uri imageUri, int width, int height) {
//        String originalPath = imageUri.toString();
//        Bitmap bitmap = BitmapFactory.decodeFile(originalPath);
//        return encodeBase64Bitmap(width, height, bitmap);
//    }
}
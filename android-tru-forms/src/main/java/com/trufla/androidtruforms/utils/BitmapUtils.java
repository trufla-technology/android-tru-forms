package com.trufla.androidtruforms.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class BitmapUtils {

    private static final int desiredWidth = 1440;
    private static final int desiredHeight = 1024;

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

    private static String convertBitMapToBase64(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String encodedImage = Base64.encodeToString(byteArray, Base64.NO_WRAP);
            return "data:image/jpeg;base64," + encodedImage.replaceAll("\n", "");
        }
        return "";
    }

    public static String editAndConvertBitMapToBase64(Bitmap bitmap, String imagePath) {
        if (bitmap != null) {
            Bitmap rotatedBitmap = handleImageRotation(imagePath, bitmap);
            if (rotatedBitmap == null)
                rotatedBitmap = bitmap;

            return convertBitMapToBase64(rotatedBitmap);
        }
        return "";
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

    public static Bitmap handleImageRotation(String filePath, Bitmap mBitmap) {
        File imgFile = new File(filePath);
        int angle = 0;

        if (imgFile.exists() && mBitmap != null) {
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


    //SDF to generate a unique name for our compress file.
    @SuppressLint("ConstantLocale")
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyymmddhhmmss", Locale.getDefault());

    public static File getCompressed(Context context, String path) throws IOException {

        if (context == null)
            throw new NullPointerException("Context must not be null.");
        //getting device external cache directory, might not be available on some devices,
        // so our code fall back to internal storage cache directory, which is always available but in smaller quantity
        File cacheDir = context.getExternalCacheDir();
        if (cacheDir == null)
            //fall back
            cacheDir = context.getCacheDir();

        String rootDir = cacheDir.getAbsolutePath() + "/ImageCompressor";
        File root = new File(rootDir);

        //Create ImageCompressor folder if it doesnt already exists.
        if (!root.exists())
            root.mkdirs();


        Bitmap scaledBitmap = decodeImageFromFiles(path, /* your desired width*/desiredWidth, /*your desired height*/ desiredHeight);

        Bitmap resizedBitmap = resizedBitmap(scaledBitmap, desiredWidth, desiredHeight);

        //create placeholder for the compressed image file
        File compressed = new File(root, SDF.format(new Date()) + ".jpg" /*Your desired format*/);

        //convert the decoded bitmap to stream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        /*compress bitmap into byteArrayOutputStream
            Bitmap.compress(Format, Quality, OutputStream)

            Where Quality ranges from 1 - 100.
         */
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        /*
        Right now, we have our bitmap inside byteArrayOutputStream Object, all we need next is to write it to the compressed file we created earlier,
        java.io.FileOutputStream can help us do just That!
         */
        FileOutputStream fileOutputStream = new FileOutputStream(compressed);
        fileOutputStream.write(byteArrayOutputStream.toByteArray());
        fileOutputStream.flush();

        fileOutputStream.close();

        //File written, return to the caller. Done!
        return compressed;
    }

    private static Bitmap resizedBitmap(Bitmap originalScaledBitmap, int desiredWidth, int desiredHeight) {
        Bitmap bitmap;
        int newWidth = -1;
        int newHeight = -1;
        float multFactor = -1.0F;

        if (!(originalScaledBitmap.getWidth() < desiredWidth && originalScaledBitmap.getHeight() < desiredHeight)) {
            if (originalScaledBitmap.getHeight() > originalScaledBitmap.getWidth()) {
                newHeight = desiredHeight;
                multFactor = (float) originalScaledBitmap.getWidth() / (float) originalScaledBitmap.getHeight();
                newWidth = (int) (newHeight * multFactor);

            } else if (originalScaledBitmap.getWidth() > originalScaledBitmap.getHeight()) {
                newWidth = desiredWidth;
                multFactor = (float) originalScaledBitmap.getHeight() / (float) originalScaledBitmap.getWidth();
                newHeight = (int) (newWidth * multFactor);
            } else {
                newHeight = desiredHeight;
                newWidth = desiredWidth;
            }

            bitmap = Bitmap.createScaledBitmap(originalScaledBitmap, newWidth, newHeight, false);
        } else
            bitmap = Bitmap.createScaledBitmap(originalScaledBitmap, originalScaledBitmap.getWidth(), originalScaledBitmap.getHeight(), false);

        return bitmap;
    }

    private static Bitmap decodeImageFromFiles(String path, int width, int height) {
        BitmapFactory.Options scaleOptions = new BitmapFactory.Options();
        scaleOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, scaleOptions);
        int scale = 1;
        while (scaleOptions.outWidth / scale / 2 >= width
                && scaleOptions.outHeight / scale / 2 >= height) {
            scale *= 2;
        }
        // decode with the sample size
        BitmapFactory.Options outOptions = new BitmapFactory.Options();
        outOptions.inSampleSize = scale;
        return BitmapFactory.decodeFile(path, outOptions);
    }

    public static File createImageTempFile(Context context) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a fiwle: path for use with ACTION_VIEW intents
        return image;
    }
}

package com.trufla.androidtruforms.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.trufla.androidtruforms.TruFormActivity;

/**
 * Created by Marina Wageed on 09,March,2020
 * Trufla Technology,
 * Cairo, Egypt.
 */

public class PermissionsUtils
{
    public static boolean checkPermission(Context context)
    {
        int result = ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public static Boolean requestPermission(Activity context)
    {
        return ActivityCompat.shouldShowRequestPermissionRationale(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }
}

package com.trufla.androidtruforms.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;

import org.apache.commons.text.WordUtils;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ohefny on 6/27/18.
 */

public class TruUtils {

    public static boolean isEmpty(EditText et) {
        return TextUtils.isEmpty(et.getText().toString().trim());
    }

    public static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.equals("");
    }

    public static String checkIfStringIsEmpty(String myString) {
        return (myString == null) ? "" : myString;
    }

    public static String setDefaultUserLanguage(String myString) {
        return (myString == null || myString.isEmpty()) ? "en" : myString;
    }

    public static String getText(EditText et) {
        return et.getText().toString().trim();
    }

    public static String getText(EditText et, String defaultText) {
        return isEmpty(et.getText()) ? defaultText : et.getText().toString();
    }

    public static String getText(String val, String defaultText) {
        if (val == null || val.equals(""))
            return defaultText;

        return val;
    }

    public static String removeUnderscoresAndCapitalize(String value) {
        if (!isEmpty(value)) {
            return WordUtils.capitalize(value.replaceAll("_", " "), null);
//            return value.replaceAll("_", " ");
        }
        return value;
    }

    public static String convertToData(long timeInMillis, String dateFormat) {
        Date date = new Date(timeInMillis);
        Format format = new SimpleDateFormat(dateFormat, Locale.getDefault());
        return format.format(date);
    }

    public static String convertDateFormat(String date, String dateFormat) {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat output = new SimpleDateFormat(dateFormat, Locale.getDefault());
        String convertedDate = null;

        try {
            Date dateValue = input.parse(date);
            if (dateValue != null) {
                convertedDate =  output.format(dateValue);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return convertedDate;
    }

    public static Activity getHostActivity(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    public static String numberToString(double d) {
        if (d == (long) d)
            return String.format("%d", (long) d);
        else
            return String.format("%s", d);
    }

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static boolean isNullOrEmpty(ArrayList list) {
        return list == null || list.isEmpty();
    }
    @NonNull
    public static String removeLastColon(String name) {
        if (name.length() > 0 && name.charAt(name.length() - 1) == ',') {
            name = name.substring(0, name.length() - 1);
        }
        return name;
    }

    public static boolean checkIfIsNotEmpty(Object myText)
    {
        if(myText != null)
        {
            String myNewStr = (String) myText;
            return !myNewStr.trim().equals("") && !myNewStr.equals("N/A");
        }
        return false;
    }
}

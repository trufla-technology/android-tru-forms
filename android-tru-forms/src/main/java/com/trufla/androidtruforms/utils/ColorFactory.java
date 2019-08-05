package com.trufla.androidtruforms.utils;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.ColorRes;

public class ColorFactory {

    public static int getTransparentColor(@ColorRes int colorRes, Context context,int alpha){
        return getColorWithAlpha(context.getResources().getColor(colorRes),alpha);
    }
    public static int getColorWithAlpha(int yourColor, int alpha) {
        int red = Color.red(yourColor);
        int blue = Color.blue(yourColor);
        int green = Color.green(yourColor);
        return Color.argb(alpha, red, green, blue);
    }
}

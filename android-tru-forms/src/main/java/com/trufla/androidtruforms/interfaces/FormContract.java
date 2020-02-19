package com.trufla.androidtruforms.interfaces;

import android.graphics.Bitmap;
import android.util.Pair;

import java.util.ArrayList;

public interface FormContract {
    void openImagePicker(TruConsumer<Bitmap> pickedImageListener);

    void onRequestData(TruConsumer<ArrayList<Pair<Object, String>>> listener, String selector, ArrayList<String> names, String url);
}

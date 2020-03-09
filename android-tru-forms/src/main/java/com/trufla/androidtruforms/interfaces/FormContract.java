package com.trufla.androidtruforms.interfaces;

import android.util.Pair;

import com.trufla.androidtruforms.models.ImageModel;

import java.util.ArrayList;

public interface FormContract {
    void openImagePicker(TruConsumer<ImageModel> pickedImageListener);

    void onRequestData(TruConsumer<ArrayList<Pair<Object, String>>> listener, String selector, ArrayList<String> names, String url);
}

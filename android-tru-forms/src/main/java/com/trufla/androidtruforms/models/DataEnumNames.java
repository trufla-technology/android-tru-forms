package com.trufla.androidtruforms.models;

import androidx.annotation.Keep;

import java.util.ArrayList;

@Keep
public class DataEnumNames {
    private final ArrayList<String> names;

    public DataEnumNames(ArrayList<String> names) {
        this.names = names;
    }

    public ArrayList<String> getNames() {
        return names;
    }
}

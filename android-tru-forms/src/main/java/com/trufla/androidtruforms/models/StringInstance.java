package com.trufla.androidtruforms.models;

import com.google.gson.annotations.SerializedName;
import com.trufla.androidtruforms.truviews.TruStringView;

import java.util.ArrayList;

/**
 * Created by ohefny on 6/26/18.
 */

public class StringInstance extends SchemaInstance {
    //Date,Image,textarea
    @SerializedName("format")
    protected String format;
    @SerializedName("maxLength")
    protected int maxLength;
    @SerializedName("minLength")
    protected int minLength;
    @SerializedName("pattern")
    protected String pattern;
    @SerializedName("enum")
    protected ArrayList<String> enumArray; //instance of String or Number or Boolean

    public ArrayList<String> getEnumArray() {
        return enumArray;
    }

    @Override
    public TruStringView getViewBuilder() {
        return new TruStringView(this);
    }
}

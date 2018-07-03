package com.trufla.androidtruforms.models;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.trufla.androidtruforms.truviews.TruStringView;

/**
 * Created by ohefny on 6/26/18.
 */

public class StringInstance extends SchemaInstance  {
    //Date,Image,textarea
    @SerializedName("format")
    protected String format;
    @SerializedName("maxLength")
    protected int maxLength;
    @SerializedName("minLength")
    protected int minLength;
    @SerializedName("pattern")
    protected String pattern;

    @Override
    public TruStringView getViewBuilder(Context context) {
        return new TruStringView(context,this);
    }

    //todo remember to delete if no use case were found for it
    public static class StringEnumInstance extends EnumInstance<String> {

    }

}

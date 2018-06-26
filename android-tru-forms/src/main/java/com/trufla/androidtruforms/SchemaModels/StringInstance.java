package com.trufla.androidtruforms.SchemaModels;

import com.google.gson.annotations.SerializedName;
import com.trufla.androidtruforms.SchemaViews.SchemaBaseView;
import com.trufla.androidtruforms.SchemaViews.TruStringView;

/**
 * Created by ohefny on 6/26/18.
 */

public class StringInstance extends SchemaInstance {
    //Date,Image,textarea
    @SerializedName("format")
    String format;

    @Override
    public TruStringView getViewBuilder() {
        return new TruStringView(this);
    }
}

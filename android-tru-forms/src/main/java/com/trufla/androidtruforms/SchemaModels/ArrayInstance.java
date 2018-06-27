package com.trufla.androidtruforms.SchemaModels;

import com.google.gson.annotations.SerializedName;
import com.trufla.androidtruforms.SchemaViews.TruArrayView;

import java.util.ArrayList;

/**
 * Created by ohefny on 6/26/18.
 */

public class ArrayInstance extends SchemaInstance {
    @SerializedName("items")
    ArrayList<SchemaInstance> items=new ArrayList<>();

    @Override
    public TruArrayView getViewBuilder() {
        return new TruArrayView(this);
    }
}

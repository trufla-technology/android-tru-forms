package com.trufla.androidtruforms.models;

import android.content.Context;

import com.trufla.androidtruforms.truviews.TruBooleanView;

/**
 * Created by ohefny on 6/26/18.
 */

public class BooleanInstance extends SchemaInstance {
    public BooleanInstance(){

    }
    public BooleanInstance(SchemaInstance instance) {
        super(instance);
    }

    @Override
    public BooleanInstance getCopy() {
        return new BooleanInstance(this);
    }

    @Override
    public TruBooleanView getViewBuilder(Context context) {
        return new TruBooleanView(context,this);
    }
}

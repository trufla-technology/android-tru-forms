package com.trufla.androidtruforms.models;

import android.content.Context;

import com.trufla.androidtruforms.truviews.TruBooleanView;

/**
 * Created by ohefny on 6/26/18.
 */

public class BooleanInstance extends SchemaInstance {
    public BooleanInstance(){

    }

    @Override
    public Object getDefaultConst() {
        return false;
    }

    public BooleanInstance(SchemaInstance instance) {
        super(instance);
    }

    @Override
    public TruBooleanView getViewBuilder(Context context) {
        return new TruBooleanView(context,this);
    }
}

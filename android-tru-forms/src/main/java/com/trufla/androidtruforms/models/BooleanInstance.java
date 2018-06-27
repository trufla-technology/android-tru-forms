package com.trufla.androidtruforms.models;

import com.trufla.androidtruforms.truviews.TruBooleanView;

/**
 * Created by ohefny on 6/26/18.
 */

public class BooleanInstance extends SchemaInstance {
    @Override
    public TruBooleanView getViewBuilder() {
        return new TruBooleanView(this);
    }
}

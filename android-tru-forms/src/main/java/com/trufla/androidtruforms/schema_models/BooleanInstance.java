package com.trufla.androidtruforms.schema_models;

import com.trufla.androidtruforms.schema_views.TruBooleanView;

/**
 * Created by ohefny on 6/26/18.
 */

public class BooleanInstance extends SchemaInstance {
    @Override
    public TruBooleanView getViewBuilder() {
        return new TruBooleanView(this);
    }
}

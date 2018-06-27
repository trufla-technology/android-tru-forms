package com.trufla.androidtruforms.schema_views;

import android.view.View;

import com.trufla.androidtruforms.schema_models.BooleanInstance;

/**
 * Created by ohefny on 6/26/18.
 */

public class TruBooleanView extends SchemaBaseView<BooleanInstance>{

    public TruBooleanView(BooleanInstance instance) {
        super(instance);
    }

    @Override
    public View build() {
        return null;
    }
}

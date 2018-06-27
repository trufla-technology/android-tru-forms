package com.trufla.androidtruforms.schema_views;

import android.view.View;

import com.trufla.androidtruforms.schema_models.NumericInstance;

/**
 * Created by ohefny on 6/26/18.
 */

public class TruNumericView extends SchemaBaseView<NumericInstance>{
    public TruNumericView(NumericInstance instance) {
        super(instance);
    }
    @Override
    public View build() {
        return null;
    }
}

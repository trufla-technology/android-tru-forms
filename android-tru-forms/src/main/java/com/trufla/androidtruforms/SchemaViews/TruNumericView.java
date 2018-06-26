package com.trufla.androidtruforms.SchemaViews;

import android.view.View;

import com.trufla.androidtruforms.SchemaModels.NumericInstance;
import com.trufla.androidtruforms.SchemaModels.SchemaInstance;

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

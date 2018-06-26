package com.trufla.androidtruforms.SchemaModels;

import com.trufla.androidtruforms.SchemaViews.TruArrayView;

/**
 * Created by ohefny on 6/26/18.
 */

public class ArrayInstance extends SchemaInstance {
    @Override
    public TruArrayView getViewBuilder() {
        return new TruArrayView(this);
    }
}

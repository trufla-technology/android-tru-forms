package com.trufla.androidtruforms.schema_views;

import android.view.View;

import com.trufla.androidtruforms.schema_models.SchemaInstance;

/**
 * Created by ohefny on 6/26/18.
 */

public abstract class SchemaBaseView<T extends SchemaInstance> {
    T instance;
    public SchemaBaseView(T instance){
        this.instance=instance;
    }
    public abstract View build();
}

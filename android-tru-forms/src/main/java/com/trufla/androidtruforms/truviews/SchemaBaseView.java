package com.trufla.androidtruforms.truviews;

import android.content.Context;
import android.view.View;

import com.trufla.androidtruforms.models.SchemaInstance;

/**
 * Created by ohefny on 6/26/18.
 */

public abstract class SchemaBaseView<T extends SchemaInstance> {
    protected T instance;
    protected Context mContext;
    public SchemaBaseView(Context context, T instance){
        this.instance=instance;
        this.mContext=context;
    }
    public abstract View build();
}

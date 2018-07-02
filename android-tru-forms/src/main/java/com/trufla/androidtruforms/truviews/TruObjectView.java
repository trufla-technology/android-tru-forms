package com.trufla.androidtruforms.truviews;

import android.content.Context;
import android.view.ViewGroup;

import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.models.ObjectInstance;
import com.trufla.androidtruforms.models.SchemaInstance;

/**
 * Created by ohefny on 7/2/18.
 */

public abstract class TruObjectView extends SchemaBaseView<ObjectInstance> {
    public TruObjectView(Context context, ObjectInstance instance) {
        super(context, instance);
    }

    protected abstract void addChildView(SchemaInstance child);

    protected abstract ViewGroup getContainerView();
}

package com.trufla.androidtruforms.truviews;

import android.content.Context;
import android.view.View;

import com.trufla.androidtruforms.models.StringInstance;

/**
 * Created by ohefny on 6/26/18.
 */

public class TruStringView extends SchemaBaseView<StringInstance> {


    public TruStringView(Context context, StringInstance instance) {
        super(context, instance);
    }

    @Override
    public View build() {
        return null;
    }
}

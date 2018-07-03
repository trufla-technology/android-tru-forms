package com.trufla.androidtruforms.truviews;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.TextView;

import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.models.StringInstance;

/**
 * Created by ohefny on 6/26/18.
 */

public class TruStringView extends SchemaBaseView<StringInstance> {


    public TruStringView(Context context, StringInstance instance) {
        super(context, instance);
    }

    @Override
    protected void setInstanceData() {
        ((TextInputLayout) (mView.findViewById(R.id.input_view_container))).setHint(instance.getPresentationTitle());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.tru_string_view;
    }


}

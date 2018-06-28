package com.trufla.androidtruforms.truviews;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;

import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.models.NumericInstance;

/**
 * Created by ohefny on 6/26/18.
 */

public class TruNumericView extends SchemaBaseView<NumericInstance>{

    public TruNumericView(Context context, NumericInstance instance) {
        super(context, instance);
        layoutId= R.layout.tru_number_view;
    }

    @Override
    protected void setInstanceData() {
        ((TextInputLayout)(mView.findViewById(R.id.input_view_container))).setHint(instance.getPresentationTitle());
    }
}

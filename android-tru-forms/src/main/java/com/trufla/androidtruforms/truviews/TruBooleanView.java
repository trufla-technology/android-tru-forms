package com.trufla.androidtruforms.truviews;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.models.BooleanInstance;

/**
 * Created by ohefny on 6/26/18.
 */

public class TruBooleanView extends SchemaBaseView<BooleanInstance>{


    public TruBooleanView(Context context, BooleanInstance instance) {
        super(context, instance);
        layoutId= R.layout.tru_boolean_view;
    }

    @Override
    protected void setInstanceData() {
        ((TextView)(mView.findViewById(R.id.input_data))).setText(instance.getPresentationTitle());
    }
}

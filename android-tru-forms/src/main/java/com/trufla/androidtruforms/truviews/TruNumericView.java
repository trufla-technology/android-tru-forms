package com.trufla.androidtruforms.truviews;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;

import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.models.NumericInstance;

import java.util.Locale;

/**
 * Created by ohefny on 6/26/18.
 */

public class TruNumericView extends SchemaBaseView<NumericInstance> {

    public TruNumericView(Context context, NumericInstance instance) {
        super(context, instance);
    }

    @Override
    protected void setInstanceData() {
        ((TextInputLayout) (mView.findViewById(R.id.input_view_container))).setHint(instance.getPresentationTitle());
    }

    @Override
    public String getInputtedData() {
        String value="null";
        try {
            if(!TextUtils.isEmpty(((TextInputLayout) mView.findViewById(R.id.input_view_container)).getEditText().getText()))
                 value= ((TextInputLayout) mView.findViewById(R.id.input_view_container)).getEditText().getText().toString().trim();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        return String.format(Locale.getDefault(), "\"%s\":%s", instance.getTitle(),value);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.tru_number_view;

    }
}

package com.trufla.androidtruforms.truviews;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.TextView;

import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.models.BooleanInstance;

import java.util.Locale;

/**
 * Created by ohefny on 6/26/18.
 */

public class TruBooleanView extends SchemaBaseView<BooleanInstance> {


    public TruBooleanView(Context context, BooleanInstance instance) {
        super(context, instance);

    }

    @Override
    protected void setInstanceData() {
        ((TextView) (mView.findViewById(R.id.title_data))).setText(instance.getPresentationTitle());
    }

    @Override
    public String getInputtedData() {
        if(mView==null)
            return String.format(Locale.getDefault(),"\"%s\":null",instance.getKey());
        boolean isChecked = ((CheckBox) mView.findViewById(R.id.input_data)).isChecked();
        String str = String.format(Locale.getDefault(), "\"%s\":%s", instance.getKey(), isChecked ? "true" : "false");
        return str;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.tru_boolean_view;
    }

    @Override
    protected boolean isFilled() {
        return mView!=null;
    }

    @Override
    protected void setNonEditableValues(Object constItem) {
        if(constItem instanceof Boolean){
            ((CheckBox) mView.findViewById(R.id.input_data)).setChecked(((Boolean) constItem));
        }
        ((CheckBox) mView.findViewById(R.id.input_data)).setEnabled(false);
    }
}

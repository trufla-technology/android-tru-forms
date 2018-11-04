package com.trufla.androidtruforms.truviews;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.models.EnumInstance;
import com.trufla.androidtruforms.utils.TruUtils;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by ohefny on 7/3/18.
 */

public class TruEnumView extends SchemaBaseView<EnumInstance> {
    protected ArrayAdapter<String> adapter;
    private Spinner spinner;

    public TruEnumView(Context context, EnumInstance instance) {
        super(context, instance);

    }

    @Override
    protected void buildSubview() {
        spinner = mView.findViewById(R.id.spinner);
    }

    protected void setupAdapter(EnumInstance instance) {
        ArrayList<String> items;
        items = instance.getEnumDisplayedNames();
        adapter = new ArrayAdapter<>(mContext, R.layout.support_simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    @Override
    protected void setInstanceData() {
        setupAdapter(instance);
        ((TextView) mView.findViewById(R.id.spinner_title)).setText(instance.getPresentationTitle());
    }

    @Override
    public String getInputtedData() {
        try {
            Object object = getSelectedObject();
            String str = String.format(Locale.getDefault(), "\"%s\":\"%s\"", instance.getKey(), object.toString());
            if (object instanceof Number) {
                str = String.format(Locale.getDefault(), "\"%s\":%s", instance.getKey(), TruUtils.numberToString((Double) object));
            }
            return str;
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            return String.format(Locale.getDefault(), "\"%s\":null", instance.getKey());
        }
    }

    protected Object getSelectedObject() {
        int position = ((Spinner) mView.findViewById(R.id.spinner)).getSelectedItemPosition();
        return instance.getEnumVals().get(position);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.tru_enum_view;
    }

    @Override
    protected void setNonEditableValues(Object constItem) {
        String constStr = String.valueOf(constItem);
        for (int i = 0; i < instance.getEnumVals().size(); i++) {
            String enumStr = String.valueOf(instance.getEnumVals().get(i));
            if (enumStr.equals(constStr)) {
                spinner.setSelection(i);
                break;
            }
        }
        spinner.setEnabled(false);
    }

}

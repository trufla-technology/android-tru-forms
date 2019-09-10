package com.trufla.androidtruforms.truviews;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.models.EnumInstance;
import com.trufla.androidtruforms.utils.TruUtils;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by ohefny on 7/3/18.
 */

public class TruEnumView extends SchemaBaseView<EnumInstance>
{
    protected ArrayAdapter<String> adapter;
    private Spinner spinner;
    protected EnumValueChangedListener valueChangedListener;

    public TruEnumView(Context context, EnumInstance instance) {
        super(context, instance);

    }

    @Override
    protected void buildSubview() {
        spinner = mView.findViewById(R.id.spinner);
    }

    protected void setupAdapter(EnumInstance instance)
    {
        ArrayList<String> items;
        items = instance.getEnumDisplayedNames();
        adapter = new ArrayAdapter<>(mContext, R.layout.support_simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (valueChangedListener != null)
                    valueChangedListener.onEnumValueChanged(instance.getKey(),instance.getEnumVals().get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        if (valueChangedListener != null)
            valueChangedListener.onEnumValueChanged(instance.getKey(),instance.getEnumVals().get(0));
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
            return "";
            //return String.format(Locale.getDefault(), "\"%s\":null", instance.getKey());
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
    protected boolean isFilled() {
        try {
            return getSelectedObject() != null;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected void setNonEditableValues(Object constItem) {
        String constStr = String.valueOf(constItem);
        for (int i = 0; i < instance.getEnumVals().size(); i++)
        {
            String enumStr = String.valueOf(instance.getEnumVals().get(i));
            enumStr = String.valueOf(enumStr).replace(".0","");
            if (enumStr.equals(constStr))
            {
                spinner.setSelection(i);
                break;
            }
        }
        spinner.setEnabled(false);
    }

    public EnumValueChangedListener getValueChangedListener() {
        return valueChangedListener;
    }

    public void setValueChangedListener(EnumValueChangedListener valueChangedListener) {
        this.valueChangedListener = valueChangedListener;
    }


    public interface EnumValueChangedListener {
        void onEnumValueChanged(String itemKey,Object val);
    }

}

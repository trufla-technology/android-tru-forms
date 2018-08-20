package com.trufla.androidtruforms.truviews;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.models.EnumInstance;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by ohefny on 7/3/18.
 */

public class TruEnumView extends SchemaBaseView<EnumInstance> {
    ArrayAdapter<String> adapter;

    public TruEnumView(Context context, EnumInstance instance) {
        super(context, instance);

    }

    private void setupAdapter(EnumInstance instance) {
        ArrayList<String> items;
        items = instance.getEnumDisplayedNames();
        adapter = new ArrayAdapter<>(mContext, R.layout.support_simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    protected void setInstanceData() {
        setupAdapter(instance);
        ((TextView) mView.findViewById(R.id.spinner_title)).setText(instance.getPresentationTitle());
    }

    @Override
    public String getInputtedData() {
        if(mView==null)
            return String.format(Locale.getDefault(),"\"%s\":null",instance.getTitle());
        int position=((Spinner) mView.findViewById(R.id.spinner)).getSelectedItemPosition();
        Object object=instance.getEnumVals().get(position);
        String str=String.format(Locale.getDefault(),"\"%s\":\"%s\"",instance.getTitle(),object.toString());
        if(object instanceof Number){
            str=String.format(Locale.getDefault(),"\"%s\":%s",instance.getTitle(),String.valueOf(object));
        }
        return str;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.tru_enum_view;
    }
    @Override
    public View build() {
        super.build();
        ((Spinner) mView.findViewById(R.id.spinner)).setAdapter(adapter);
        return mView;
    }
}

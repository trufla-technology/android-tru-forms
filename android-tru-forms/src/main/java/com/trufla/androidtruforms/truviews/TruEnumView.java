package com.trufla.androidtruforms.truviews;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.models.EnumInstance;

import java.util.ArrayList;

/**
 * Created by ohefny on 7/3/18.
 */

public class TruEnumView extends SchemaBaseView<EnumInstance> {
    ArrayAdapter<String> adapter;

    public TruEnumView(Context context, EnumInstance instance) {
        super(context, instance);

    }

    private void setupAdapter(EnumInstance instance) {
        ArrayList<String> items = new ArrayList<>();
        items = instance.getEnumDisplayedNames();
        adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    protected void setInstanceData() {
        setupAdapter(instance);
        ((TextView) mView.findViewById(R.id.spinner_title)).setText(instance.getPresentationTitle());
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

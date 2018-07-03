package com.trufla.androidtruforms.truviews;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.models.ArrayInstance;

/**
 * Created by ohefny on 6/26/18.
 */

public class TruArrayView extends SchemaBaseView<ArrayInstance>{


    public TruArrayView(Context context, ArrayInstance instance) {
        super(context, instance);
    }

    @Override
    public View build() {
        super.build();
        ((ViewGroup) mView).addView(instance.getItems().getViewBuilder(mContext).build());
        return mView;
    }

    @Override
    protected void setInstanceData() {
        ((TextView)(mView.findViewById(R.id.input_data))).setText(instance.getPresentationTitle());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.tru_array_view;
    }
}

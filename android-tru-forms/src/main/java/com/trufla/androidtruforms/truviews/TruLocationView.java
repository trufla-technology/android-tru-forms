package com.trufla.androidtruforms.truviews;

import android.content.Context;

import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.models.SchemaInstance;
import com.trufla.androidtruforms.models.StringInstance;

/**
 * Created by ohefny on 7/5/18.
 */

public class TruLocationView extends TruStringView {

    public TruLocationView(Context mContext, StringInstance instance) {
        super(mContext, instance);
    }

    @Override
    protected void setInstanceData() {
        super.setInstanceData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.tru_location_pick_view;
    }
}

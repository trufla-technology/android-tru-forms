package com.trufla.androidtruforms.truviews;

import android.content.Context;
import android.widget.TextView;

import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.models.SchemaInstance;
import com.trufla.androidtruforms.models.StringInstance;

/**
 * Created by ohefny on 7/5/18.
 */

public class TruLocationView extends TruStringView {

    TextView input_title ;
    StringInstance instance ;

    public TruLocationView(Context mContext, StringInstance instance) {
        super(mContext, instance);
        this.instance = instance ;

    }

    @Override
    protected void buildSubview() {
        super.buildSubview();
        input_title = mView.findViewById(R.id.input_title);
    }

    @Override
    protected void setInstanceData() {
        super.setInstanceData();
        if(input_title!=null && instance !=null)
        input_title.setText(instance.getPresentationTitle());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.tru_location_pick_view;
    }
}

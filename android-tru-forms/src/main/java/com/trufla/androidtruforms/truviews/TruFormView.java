package com.trufla.androidtruforms.truviews;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;

import com.google.gson.JsonObject;
import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.models.SchemaDocument;
import com.trufla.androidtruforms.models.SchemaInstance;

import java.util.ArrayList;

/**
 * Created by ohefny on 7/2/18.
 */

public class TruFormView extends TruObjectView {

    public TruFormView(Context context, SchemaDocument instance) {
        super(context, instance);
    }

    @Override
    protected void setInstanceData() {

    }

    @Override
    public View build() {
       super.build();
        for(SchemaInstance child:instance.getProperties().getVals()){
            addChildView(child);
        }
        return mView;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.tru_form_view;
    }
    @Override
    protected ViewGroup getContainerView(){
        return ((ViewGroup)mView.findViewById(R.id.container));
    }
}

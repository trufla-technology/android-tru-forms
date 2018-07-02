package com.trufla.androidtruforms.truviews;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;

import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.models.SchemaDocument;
import com.trufla.androidtruforms.models.SchemaInstance;

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
    protected void addChildView(SchemaInstance child) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0,0, 8);
        View childView=child.getViewBuilder(mContext).build();
        childView.setLayoutParams(layoutParams);
        ((ViewGroup)mView.findViewById(R.id.container)).addView(childView);
    }
    @Override
    protected ViewGroup getContainerView(){
        return ((ViewGroup)mView.findViewById(R.id.container));
    }
}

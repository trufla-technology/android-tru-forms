package com.trufla.androidtruforms.truviews;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.TruUtils;
import com.trufla.androidtruforms.models.ObjectInstance;
import com.trufla.androidtruforms.models.SchemaInstance;

/**
 * Created by ohefny on 6/26/18.
 */

public class TruSectionView extends SchemaBaseView<ObjectInstance>{

    boolean expanded=false;
    public TruSectionView(Context context, ObjectInstance instance) {
        super(context, instance);
        layoutId= R.layout.tru_group_view;
    }

    @Override
    public View build() {
        super.build();
        if(TruUtils.isEmpty(instance.getTitle())){
            mView.findViewById(R.id.section_header).setVisibility(View.GONE);
        }
        else{
            handleExpandBehavior();
            mView.findViewById(R.id.container).setVisibility(View.GONE);
        }
        for(SchemaInstance child:instance.getProperties().getVals()){
            addChildView(child);
        }

        return mView;
    }

    private void addChildView(SchemaInstance child) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0,0, 8);
        View childView=child.getViewBuilder(mContext).build();
        childView.setLayoutParams(layoutParams);
        ((ViewGroup)mView.findViewById(R.id.container)).addView(childView);
    }

    private void handleExpandBehavior() {
        mView.findViewById(R.id.expand_collapse_img).setOnClickListener(view->{
            if(expanded) {
                ((ImageView) (view)).setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_expand_more_white_24dp));
                mView.findViewById(R.id.container).setVisibility(View.GONE);

            }
            else{
                ((ImageView) (view)).setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_expand_less_white_24dp));
                mView.findViewById(R.id.container).setVisibility(View.VISIBLE);
            }
            expanded=!expanded;
        });
    }

    @Override
    protected void setInstanceData() {
        ((TextView)(mView.findViewById(R.id.input_data))).setText(instance.getPresentationTitle());

    }

}

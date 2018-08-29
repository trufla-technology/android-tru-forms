package com.trufla.androidtruforms.truviews;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.exceptions.UnableToFindObjectProperties;
import com.trufla.androidtruforms.models.ObjectInstance;
import com.trufla.androidtruforms.models.SchemaInstance;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by ohefny on 7/2/18.
 */

public abstract class TruObjectView extends SchemaBaseView<ObjectInstance> {
    protected ArrayList<SchemaBaseView> childs = new ArrayList<>();

    public TruObjectView(Context context, ObjectInstance instance) {
        super(context, instance);
    }

    protected abstract ViewGroup getContainerView();

    @Override
    public String getInputtedData() {
        if (mView == null)
            return String.format(Locale.getDefault(), "\"%s\":{}", instance.getKey());
        StringBuilder stringBuilder = new StringBuilder();
        for (SchemaBaseView viewBuilder : childs) {
            stringBuilder.append(viewBuilder.getInputtedData() + ",");
        }
        if (stringBuilder.length() > 0)
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        return String.format(Locale.getDefault(), "\"%s\":{%s}", instance.getKey(), stringBuilder.toString());
    }

    protected void addChildView(SchemaInstance child){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, 8);
        SchemaBaseView childViewBuilder = child.getViewBuilder(mContext);
        View childView = childViewBuilder.build();
        childs.add(childViewBuilder);
        childView.setLayoutParams(layoutParams);
        ((ViewGroup) mView.findViewById(R.id.container)).addView(childView);
        childViewBuilder.setParentView(this);
    }

}

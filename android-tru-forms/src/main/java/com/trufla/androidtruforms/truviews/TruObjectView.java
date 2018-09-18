package com.trufla.androidtruforms.truviews;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.trufla.androidtruforms.R;
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

        if (!validate())
            return null;
        StringBuilder stringBuilder = new StringBuilder();
        for (SchemaBaseView viewBuilder : childs) {
            stringBuilder.append(viewBuilder.getInputtedData() + ",");
        }
        if (stringBuilder.length() > 0)
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        return String.format(Locale.getDefault(), "\"%s\":{%s}", instance.getKey(), stringBuilder.toString());
    }

    protected void addChildView(SchemaInstance child) {
        SchemaBaseView childViewBuilder = child.getViewBuilder(mContext);
        View childView = childViewBuilder.build();
        childs.add(childViewBuilder);
        ((ViewGroup) mView.findViewById(R.id.container)).addView(childView);
        setLayoutParams(childView, childViewBuilder);
        childViewBuilder.setParentView(this);
    }

    protected void setLayoutParams(View childView, SchemaBaseView truView) {
        LinearLayout.LayoutParams layoutParams = truView.getLayoutParams();
        layoutParams.setMargins(0, 0, 0, 8);
        childView.setLayoutParams(layoutParams);
    }

    @Override
    public boolean validate() {
        for (SchemaBaseView viewBuilder : childs) {
            if (!viewBuilder.validate())
                return false;
        }
        return true;
    }
}

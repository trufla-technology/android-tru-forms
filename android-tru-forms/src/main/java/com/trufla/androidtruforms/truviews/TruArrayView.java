package com.trufla.androidtruforms.truviews;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.models.ArrayInstance;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by ohefny on 6/26/18.
 */

public class TruArrayView extends SchemaBaseView<ArrayInstance> {

    ArrayList<SchemaBaseView> items = new ArrayList<>();

    public TruArrayView(Context context, ArrayInstance instance) {
        super(context, instance);
    }

    @Override
    public View build() {
        super.build();
        initPrimaryItem();
        mView.findViewById(R.id.add_item_img).setOnClickListener((v) -> onAddNewView());
        return mView;
    }

    private void initPrimaryItem() {
        SchemaBaseView primaryItem = instance.getItems().getViewBuilder(mContext);
        items.add(primaryItem);
        ((ViewGroup) mView).addView(primaryItem.build());
    }

    private void onAddNewView() {
        SchemaBaseView viewBuilder = getNewInstanceViewBuilder();
        addNewItem(getNewItemView(viewBuilder), viewBuilder);
    }

    private SchemaBaseView getNewInstanceViewBuilder() {
        return instance.getItems().getViewBuilder(mContext);
    }

    private void addNewItem(View newItemView, SchemaBaseView viewBuilder) {
        ((ViewGroup) mView).addView(newItemView);
        items.add(viewBuilder);
    }

    @Override
    protected void setInstanceData() {
        ((TextView) (mView.findViewById(R.id.input_data))).setText(instance.getPresentationTitle());
    }

    @Override
    public String getInputtedData() {
        if (mView == null)
            return String.format(Locale.getDefault(), "\"%s\":null", instance.getKey());
        StringBuilder stringBuilder = new StringBuilder();
        for (SchemaBaseView viewBuilder : items) {
            stringBuilder.append(viewBuilder.getInputtedData().substring(viewBuilder.getInputtedData().indexOf(":") + 1) + ",");
        }
        if (stringBuilder.length() > 0)
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        return String.format(Locale.getDefault(), "\"%s\":[%s]", instance.getKey(), stringBuilder.toString());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.tru_array_view;
    }

    public View getNewItemView(SchemaBaseView newView) {
        View itemView = layoutInflater.inflate(R.layout.tru_item_view, null);
        ((TextView) (itemView.findViewById(R.id.input_data))).setText(instance.getPresentationTitle());
        ((ViewGroup) itemView).addView(newView.build());
        final int viewIdx = items.size();
        itemView.findViewById(R.id.remove_item_img).setOnClickListener(
                (v) -> removeItem(itemView, viewIdx));
        return itemView;

    }

    private void removeItem(View itemView, int idx) {
        ((ViewGroup) mView).removeView(itemView);
        items.remove(idx);
    }
}

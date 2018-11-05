package com.trufla.androidtruforms.truviews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.models.ArrayInstance;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by ohefny on 6/26/18.
 */

public class TruArrayView extends SchemaBaseView<ArrayInstance> {

    SchemaBaseView primaryItem;
    ArrayList<SchemaBaseView> items = new ArrayList<>();
    ArrayList<TextView> headerViews = new ArrayList<>();

    public TruArrayView(Context context, ArrayInstance instance) {
        super(context, instance);
    }

    @Override
    protected void buildSubview() {
        super.buildSubview();
        initPrimaryItem();
        mView.findViewById(R.id.add_item_img).setOnClickListener((v) -> onAddNewView());
    }

    private void initPrimaryItem() {
        primaryItem = instance.getItems().getViewBuilder(mContext);
        View primaryItemView = primaryItem.build();
        setLayoutParams(primaryItemView, primaryItem);
        addNewItem(primaryItemView, primaryItem);
    }

    private View onAddNewView() {
        SchemaBaseView viewBuilder = getNewInstanceViewBuilder();
        View addedView = getNewItemView(viewBuilder);
        addNewItem(addedView, viewBuilder);
        return addedView;
    }

    private SchemaBaseView getNewInstanceViewBuilder() {
        return instance.getItems().getViewBuilder(mContext);
    }

    private void addNewItem(View newItemView, SchemaBaseView viewBuilder) {
        ((ViewGroup) mView).addView(newItemView);
        items.add(viewBuilder);
        headerViews.add(newItemView.findViewById(R.id.input_data));
    }

    @Override
    protected void setInstanceData() {
        String title = getTitle(items.size());
        ((TextView) (mView.findViewById(R.id.input_data))).setText(title);
    }

    @NonNull
    private String getTitle(int number) {
        return mView.getResources().getString(R.string.array_item_no_title, instance.getPresentationTitle(), number);
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

    public View getNewItemView(SchemaBaseView itemViewBuilder) {
        View arrayLayoutView = layoutInflater.inflate(R.layout.tru_array_item_view, null);
        ((TextView) (arrayLayoutView.findViewById(R.id.input_data))).setText(getTitle(items.size() + 1));
        View itemView = itemViewBuilder.build();
        ((ViewGroup) arrayLayoutView).addView(itemView);
        setLayoutParams(itemView, itemViewBuilder);
        arrayLayoutView.findViewById(R.id.remove_item_img).setOnClickListener(
                (v) -> removeItem(arrayLayoutView));
        return arrayLayoutView;

    }

    private void removeItem(View itemView) {
        int idx = ((ViewGroup) mView).indexOfChild(itemView);
        ((ViewGroup) mView).removeView(itemView);
        items.remove(idx - 1);
        headerViews.remove(idx - 1);
        renameTitleViews(idx - 1);
    }

    private void renameTitleViews(int beginIdx) {
        for (int i = beginIdx; i < headerViews.size(); i++) {
            headerViews.get(i).setText(getTitle(i + 1));
        }
    }

    private void setLayoutParams(View childView, SchemaBaseView truView) {
        LinearLayout.LayoutParams layoutParams = truView.getLayoutParams();
        layoutParams.setMargins(0, 4, 0, 4);
        childView.setLayoutParams(layoutParams);
    }

    @Override
    protected void setNonEditableValues(Object constItem) {
        if (constItem instanceof ArrayList) {
            ArrayList constItemsList = (ArrayList) constItem;
            if (constItemsList.size() == 0)
                primaryItem.mView.setVisibility(View.GONE);

            for (int i = 0; i < constItemsList.size(); i++) {
                if (i == 0) {
                    mView.findViewById(R.id.add_item_img).setVisibility(View.GONE);
                    primaryItem.addAfterBuildConstItem(constItemsList.get(i));
                } else {
                    View addedView = onAddNewView();
                    addedView.findViewById(R.id.remove_item_img).setVisibility(View.GONE);
                    items.get(i).addAfterBuildConstItem(constItemsList.get(i));
                }
            }

        }
        mView.setEnabled(false);
    }
}

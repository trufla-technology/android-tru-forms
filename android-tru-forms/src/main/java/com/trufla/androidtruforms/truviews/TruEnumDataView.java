package com.trufla.androidtruforms.truviews;

import android.app.AlertDialog;
import android.content.Context;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.trufla.androidtruforms.interfaces.FormContract;
import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.interfaces.TruConsumer;
import com.trufla.androidtruforms.models.DataInstance;
import com.trufla.androidtruforms.models.EnumInstance;

import java.util.ArrayList;
import java.util.List;

public class TruEnumDataView extends TruEnumView {
    private int selectedPosition = -1;
    private Button pickBtn;

    public TruEnumDataView(Context context, EnumInstance instance) {
        super(context, instance);
    }

    @Override
    protected Object getSelectedObject() {
       /* if (selectedPosition < 0)
            return "null";*/
        Object selected = instance.getEnumVals().get(selectedPosition);
        return selected;

    }

    @Override
    protected int getLayoutId() {
        return R.layout.tru_enum_data_view;
    }

    @Override
    protected void setInstanceData() {
        ((TextView) mView.findViewById(R.id.pick_item_btn_title)).setText(instance.getPresentationTitle());
        if (selectedPosition >= 0 && instance.enumExists()) {
            String choosedItemTitle = "";
            if(instance.getEnumDisplayedNames().size() > 0)
                choosedItemTitle = String.valueOf(instance.getEnumDisplayedNames().get(selectedPosition));

            ((Button) mView.findViewById(R.id.pick_item_btn)).setText(choosedItemTitle);
        }
    }

    @Override
    protected void onViewCreated() {
        pickBtn = mView.findViewById(R.id.pick_item_btn);
        setInstanceData();
        setButtonClickListener();
        super.onViewCreated();
    }

    private void setButtonClickListener() {
        if (!instance.enumExists())
            pickBtn.setOnClickListener(getLoadItemsAction());
        else
            pickBtn.setOnClickListener((v) -> showChooserDialogAction());

    }

    private View.OnClickListener getLoadItemsAction() {
        return (v) -> {
            FormContract formActivity = getFormContract(v);
            if (formActivity != null) {
                DataInstance dataInstance = instance.getDataInstance();
                formActivity.onRequestData(getDataLoadedListener(), dataInstance.getIdentifierColumn(), dataInstance.getNames(), dataInstance.getUrl());
            }
        };
    }

    @NonNull
    private TruConsumer<ArrayList<Pair<Object, String>>> getDataLoadedListener() {
        return (pairArrayList) -> {
            ArrayList<Object> ids = new ArrayList<>();
            ArrayList<String> names = new ArrayList<>();
            for (Pair<Object, String> pair : pairArrayList) {
                ids.add(pair.first);
                names.add(pair.second);
            }
            instance.setEnumVals(ids);
            instance.setEnumNames(names);
            if (!hasConstValue()) {
                setButtonClickListener();
                showChooserDialogAction();
            } else {
                setNonEditableValues(getItemNameForItemValue());
            }
        };
    }

    private boolean hasConstValue() {
        return instance.getConstItem() != null;
    }

    private Object getItemNameForItemValue() {
        int valIdx;
        if (instance.getEnumVals().size() > 0 && instance.getEnumVals().get(0) instanceof String) {
            valIdx = instance.getEnumVals().indexOf(instance.getConstItem());
        } else valIdx = instance.getEnumVals().indexOf(Double.parseDouble(instance.getConstItem().toString()));
        if (valIdx >= 0)
            return instance.getEnumNames().get(valIdx);
        else
            return instance.getConstItem(); //to pervent any unpredictable crashes
    }

    public void showChooserDialogAction() {
        String[] displayedNames = new String[]{};
        if(instance.getEnumDisplayedNames().size() > 0)
            displayedNames = ((List<String>) instance.getEnumDisplayedNames()).toArray(new String[0]);

        new AlertDialog.Builder(mContext)
                .setSingleChoiceItems(displayedNames, 0, null)
                .setPositiveButton("OK", (dialog, whichButton) -> {
                    selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                    if (valueChangedListener != null) {
                        valueChangedListener.onEnumValueChanged(instance.getKey(), instance.getEnumVals().get(selectedPosition));
                    }
                    setInstanceData();
                })
                .show();
    }

    @Override
    protected void setNonEditableValues(Object constItem) {
        if(pickBtn.isEnabled())
            pickBtn.performClick();
        String constStr = String.valueOf(constItem);
        if (TextUtils.isEmpty(constStr))
            pickBtn.setText("Non Selected");
        else
            pickBtn.setText(constStr.toString());
        pickBtn.setEnabled(false);
    }
}

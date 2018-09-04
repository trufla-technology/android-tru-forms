package com.trufla.androidtruforms.truviews;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.TruFormActivity;
import com.trufla.androidtruforms.interfaces.TruConsumer;
import com.trufla.androidtruforms.models.DataInstance;
import com.trufla.androidtruforms.models.EnumInstance;
import com.trufla.androidtruforms.utils.BitmapUtils;
import com.trufla.androidtruforms.utils.TruUtils;

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
        int position = ((Spinner) mView.findViewById(R.id.spinner)).getSelectedItemPosition();
        return instance.getEnumVals().get(position);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.tru_enum_data_view;
    }

    @Override
    protected void setInstanceData() {
        ((TextView) mView.findViewById(R.id.pick_item_btn_title)).setText(instance.getPresentationTitle());
        if (selectedPosition >= 0 && instance.enumExists()) {
            String choosedItemTitle = String.valueOf(instance.getEnumDisplayedNames().get(selectedPosition));
            ((Button) mView.findViewById(R.id.pick_item_btn)).setText(choosedItemTitle);
        }
    }

    @Override
    protected void onViewCreated() {
        setInstanceData();
        pickBtn = mView.findViewById(R.id.pick_item_btn);
        setButtonClickListener();
    }

    private void setButtonClickListener() {
        if (!instance.enumExists())
            pickBtn.setOnClickListener(getLoadItemsAction());
        else
            pickBtn.setOnClickListener((v) -> showChooserDialogAction());
    }

    private View.OnClickListener getLoadItemsAction() {
        return (v) -> {
            TruFormActivity formActivity = getTruFormHostActivity(v);
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
            setButtonClickListener();
            showChooserDialogAction();
        };
    }

    public void showChooserDialogAction() {
        String[] displayedNames = ((List<String>) instance.getEnumDisplayedNames()).toArray(new String[0]);
        new AlertDialog.Builder(getTruFormHostActivity(mView))
                .setSingleChoiceItems(displayedNames, 0, null)
                .setPositiveButton("OK", (dialog, whichButton) -> {
                    selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                    setInstanceData();
                })
                .show();

    }

}

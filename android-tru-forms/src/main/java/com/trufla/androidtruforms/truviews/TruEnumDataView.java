package com.trufla.androidtruforms.truviews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class TruEnumDataView extends TruEnumView {
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
    }

    @Override
    protected void onViewCreated() {
        ((Button) mView).setText(instance.getPresentationTitle());
        if (!instance.enumExists())
            mView.setOnClickListener(getLoadItemsAction());
        else
            mView.setOnClickListener((v) -> showChooserDialogAction());
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
            mView.setOnClickListener((v) -> showChooserDialogAction());
            showChooserDialogAction();
        };
    }

    public void showChooserDialogAction() {

    }
}

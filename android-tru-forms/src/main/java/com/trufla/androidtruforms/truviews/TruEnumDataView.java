package com.trufla.androidtruforms.truviews;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.interfaces.DataContract;
import com.trufla.androidtruforms.interfaces.TitlesListContract;
import com.trufla.androidtruforms.interfaces.TruConsumer;
import com.trufla.androidtruforms.models.DataInstance;
import com.trufla.androidtruforms.models.EnumInstance;
import com.trufla.androidtruforms.utils.EnumDataFormatter;
import com.trufla.androidtruforms.utils.TruUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TruEnumDataView extends TruEnumView {
    private int selectedPosition = -1;
    private MaterialButton pickBtn;
    private Context context;
    private static ArrayList<String> listNames = new ArrayList<>();
    TextView input_title;
    private MaterialButton inputLayout;
    private ProgressDialog progressDialog;
    private TextView tvRequired;

    public TruEnumDataView(Context context, EnumInstance instance) {
        super(context, instance);
        this.context = context;
    }


    @Override
    protected void setViewError(String errorMsg) {
        if (inputLayout != null) {
            tvRequired.setVisibility(View.VISIBLE);
            inputLayout.setIconGravity(MaterialButton.ICON_GRAVITY_END);
            inputLayout.setIconTint(ColorStateList.valueOf(context.getResources().getColor(R.color.design_default_color_error)));
            inputLayout.setIcon(context.getResources().getDrawable(R.drawable.ic_error_1));
            inputLayout.setStrokeColor(ColorStateList.valueOf(context.getResources().getColor(R.color.design_default_color_error)));
        }
    }

    @Override
    protected Object getSelectedObject() {
       /* if (selectedPosition < 0)
            return "null";*/
        return instance.getEnumVals().get(selectedPosition);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.tru_enum_data_view;
    }


    @Override
    protected void setInstanceData() {
        inputLayout.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    setViewError("");
                    selectedPosition = -1;

                } else {
                    disableError();
                }
            }
        });
        if (instance.isRequiredField())
            input_title.setText(instance.getPresentationTitle().concat("*"));
        else
            input_title.setText(instance.getPresentationTitle());
        if (selectedPosition >= 0 && instance.enumExists()) {
            String choosedItemTitle = "";

            if(listNames.size() > 0)
                choosedItemTitle = String.valueOf(listNames.get(selectedPosition));

            else if (instance.getEnumDisplayedNames().size() > 0)
                choosedItemTitle = String.valueOf(instance.getEnumDisplayedNames().get(selectedPosition));

            ((MaterialButton) mView.findViewById(R.id.pick_item_btn)).setText(choosedItemTitle);
        }
    }


    private void disableError() {
        if (inputLayout != null) {
            tvRequired.setVisibility(View.GONE);
            inputLayout.setIconGravity(MaterialButton.ICON_GRAVITY_END);
            inputLayout.setIcon(context.getResources().getDrawable(R.drawable.ic_arrow_drop_down_black_24dp));
            inputLayout.setIconTint(ColorStateList.valueOf(context.getResources().getColor(R.color.dark_grey)));
            inputLayout.setStrokeColor(ColorStateList.valueOf(context.getResources().getColor(R.color.light_grey)));
        }
    }

    @Override
    protected void onViewCreated() {
        pickBtn = mView.findViewById(R.id.pick_item_btn);
        input_title = mView.findViewById(R.id.input_title);
        inputLayout = mView.findViewById(R.id.pick_item_btn);
        tvRequired = mView.findViewById(R.id.tv_required);
        setInstanceData();
        setButtonClickListener();
        super.onViewCreated();
    }

    private void setButtonClickListener() {
        if (!instance.enumExists())
            pickBtn.setOnClickListener(getLoadItemsAction());
        else
            pickBtn.setOnClickListener((v) -> showAPiDataDialog(listNames));
//            pickBtn.setOnClickListener((v) -> showChooserDialogAction());
    }
    private void showProgressDialog(View view) {

        if (progressDialog != null && progressDialog.isShowing())
            return;
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setTitle(Objects.requireNonNull(TruUtils.getHostActivity(view)).getString(R.string.loading));
        progressDialog.show();
    }
    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }
    private View.OnClickListener getLoadItemsAction() {
        return (v) -> {
            showProgressDialog(v);
            if (!hasConstValue()) {
                TitlesListContract titlesListContract= getTitlesListContract(v);
                if (titlesListContract != null){
                    DataInstance dataInstance = instance.getDataInstance();
                    titlesListContract.onRequestTitlesList(getTitlesLoadedListener(dataInstance.getIdentifierColumn(),dataInstance.getNames()),dataInstance.getUrl());

                }
            }else{
                DataContract dataContract = getDataContract(v);
                if (dataContract != null){
                    dataContract.onRequestData(getTitleLoadedListener());
                }
            }
        };
    }
    @NonNull
    private TruConsumer<String> getTitleLoadedListener() {
        hideProgressDialog();
        return this::setNonEditableValues;
    }

    @NonNull
    private TruConsumer<String> getTitlesLoadedListener(final String selector, final ArrayList<String> names) {
        return (responseBody) -> {
            hideProgressDialog();
            ArrayList<Pair<Object, String>> pairArrayList = EnumDataFormatter.getPairList(responseBody, selector, names);
            ArrayList<Object> ids = new ArrayList<>();
            ArrayList<String> titles = new ArrayList<>();
            for (Pair<Object, String> pair : pairArrayList) {
                ids.add(pair.first);
                titles.add(pair.second);
            }

            if(listNames.isEmpty())
                listNames.addAll(titles);

            instance.setEnumVals(ids);
            instance.setMyEnumNa(titles);
            if (!hasConstValue()) {
                setButtonClickListener();
                showAPiDataDialog(titles);
                //showChooserDialogAction();
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

            if(listNames.isEmpty())
                listNames.addAll(names);

            instance.setEnumVals(ids);
            instance.setMyEnumNa(names);
            if (!hasConstValue()) {
                setButtonClickListener();
                showAPiDataDialog(names);
                //showChooserDialogAction();
            } else
                setNonEditableValues(getItemNameForItemValue());
        };
    }

    private boolean hasConstValue() {
        return instance.getConstItem() != null;
    }

    private Object getItemNameForItemValue() {
        int valIdx;
        if (instance.getEnumVals().size() > 0 && instance.getEnumVals().get(0) instanceof String)
            valIdx = instance.getEnumVals().indexOf(instance.getConstItem());
        else
            valIdx = instance.getEnumVals().indexOf(Double.parseDouble(instance.getConstItem().toString()));
        if (valIdx >= 0)
            return instance.getMyEnumNa().get(valIdx);
        else
            return instance.getConstItem(); //to pervent any unpredictable crashes
    }

    public void showAPiDataDialog(ArrayList<String> namesList) {
        String[] displayedNames = new String[]{};
        if (namesList.size() > 0)
            displayedNames = ((List<String>) namesList).toArray(new String[0]);

        new MaterialAlertDialogBuilder(mContext)
                .setTitle(instance.getPresentationTitle())
                .setSingleChoiceItems(displayedNames, 0, null)
                .setPositiveButton(context.getString(R.string.ok), (dialog, whichButton) -> {
                    selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                    if (valueChangedListener != null)
                        valueChangedListener.onEnumValueChanged(instance.getKey(), instance.getEnumVals().get(selectedPosition));
                    setInstanceData();

                }).show();
    }

    public void showChooserDialogAction() {
        String[] displayedNames = new String[]{};
        if (instance.getEnumDisplayedNames().size() > 0)
            displayedNames = ((List<String>) instance.getEnumDisplayedNames()).toArray(new String[0]);

        new MaterialAlertDialogBuilder(mContext)
                .setTitle(instance.getPresentationTitle())
                .setSingleChoiceItems(displayedNames, 0, null)
                .setPositiveButton(context.getString(R.string.ok), (dialog, whichButton) -> {
                    selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                    if (valueChangedListener != null)
                        valueChangedListener.onEnumValueChanged(instance.getKey(), instance.getEnumVals().get(selectedPosition));
                    setInstanceData();

                }).show();
    }

    @Override
    protected void setNonEditableValues(Object constItem) {
        if (pickBtn.isEnabled())
            pickBtn.performClick();
        String constStr = String.valueOf(constItem);
        //      if (TextUtils.isEmpty(constStr))
        //           pickBtn.setText(context.getString(R.string.non_selected));
        //      else

        if (constItem instanceof String){
            pickBtn.setText(constStr);
        }
        if (instance.getEnumVals() != null)
            pickBtn.setText(constStr.toString());
        pickBtn.setEnabled(false);
    }
}

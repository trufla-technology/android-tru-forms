package com.trufla.androidtruforms.truviews;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import com.google.android.material.textfield.TextInputLayout;
import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.models.EnumInstance;
import com.trufla.androidtruforms.utils.TruUtils;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by ohefny on 7/3/18.
 */

public class TruEnumView extends SchemaBaseView<EnumInstance> {

    private int selectedItemPos = -1;

    protected ArrayAdapter<String> adapter;
    //    private Spinner spinner;
    protected EnumValueChangedListener valueChangedListener;

    private AppCompatAutoCompleteTextView autoCompleteTextView;
    private TextInputLayout inputLayout;

    public TruEnumView(Context context, EnumInstance instance) {
        super(context, instance);
    }

    @Override
    protected void buildSubview() {
        inputLayout = mView.findViewById(R.id.input_layout);
        autoCompleteTextView = mView.findViewById(R.id.editText);
    }

    protected void setupAdapter(EnumInstance instance) {
        ArrayList<String> items;
        items = instance.getEnumDisplayedNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, R.layout.support_simple_spinner_dropdown_item, items);
        autoCompleteTextView.setAdapter(adapter);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            autoCompleteTextView.setShowSoftInputOnFocus(false);
        }

        autoCompleteTextView.setOnClickListener(view -> {
            InputMethodManager inputManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null)
                inputManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);

            if (inputLayout != null)
                inputLayout.requestFocus();
            if (autoCompleteTextView != null)
                autoCompleteTextView.showDropDown();
        });


        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            inputLayout.setErrorEnabled(false);
            inputLayout.setError(null);
            selectedItemPos = position;
            if (valueChangedListener != null)
                valueChangedListener.onEnumValueChanged(instance.getKey(), instance.getEnumVals().get(position));
        });

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0)
                    selectedItemPos = -1;
            }
        });
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        if (valueChangedListener != null)
            valueChangedListener.onEnumValueChanged(instance.getKey(), instance.getEnumVals().get(0));
    }

    @Override
    protected void setInstanceData() {
        setupAdapter(instance);
        inputLayout.setHint(instance.getPresentationTitle());
    }

    @Override
    protected void setViewError(String errorMsg) {
        if (inputLayout != null) {
            inputLayout.setErrorEnabled(true);
            inputLayout.setError(errorMsg);
        }
    }


    @Override
    public String getInputtedData() {
        try {
            Object object = getSelectedObject();
            String str = String.format(Locale.getDefault(), "\"%s\":\"%s\"", instance.getKey(), object.toString());
            if (object instanceof Number) {
                str = String.format(Locale.getDefault(), "\"%s\":%s", instance.getKey(), TruUtils.numberToString((Double) object));
            }
            return str;
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            return "";
            //return String.format(Locale.getDefault(), "\"%s\":null", instance.getKey());
        }
    }

    protected Object getSelectedObject() {
        return instance.getEnumVals().get(selectedItemPos);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.tru_enum_view;
    }

    @Override
    protected boolean isFilled() {
        try {
            return getSelectedObject() != null;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected void setNonEditableValues(Object constItem) {
        String constStr = String.valueOf(constItem);
        for (int i = 0; i < instance.getEnumVals().size(); i++) {
            String enumStr = String.valueOf(instance.getEnumVals().get(i));
            enumStr = String.valueOf(enumStr).replace(".0", "");
            if (enumStr.equals(constStr)) {
                String textToDisplay = "";
                if (instance.getEnumNames() != null)
                    textToDisplay = (String) instance.getEnumNames().get(i);
                else
                    textToDisplay = enumStr;

                ArrayAdapter<String> adapter = (ArrayAdapter<String>) autoCompleteTextView.getAdapter();
                autoCompleteTextView.setAdapter(null);
                autoCompleteTextView.setText(textToDisplay);
                autoCompleteTextView.setAdapter(adapter);
                break;
            }
        }
        inputLayout.setEnabled(false);
    }

    public EnumValueChangedListener getValueChangedListener() {
        return valueChangedListener;
    }

    public void setValueChangedListener(EnumValueChangedListener valueChangedListener) {
        this.valueChangedListener = valueChangedListener;
    }

    public interface EnumValueChangedListener {
        void onEnumValueChanged(String itemKey, Object val);
    }
}

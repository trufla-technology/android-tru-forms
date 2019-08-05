package com.trufla.androidtruforms.truviews;

import android.content.Context;
import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputLayout;
import android.text.TextUtils;

import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.models.NumericInstance;
import com.trufla.androidtruforms.utils.TruUtils;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ohefny on 6/26/18.
 */

public class TruNumericView extends SchemaBaseView<NumericInstance> {

    public TruNumericView(Context context, NumericInstance instance) {
        super(context, instance);
    }

    @Override
    protected void setInstanceData() {
        ((TextInputLayout) (mView.findViewById(R.id.input_view_container))).setHint(instance.getPresentationTitle());
    }

    @Override
    public String getInputtedData() {
        try {
            String value = extractData();
            if (!TextUtils.isEmpty(value))
                return String.format(Locale.getDefault(), "\"%s\":%s", instance.getKey(), value);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    @NonNull
    private String extractData() {
        return ((TextInputLayout) mView.findViewById(R.id.input_view_container)).getEditText().getText().toString();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.tru_number_view;

    }

    @Override
    protected boolean isFilled() {
        try {
            if (!TruUtils.isEmpty(extractData()))
                return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean validate() {
        if (!isFilled() && instance.isRequiredField()) {
            setRequiredError();
            return false;
        }
        if (TruUtils.isEmpty(instance.getPattern()) || !isFilled())
            return super.validate();
        try {
            Pattern patternObj = Pattern.compile(instance.getPattern());
            Matcher matcher = patternObj.matcher(extractData());
            if (matcher.matches())
                return true;

        } catch (Exception ex) {
            ex.getMessage();
        }
        setValidationError();
        return false;
    }

    protected void setValidationError() {
        ((TextInputLayout) mView.findViewById(R.id.input_view_container)).setError(mView.getResources().getString(R.string.pattern_validation_error, instance.getPattern()));
        mView.findViewById(R.id.input_view_container).requestFocus();
    }

    private void setRequiredError() {
        ((TextInputLayout) mView.findViewById(R.id.input_view_container)).setError(mView.getResources().getString(R.string.required_field));
        mView.findViewById(R.id.input_view_container).requestFocus();
    }

    @Override
    protected void setNonEditableValues(Object constItem) {
        if (constItem instanceof Number) {
            ((TextInputLayout) mView.findViewById(R.id.input_view_container)).getEditText().setText(constItem.toString());
        }
        mView.findViewById(R.id.input_view_container).setEnabled(false);
    }

}
package com.trufla.androidtruforms.truviews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.models.StringInstance;
import com.trufla.androidtruforms.utils.TruUtils;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ohefny on 6/26/18.
 */

public class TruStringView extends SchemaBaseView<StringInstance> {


    public TruStringView(Context context, StringInstance instance) {
        super(context, instance);
    }

    @Override
    protected void setInstanceData() {
        ((TextInputLayout) (mView.findViewById(R.id.input_view_container))).setHint(instance.getPresentationTitle());
    }

    @Override
    public String getInputtedData() {
        try {
            if(instance.getKey().equals("who_was_driving") || instance.getKey().equals("phone_type_other"))
                return String.format(Locale.getDefault(), "\"%s\":\"%s\"", instance.getKey(), " ");

            if(!TextUtils.isEmpty(extractData()))
                return String.format(Locale.getDefault(), "\"%s\":\"%s\"", instance.getKey(), extractData());

        } catch (NullPointerException ex) {
            ex.printStackTrace();
            //return String.format(Locale.getDefault(), "\"%s\":null", instance.getKey());
        }
        return "";

//        try {
//            if(!TextUtils.isEmpty(extractData()))
//                return String.format(Locale.getDefault(), "\"%s\":\"%s\"", instance.getKey(), extractData());
//        } catch (NullPointerException ex) {
//            ex.printStackTrace();
//            //return String.format(Locale.getDefault(), "\"%s\":null", instance.getKey());
//        }
//        return "";
    }

    @NonNull
    protected String extractData() {
        return ((TextInputLayout) mView.findViewById(R.id.input_view_container)).getEditText().getText().toString().trim();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.tru_string_view;
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
    public boolean isValidAgainstOtherRules() {
        if (TruUtils.isEmpty(instance.getPattern()))
            return true;
        try {
            Pattern patternObj = Pattern.compile(instance.getPattern());
            Matcher matcher = patternObj.matcher(extractData());
            if (matcher.matches())
                return true;

        } catch (Exception ex) {
            ex.getMessage();
        }
        return false;

    }

    @Override
    protected String getRequiredErrorMessage() {
        return mView.getResources().getString(R.string.required_field);
    }

    @Override
    protected String getOtherRulesErrorMessage() {
        return mView.getResources().getString(R.string.pattern_validation_error, instance.getPattern());
    }

/*    @Override
    protected void setError(String errorMsg) {
        if (mView.findViewById(R.id.input_view_container) != null) {
            ((TextInputLayout) mView.findViewById(R.id.input_view_container)).setError(errorMsg);
            mView.findViewById(R.id.input_view_container).requestFocus();
        }
    }


    @Override
    protected void removeErrorMsg() {
        if (mView.findViewById(R.id.input_view_container) != null)
            ((TextInputLayout) mView.findViewById(R.id.input_view_container)).setError(null);

    }*/

    @Override
    protected void setNonEditableValues(Object constItem) {
        if (constItem instanceof String) {
            ((TextInputLayout) mView.findViewById(R.id.input_view_container)).getEditText().setText(constItem.toString());
        }
        mView.findViewById(R.id.input_view_container).setEnabled(false);
    }

}

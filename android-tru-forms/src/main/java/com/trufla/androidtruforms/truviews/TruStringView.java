package com.trufla.androidtruforms.truviews;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.models.StringInstance;
import com.trufla.androidtruforms.utils.TruUtils;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ohefny on 6/26/18.
 */

public class TruStringView extends SchemaBaseView<StringInstance> {

    private String STRING_TYPE = "";
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
    private TextInputLayout textInputLayout;
    private TextInputEditText editText;


    public TruStringView(Context context, StringInstance instance) {
        super(context, instance);
    }

    public TruStringView(Context context, StringInstance instance, String type) {
        super(context, instance);
        STRING_TYPE = type;
    }

    @Override
    protected void buildSubview() {
        textInputLayout = mView.findViewById(R.id.input_view_container);
        editText = mView.findViewById(R.id.input_data);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textInputLayout.setErrorEnabled(false);
                textInputLayout.setError(null);
            }
        });
    }

    @Override
    protected void setInstanceData() {
        textInputLayout.setHint(instance.getPresentationTitle());
    }

    @Override
    protected void setViewError(String errorMsg) {
        if (textInputLayout != null) {
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError(errorMsg);
        }
    }

    @Override
    public String getInputtedData() {
        try {
            if (instance.getKey().equals("phone_type_other"))
                return String.format(Locale.getDefault(), "\"%s\":\"%s\"", instance.getKey(), " ");

            if (instance.getKey().equals("who_was_driving") && TextUtils.isEmpty(extractData()))
                return String.format(Locale.getDefault(), "\"%s\":\"%s\"", instance.getKey(), " ");

            if (!TextUtils.isEmpty(extractData()))
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
        return Objects.requireNonNull((textInputLayout)
                .getEditText()).getText().toString().trim();
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
        if (TruUtils.isEmpty(instance.getPattern()) && !STRING_TYPE.equals("email"))
            return true;

        else if (STRING_TYPE.equals("email"))
            return checkPattern(EMAIL_PATTERN);

        return checkPattern(instance.getPattern());

//        try {
//            Pattern patternObj = Pattern.compile(instance.getPattern());
//            Matcher matcher = patternObj.matcher(extractData());
//            if (matcher.matches())
//                return true;
//
//        } catch (Exception ex) {
//            ex.getMessage();
//        }
//        return false;
    }

    private boolean checkPattern(String originPattern) {
        try {
            Pattern patternObj = Pattern.compile(originPattern);
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
        return mView.getResources().getString(R.string.general_invalid_input);
//        return mView.getResources().getString(R.string.pattern_validation_error, instance.getPattern());
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
            Objects.requireNonNull(((TextInputLayout) mView.findViewById(R.id.input_view_container))
                    .getEditText()).setText(constItem.toString());
        }
        textInputLayout.setEnabled(false);
    }
}

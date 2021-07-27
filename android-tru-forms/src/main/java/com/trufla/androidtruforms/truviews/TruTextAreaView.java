package com.trufla.androidtruforms.truviews;

import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.models.SchemaKeywords;
import com.trufla.androidtruforms.models.StringInstance;
import com.trufla.androidtruforms.utils.TruUtils;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TruTextAreaView extends SchemaBaseView<StringInstance> {
    private String STRING_TYPE = "";

    private TextInputLayout textInputLayout;
    private TextInputEditText editText;
    TextView input_title ;


    public TruTextAreaView(Context context, StringInstance instance) {
        super(context, instance);
    }

    public TruTextAreaView(Context context, StringInstance instance, String type) {
        super(context, instance);
        STRING_TYPE = type;
    }

    @Override
    protected void buildSubview() {
        textInputLayout = mView.findViewById(R.id.input_view_container);
        input_title = mView.findViewById(R.id.input_title);

        editText = mView.findViewById(R.id.input_data);


        if (editText != null) {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (textInputLayout != null) {
                        textInputLayout.setErrorEnabled(false);
                        textInputLayout.setError(null);
                    }
                }
            });
        }
    }

    @Override
    protected void setInstanceData() {
        input_title.setText(instance.getPresentationTitle());
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
            if (instance.getKey().equals("phone_type_other") && TextUtils.isEmpty(extractData()))
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
    protected String extractData()
    {
        String editTextValue = Objects.requireNonNull((textInputLayout)
                .getEditText()).getText().toString().trim();
        return editTextValue.replaceAll("\n"," ");

//        return Objects.requireNonNull((textInputLayout)
//                .getEditText()).getText().toString().trim();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.tru_textarea_view;
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
        return true;

    }


    @Override
    protected String getRequiredErrorMessage() {
        return mView.getResources().getString(R.string.required_field);
    }



    @Override
    public boolean validate() {
        if (!isFilled() && instance.isRequiredField()) {
            setRequiredError();
            return false;
        }

        //setValidationError();
        return true;
    }

    private void setRequiredError() {
        if (mView.findViewById(R.id.input_view_container) != null) {
            TextInputLayout textInputLayout = mView.findViewById(R.id.input_view_container);
            if (textInputLayout != null)
                textInputLayout.setError(mView.getResources().getString(R.string.required_field));
            mView.findViewById(R.id.input_view_container).requestFocus();
        }

        if (mView.findViewById(R.id.input_label) != null){
            TextInputLayout textInputLayout =  mView.findViewById(R.id.input_label);
            if (textInputLayout != null)
                textInputLayout.setError(mView.getResources().getString(R.string.required_field));
            mView.findViewById(R.id.input_label).requestFocus();
        }
    }

    protected void setValidationError() {
        if (mView.findViewById(R.id.input_view_container) != null){
            TextInputLayout textInputLayout =  mView.findViewById(R.id.input_view_container);
            if (textInputLayout != null)
                textInputLayout.setError(mView.getResources().getString(R.string.pattern_validation_error, instance.getPattern()));
            mView.findViewById(R.id.input_view_container).requestFocus();
        }

        if (mView.findViewById(R.id.input_label) != null){
            TextInputLayout textInputLayout =  mView.findViewById(R.id.input_label);
            if (textInputLayout != null)
                textInputLayout.setError(mView.getResources().getString(R.string.pattern_validation_error,instance.getPattern()));
            mView.findViewById(R.id.input_label).requestFocus();
        }
    }


    @Override
    protected void setNonEditableValues(Object constItem) {
        if (constItem instanceof String) {
            Objects.requireNonNull(((TextInputLayout) mView.findViewById(R.id.input_view_container))
                    .getEditText()).setText(Html.fromHtml(Html.fromHtml(constItem.toString()).toString()));
        }
        if (textInputLayout != null)
            textInputLayout.setEnabled(false);
    }
}

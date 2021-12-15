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

/**
 * Created by ohefny on 6/26/18.
 */

public class TruStringView extends SchemaBaseView<StringInstance> {

    private String STRING_TYPE = "";
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
    private static final String DRIVER_LICENCE_NUMBER = "^[a-zA-Z0-9]*$" ;
    private static final String POSTAL_CODE = "^([a-zA-Z][0-9]){3}$";
    private TextInputLayout textInputLayout;
    private TextInputEditText editText;
    TextView input_title ;


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
        input_title = mView.findViewById(R.id.input_title);

        if (instance.getFormat() != null && instance.getFormat().equals(SchemaKeywords.StringFormats.PHONE))
            editText.setInputType(InputType.TYPE_CLASS_PHONE);


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
        if(instance != null) {
            if (instance.getPresentationTitle() != null && input_title != null){
                if (instance.isRequiredField())
                    input_title.setText(instance.getPresentationTitle().concat("*"));
                else
                    input_title.setText(instance.getPresentationTitle());

            }
            if (instance.getPlaceholder() != null)
                editText.setHint(instance.getPlaceholder());
        }
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
        return editTextValue.replaceAll("\n","&lt;br&gt;");

//        return Objects.requireNonNull((textInputLayout)
//                .getEditText()).getText().toString().trim();
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

        else if (STRING_TYPE.equalsIgnoreCase("driver_license_number"))
                return checkPattern(DRIVER_LICENCE_NUMBER);

        else if(STRING_TYPE.equalsIgnoreCase("postal_code"))
               return checkPattern(POSTAL_CODE);


        else if(   !TruUtils.isEmpty(instance.getPattern())
                && ! STRING_TYPE.equals("email")
                && ! STRING_TYPE.equals("driver_license_number")
                && ! STRING_TYPE.equals("postal_code")
              )
            return true ;

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

            if(extractData().isEmpty() || matcher.matches())
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
        if (STRING_TYPE.equals("email"))
            return mView.getResources().getString(R.string.enter_valid_email);
        else
            return mView.getResources().getString(R.string.general_invalid_input);
//        return mView.getResources().getString(R.string.pattern_validation_error, instance.getPattern());
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

    private void setRequiredError() {
        if (mView.findViewById(R.id.input_view_container) != null) {
            TextInputLayout textInputLayout = ((TextInputLayout) mView.findViewById(R.id.input_view_container));
            if (textInputLayout != null)
                textInputLayout.setError(mView.getResources().getString(R.string.required_field));
            mView.findViewById(R.id.input_view_container).requestFocus();
        }

        if (mView.findViewById(R.id.input_label) != null){
            TextInputLayout textInputLayout = ((TextInputLayout) mView.findViewById(R.id.input_label));
            if (textInputLayout != null)
                textInputLayout.setError(mView.getResources().getString(R.string.required_field));
            mView.findViewById(R.id.input_label).requestFocus();
        }
    }

    protected void setValidationError() {
        if (mView.findViewById(R.id.input_view_container) != null){
            TextInputLayout textInputLayout = ((TextInputLayout) mView.findViewById(R.id.input_view_container));
            if (textInputLayout != null)
                textInputLayout.setError(mView.getResources().getString(R.string.pattern_validation_error, instance.getPattern()));
            mView.findViewById(R.id.input_view_container).requestFocus();
        }

        if (mView.findViewById(R.id.input_label) != null){
            TextInputLayout textInputLayout = ((TextInputLayout) mView.findViewById(R.id.input_label));
            if (textInputLayout != null)
                textInputLayout.setError(mView.getResources().getString(R.string.pattern_validation_error,instance.getPattern()));
            mView.findViewById(R.id.input_label).requestFocus();
        }
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
                    .getEditText()).setText(Html.fromHtml(Html.fromHtml(constItem.toString()).toString()));
        }
        if (textInputLayout != null)
            textInputLayout.setEnabled(false);
    }
}

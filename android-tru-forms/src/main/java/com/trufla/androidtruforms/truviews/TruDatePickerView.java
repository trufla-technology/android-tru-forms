package com.trufla.androidtruforms.truviews;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.SchemaBuilder;
import com.trufla.androidtruforms.TruFormFragment;
import com.trufla.androidtruforms.models.StringInstance;
import com.trufla.androidtruforms.utils.TruUtils;

import java.util.Calendar;

public class TruDatePickerView extends TruStringView {

    Calendar cal = Calendar.getInstance();

    private TextInputLayout inputLayout;
    TextView input_title ;

    public TruDatePickerView(Context context, StringInstance instance) {
        super(context, instance);
    }

    @Override
    protected void setInstanceData() {
        if (instance.isRequiredField())
            input_title.setText(instance.getPresentationTitle().concat("*"));
        else
            input_title.setText(instance.getPresentationTitle());
        inputLayout.setHelperTextEnabled(true);
        inputLayout.setHelperText("");
    }

    @NonNull
    protected String getDateHint() {
        return "dd/mm/yyyy";
    }

    @Override
    public String getInputtedData() {
        return super.getInputtedData();
    }

    @NonNull
    @Override
    protected String extractData() {
        return TruUtils.convertDateFormat(((TextInputEditText) mView.findViewById(R.id.input_data)).getText().toString().trim(),getFormat(),"yyyy-MM-dd");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.tru_date_pick_view;
    }


    @Override
    protected void setViewError(String errorMsg) {
        if (inputLayout != null) {
            inputLayout.setErrorEnabled(true);
            inputLayout.setError(errorMsg);
        }
    }

    @Override
    protected void buildSubview()
    {
        super.buildSubview();

        input_title = mView.findViewById(R.id.input_title);
        inputLayout = mView.findViewById(R.id.input_label);
        mView.findViewById(R.id.input_data).setOnClickListener(this::onDateViewClicked);
        mView.setOnClickListener(this::onDateViewClicked);
    }

    private void onDateViewClicked(View view) {
        if (inputLayout != null) {
            inputLayout.setErrorEnabled(false);
            inputLayout.setError(null);
        }
        showDateDialog();
    }

    //     dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
    //        long three_years = (dialog.getDatePicker().getYear() - 3) * DateUtils.YEAR_IN_MILLIS;
    //        long hund_years = (dialog.getDatePicker().getYear() - 100) * DateUtils.YEAR_IN_MILLIS;
    //        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - hund_years);
    //        dialog.getDatePicker().setMaxDate(System.currentTimeMillis() - three_years);


    private long three_years_millsec = 94670856000L ;
    private long hundred_years_millsec = 3155695200000L;
    protected void showDateDialog() {
        //&& TruFormFragment.mySchemaType != 1
        DatePickerDialog dialog = new DatePickerDialog(mContext, getOnDateSetListener(), cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        dialog.setTitle(R.string.select_date);
        if (TruFormFragment.mySchemaType == 4 ){
            dialog.getDatePicker().setMinDate(System.currentTimeMillis() - hundred_years_millsec);
            dialog.getDatePicker().setMaxDate(System.currentTimeMillis() - three_years_millsec);

        }
        dialog.show();
    }

    @NonNull
    protected DatePickerDialog.OnDateSetListener getOnDateSetListener() {
        return (view, year, month, dayOfMonth) -> {
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            onDateChanged(cal.getTimeInMillis());
        };
    }

    protected void onDateChanged(long milliseconds) {
        ((TextInputEditText) mView.findViewById(R.id.input_data)).setText(TruUtils.convertToData(milliseconds, getFormat()));
    }

    protected String getFormat() {
        return SchemaBuilder.getInstance().getDateFormat();
    }

    /*@Override
    protected void setError(String errorMsg) {
        if (isVisibleView()) {
            ((EditText) mView.findViewById(R.id.input_data)).setError("");
            ((TextView) mView.findViewById(R.id.date_picker_error_msg)).setText(errorMsg);
        }
    }

    private boolean isVisibleView() {
        return mView.findViewById(R.id.input_data) != null && mView.findViewById(R.id.date_picker_error_msg) != null;
    }

    @Override
    protected void removeErrorMsg() {
        if (isVisibleView()) {
            ((EditText) mView.findViewById(R.id.input_data)).setError(null);
            ((TextView) mView.findViewById(R.id.date_picker_error_msg)).setText(null);
        }
    }*/

    @Override
    protected void setNonEditableValues(Object constItem) {
        if (constItem instanceof String)
            ((TextInputEditText) mView.findViewById(R.id.input_data)).setText(TruUtils.convertDateFormat(constItem.toString(),"yyyy-MM-dd",getFormat()));

        mView.findViewById(R.id.input_data).setEnabled(false);
        mView.setEnabled(false);
    }
}

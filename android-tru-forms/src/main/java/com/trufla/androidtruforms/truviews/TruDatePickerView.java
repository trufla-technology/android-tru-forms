package com.trufla.androidtruforms.truviews;

import android.app.DatePickerDialog;
import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.SchemaBuilder;
import com.trufla.androidtruforms.TruFormFragment;
import com.trufla.androidtruforms.utils.TruUtils;
import com.trufla.androidtruforms.models.StringInstance;

import java.util.Calendar;

public class TruDatePickerView extends TruStringView {

    Calendar cal = Calendar.getInstance();

    public TruDatePickerView(Context context, StringInstance instance) {
        super(context, instance);
    }

    @Override
    protected void setInstanceData() {
        ((TextView) (mView.findViewById(R.id.input_label))).setText(instance.getPresentationTitle());
        ((TextView) (mView.findViewById(R.id.input_data))).setHint(getDateHint());

    }

    @NonNull
    protected String getDateHint() {
        return "YYYY/MM/DD";
    }

    @Override
    public String getInputtedData() {
        return super.getInputtedData();
    }

    @NonNull
    @Override
    protected String extractData() {
        return ((EditText) mView.findViewById(R.id.input_data)).getText().toString().trim();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.tru_date_pick_view;
    }

    @Override
    protected void buildSubview() {
        super.buildSubview();
        mView.findViewById(R.id.input_data).setOnClickListener(this::onDateViewClicked);
        mView.setOnClickListener(this::onDateViewClicked);

    }

    private void onDateViewClicked(View view) {
        showDateDialog();
    }


    protected void showDateDialog() {
        DatePickerDialog dialog = new DatePickerDialog(mContext, getOnDateSetListener(), cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        dialog.setTitle(R.string.select_date);
        if(TruFormFragment.mySchemaType != 4 && TruFormFragment.mySchemaType != 1)
            dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

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
        ((EditText) mView.findViewById(R.id.input_data)).setText(TruUtils.convertToData(milliseconds, getFormat()));
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
            ((EditText) mView.findViewById(R.id.input_data)).setText(constItem.toString());

        mView.findViewById(R.id.input_data).setEnabled(false);
        mView.setEnabled(false);
    }
}

package com.trufla.androidtruforms.truviews;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import androidx.annotation.NonNull;

import com.trufla.androidtruforms.SchemaBuilder;
import com.trufla.androidtruforms.models.StringInstance;

import java.util.Calendar;

public class TruDateTimePickerView extends TruDatePickerView {
    public TruDateTimePickerView(Context context, StringInstance instance) {
        super(context, instance);
    }

    @NonNull
    @Override
    protected String getDateHint() {
        return "YYYY/MM/DD Hrs:Mins";
    }

    protected void showTimeDialog() {
        TimePickerDialog dialogTime = new TimePickerDialog(mContext, (view2, hourOfDay, minute) -> {
            cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
            cal.set(Calendar.MINUTE, minute);
            onDateChanged(cal.getTimeInMillis());
        }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false);
        dialogTime.show();
    }
    @NonNull
    protected DatePickerDialog.OnDateSetListener getOnDateSetListener() {
        return (view, year, month, dayOfMonth) -> {
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            showTimeDialog();
        };
    }

    @Override
    public String getInputtedData() {

        return super.getInputtedData();
    }
    protected String getFormat(){
        return SchemaBuilder.getInstance().getDateTimeFormat();
    }
}

package com.trufla.androidtruforms.truviews;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;

import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.models.StringInstance;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TruDatePickerView extends TruStringView{

    Calendar cal=Calendar.getInstance();
    public TruDatePickerView(Context context, StringInstance instance) {
        super(context, instance);
    }

    @Override
    protected void setInstanceData() {
            super.setInstanceData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.tru_date_pick_view;
    }

    @Override
    public View build() {
        super.build();
        mView.findViewById(R.id.picker).setOnClickListener((v)->{
            DatePickerDialog dialog = new DatePickerDialog(mContext, (view, year, month, dayOfMonth) -> {
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                TimePickerDialog dialogTime = new TimePickerDialog(mContext, (view2, hourOfDay, minute) -> {
                    cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    cal.set(Calendar.MINUTE, minute);
                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false);
                dialogTime.setTitle(R.string.select_time);
                dialogTime.show();
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            dialog.setTitle(R.string.select_date);
            dialog.show();

        });
        return mView;
    }
}

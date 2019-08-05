package com.trufla.androidtruforms.truviews;

import android.app.TimePickerDialog;
import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.SchemaBuilder;
import com.trufla.androidtruforms.models.StringInstance;
import com.trufla.androidtruforms.utils.TruUtils;

import java.util.Calendar;


public class TruTimePickerView extends TruStringView
{
    private Calendar cal = Calendar.getInstance();

    public TruTimePickerView(Context context, StringInstance instance) {
        super(context, instance);
    }

    @Override
    protected void setInstanceData() {
        ((TextView) (mView.findViewById(R.id.input_label))).setText(instance.getPresentationTitle());
        ((TextView) (mView.findViewById(R.id.input_data))).setHint(getDateHint());
    }

    @NonNull
    protected String getDateHint() {
        return "HH:mm:ss";
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
        mView.findViewById(R.id.input_data).setOnClickListener(this::onTimeViewClicked);
        mView.setOnClickListener(this::onTimeViewClicked);

    }

    private void onTimeViewClicked(View view) {
        showTimeDialog();
    }

    protected void showTimeDialog() {
        TimePickerDialog dialogTime = new TimePickerDialog(mContext, (view2, hourOfDay, minute) -> {
            cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
            cal.set(Calendar.MINUTE, minute);
            onDateChanged(cal.getTimeInMillis());
        }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false);
        dialogTime.show();
    }


    protected void onDateChanged(long milliseconds) {
        ((EditText) mView.findViewById(R.id.input_data)).setText(TruUtils.convertToData(milliseconds, getFormat()));
    }

    protected String getFormat() {
        return SchemaBuilder.getInstance().getTimeFormat();
    }

    @Override
    protected void setNonEditableValues(Object constItem) {
        if (constItem instanceof String)
            ((EditText) mView.findViewById(R.id.input_data)).setText(constItem.toString());

        mView.findViewById(R.id.input_data).setEnabled(false);
        mView.setEnabled(false);
    }
}

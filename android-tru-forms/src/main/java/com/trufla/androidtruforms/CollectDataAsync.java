package com.trufla.androidtruforms;

import android.os.AsyncTask;

import com.trufla.androidtruforms.truviews.SchemaBaseView;

import java.util.Locale;

/**
 * Created by Mohamed Salah on 15,March,2020
 * Trufla Technology,
 * Cairo, Egypt.
 */
public class CollectDataAsync extends AsyncTask<SchemaBaseView, Void, String> {


    private TruFormFragment.OnFormActionsListener mListener;
    private String instanceKey;

    public CollectDataAsync(TruFormFragment.OnFormActionsListener mListener, String instanceKey) {
        this.mListener = mListener;
        this.instanceKey = instanceKey;
    }

    @Override
    protected String doInBackground(SchemaBaseView... schemaBaseViews) {

        StringBuilder stringBuilder = new StringBuilder();

        for (SchemaBaseView viewBuilder : schemaBaseViews) {
            if (viewBuilder != null && viewBuilder.getInputtedData() != null && !viewBuilder.getInputtedData().equals(""))
                stringBuilder.append(viewBuilder.getInputtedData()).append(",");
        }

        if (stringBuilder.length() > 0 && stringBuilder.charAt(stringBuilder.length() - 1) == ',')
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        else
            return "";

        return String.format(Locale.getDefault(), "\"%s\":{%s}", instanceKey, stringBuilder.toString());

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mListener.onFormSubmitted(s);
    }
}

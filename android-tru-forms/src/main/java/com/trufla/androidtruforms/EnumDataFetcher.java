package com.trufla.androidtruforms;

import android.util.Pair;
import com.trufla.androidtruforms.interfaces.TruConsumer;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import okhttp3.Callback;
import okhttp3.Request;


public class EnumDataFetcher {
    TruConsumer<ArrayList<Pair<Object, String>>> mListener;
    String mSelector;
    ArrayList<String> mNames;

    public EnumDataFetcher(TruConsumer<ArrayList<Pair<Object, String>>> listener, String selector, ArrayList<String> names) {
        this.mListener = listener;
        this.mNames = names;
        this.mSelector = selector;
    }

    public void requestData(String url, Callback callback) {
        SchemaBuilder.getInstance().getOkHttpClient().newCall(getFullRequest(url)).enqueue(callback);
    }


    private Request getFullRequest(String absoluteUrl) {

        ArrayList<String> urlIncludes = new ArrayList<>();
        StringBuilder fullURL = new StringBuilder(absoluteUrl);
        String currentDate = getCurrentDate();
        for (String mName : mNames) {
            if (mName.contains(".")) {
                String[] parts = mName.split("\\.");
                urlIncludes.add(parts[0]);
            }
        }
        if (urlIncludes.size() != 0) {
            fullURL.append("?includes=");
            for (int i = 0; i < urlIncludes.size(); i++)
                fullURL.append(urlIncludes.get(i)).append(",");
        }

        switch (absoluteUrl)
        {
            case "/vehicles":
            case "/properties":
                if(fullURL.toString().contains("?includes="))
                    fullURL.append("location.policy&location.policy.policy_expiration_date=gteq::").append(currentDate).append("&location.policy.cycle_business_purpose=!eq::XLN");
                else
                    fullURL.append("?includes=location.policy&location.policy.policy_expiration_date=gteq::").append(currentDate).append("&location.policy.cycle_business_purpose=!eq::XLN");
                break;

            case "/policies":
                fullURL.append("?policy_expiration_date=gteq::").append(currentDate).append("&cycle_business_purpose=!eq::XLN");
                break;
        }

        Request request = SchemaBuilder.getInstance().getRequestBuilder().build();
        return request.newBuilder().url(request.url() + fullURL.toString()).build();
    }

    private String getCurrentDate()
    {
        Date date = new Date();
        Format format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return format.format(date);
    }

}
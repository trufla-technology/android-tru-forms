package com.trufla.androidtruforms;

import android.app.ProgressDialog;
import android.util.Log;
import android.util.Pair;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.trufla.androidtruforms.interfaces.TruConsumer;

import java.util.ArrayList;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

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
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(getLoggingInterceptor()).build();
        client.newCall(getFullRequest(url)).enqueue(callback);
    }


    private Request getFullRequest(String absoluteUrl) {
        Request request = SchemaBuilder.getInstance().getRequestBuilder().build();
        return request.newBuilder().url(request.url() + absoluteUrl).build();
    }
    public HttpLoggingInterceptor getLoggingInterceptor() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor((logMessage) -> Log.d("Network",logMessage));

        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        return loggingInterceptor;
    }


}
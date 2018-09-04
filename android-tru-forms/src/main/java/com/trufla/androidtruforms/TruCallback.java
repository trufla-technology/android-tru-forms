package com.trufla.androidtruforms;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

public abstract class TruCallback implements Callback {
    Handler mainHandler;

    public TruCallback(Context context) {
        mainHandler = new Handler(context.getMainLooper());

    }

    @Override
    public void onFailure(Call call, IOException e) {
        mainHandler.post(() -> onUIFailure(e.getMessage()));
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        mainHandler.post(() -> {
                    if (response.isSuccessful() && response.body() != null) {
                        onUISuccess(response.body());
                    } else {
                        onUIFailure(response.message());
                    }
                }
        );

    }

    public abstract void onUIFailure(String message);

    public abstract void onUISuccess(ResponseBody responseBody);
}

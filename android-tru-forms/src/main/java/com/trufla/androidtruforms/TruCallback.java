package com.trufla.androidtruforms;

import android.content.Context;
import android.os.Handler;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public abstract class TruCallback implements Callback {
    Handler mainHandler;

    public TruCallback(Context context) {
        mainHandler = new Handler(context.getMainLooper());

    }

    @Override
    public void onFailure(Call call, IOException e) {
        fail(e.getMessage());
    }

    @Override
    public void onResponse(Call call, Response response) {
        if (response.isSuccessful() && response.body() != null) {
            final String body;
            try {
                body = response.body().string();
                mainHandler.post(() -> onUISuccess(body));

            } catch (IOException e) {
                e.printStackTrace();
                fail(e.getMessage());

            }
        } else {
            String msg = response.message();
            fail(msg);
        }

    }

    private void fail(String msg) {
        mainHandler.post(() -> onUIFailure(msg));
    }

    public abstract void onUIFailure(String message);

    public abstract void onUISuccess(String responseBody);
}

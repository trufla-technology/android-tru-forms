package com.trufla.androidtruforms;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.trufla.androidtruforms.databinding.ActivityTruFormBinding;
import com.trufla.androidtruforms.interfaces.TruConsumer;
import com.trufla.androidtruforms.truviews.TruFormView;
import com.trufla.androidtruforms.utils.TruUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ohefny on 8/13/18.
 */

public class TruFormActivity extends AppCompatActivity {
    private static final String JSON_KEY = "JSON_KEY";
    private static final int IMAGE_PICKER_CODE = 505;
    private static SchemaBuilder sSchemaBuilder;
    private TruFormView truFormView;
    TruConsumer<String> mPickedImageListener;
    TruConsumer<ArrayList<Pair<Object, String>>> mDataFetchListener;

    public static void startActivityForFormResult(Activity context, String jsonStr, SchemaBuilder schemaBuilder) {
        Intent intent = new Intent(context, TruFormActivity.class);
        sSchemaBuilder = schemaBuilder;
        intent.putExtra(JSON_KEY, jsonStr);
        context.startActivityForResult(intent, SchemaBuilder.REQUEST_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTruFormBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_tru_form);
        try {
            truFormView = sSchemaBuilder.buildSchemaView(getIntent().getExtras().getString(JSON_KEY), this);
            View formView = truFormView.build();
            binding.formContainer.addView(formView);
            binding.setFormView(this);
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(this, "Unable to create the form ... please check the schema", Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED);
            finish();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    public void onSubmitClicked() {
        if (!isValidData())
            return;
        Toast.makeText(this, "submitted", Toast.LENGTH_SHORT).show();
        String result = truFormView.getInputtedData();
        Intent intent = new Intent();
        intent.putExtra(SchemaBuilder.RESULT_DATA_KEY, result);
        setResult(RESULT_OK, intent);
        finish();

    }

    public void openImagePicker(TruConsumer<String> pickedImageListener) {
        this.mPickedImageListener = pickedImageListener;
        ImagePicker.create(this).single() // single mode
                // Activity or Fragment
                .start(IMAGE_PICKER_CODE);
    }

    public void onRequestData(TruConsumer<ArrayList<Pair<Object, String>>> listener, String selector, ArrayList<String> names, String url) {
        this.mDataFetchListener = listener;
        new EnumDateFetcher(mDataFetchListener, selector, names).requestData(url);
        //todo fetch results
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_PICKER_CODE) {
            // Get a list of picked images
            List<Image> images = ImagePicker.getImages(data);
            // or get a single image only
            if (images == null || images.size() == 0)
                return;
            Image image = ImagePicker.getImages(data).get(0);
            String path = image.getPath();
            if (!TruUtils.isEmpty(path) && mPickedImageListener != null) {
                mPickedImageListener.accept(path);
            }

        }
    }

    private boolean isValidData() {
        return true;
    }

    class EnumDateFetcher {
        TruConsumer<ArrayList<Pair<Object, String>>> mListener;
        String mSelector;
        ArrayList<String> mNames;

        EnumDateFetcher(TruConsumer<ArrayList<Pair<Object, String>>> listener, String selector, ArrayList<String> names) {
            this.mListener = listener;
            this.mNames = names;
            this.mSelector = selector;
        }

        private void requestData(String url) {
            OkHttpClient client = new OkHttpClient();
            client.newCall(getFullRequest(url)).enqueue(getHttpCallback());
        }

        @NonNull
        private Callback getHttpCallback() {
            return new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.body() != null && response.isSuccessful())
                        mDataFetchListener.accept(getPairList(response.body().string()));
                    else
                        onFailure(call, new IOException("Can't fetch the data"));
                }
            };
        }

        private Request getFullRequest(String absoluteUrl) {
            Request request = SchemaBuilder.getInstance().getRequestBuilder().build();
            return request.newBuilder().url(request.url() + absoluteUrl).build();
        }

        public ArrayList<Pair<Object, String>> getPairList(String string) {
            ArrayList<Pair<Object, String>> list = new ArrayList<>();
            JsonArray jsonArray = new JsonParser().parse(string).getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                list.add(getPairFromObject(jsonArray.get(i).getAsJsonObject()));
            }
            return list;
        }

        private Pair<Object, String> getPairFromObject(JsonObject asJsonObject) {
            Object key = new Gson().fromJson(asJsonObject.getAsJsonPrimitive(mSelector), Object.class);
            String name = "";
            for (String n : mNames) {
                name += String.valueOf(new Gson().fromJson(asJsonObject.getAsJsonPrimitive(n), Object.class)) + ",";
            }
            if (name.length() > 0 && name.charAt(name.length() - 1) == ',') {
                name = name.substring(0, name.length() - 1);
            }
            return new Pair<>(key, name);
        }
    }

}

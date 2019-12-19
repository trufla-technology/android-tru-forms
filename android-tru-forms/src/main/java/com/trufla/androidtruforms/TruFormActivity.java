package com.trufla.androidtruforms;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.trufla.androidtruforms.interfaces.FormContract;
import com.trufla.androidtruforms.interfaces.TruConsumer;
import com.trufla.androidtruforms.truviews.TruFormView;
import com.trufla.androidtruforms.utils.EnumDataFormatter;
import com.trufla.androidtruforms.utils.TruUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Callback;

/**
 * Created by ohefny on 8/13/18.
 */

public class TruFormActivity extends AppCompatActivity implements FormContract {
    private static final String SCHEMA_KEY = "SCHEMA_KEY";
    private static final int IMAGE_PICKER_CODE = 505;
    private static final String JSON_KEY = "JSON_KEY";
    private TruFormView truFormView;
    TruConsumer<String> mPickedImageListener;
    TruConsumer<ArrayList<Pair<Object, String>>> mDataFetchListener;
    ProgressDialog progressDialog;

    public static void startActivityForFormResult(Activity context, String jsonStr) {
        Intent intent = new Intent(context, TruFormActivity.class);
        intent.putExtra(SCHEMA_KEY, jsonStr);
        context.startActivityForResult(intent, SchemaBuilder.REQUEST_CODE);
    }

    public static void startActivityForFormResult(Fragment hostFragment, String jsonStr) {
        Intent intent = new Intent(hostFragment.getActivity(), TruFormActivity.class);
        intent.putExtra(SCHEMA_KEY, jsonStr);
        hostFragment.startActivityForResult(intent, SchemaBuilder.REQUEST_CODE);
    }


    public static void startActivityToRenderConstSchema(Fragment hostFragment, String jsonStr, String jsonVal) {
        Intent intent = new Intent(hostFragment.getActivity(), TruFormActivity.class);
        intent.putExtra(SCHEMA_KEY, jsonStr);
        intent.putExtra(JSON_KEY, jsonVal);
        hostFragment.startActivityForResult(intent, SchemaBuilder.REQUEST_CODE);
    }

    public static void startActivityToRenderConstSchema(Activity context, String jsonStr, String jsonVal) {
        Intent intent = new Intent(context, TruFormActivity.class);
        intent.putExtra(SCHEMA_KEY, jsonStr);
        intent.putExtra(JSON_KEY, jsonVal);
        context.startActivityForResult(intent, SchemaBuilder.REQUEST_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tru_form);
        try {
            String jsonVal = Objects.requireNonNull(getIntent().getExtras()).getString(JSON_KEY);
            if (TextUtils.isEmpty(jsonVal))
                truFormView = SchemaBuilder.getInstance().buildSchemaView(getIntent().getExtras().getString(SCHEMA_KEY), this);
            else {
                truFormView = SchemaBuilder.getInstance().buildSchemaViewWithConstValues(getIntent().getStringExtra(SCHEMA_KEY), jsonVal, this);
                findViewById(R.id.submit_btn).setVisibility(View.GONE);
            }
            View formView = truFormView.build();
            ((LinearLayout) findViewById(R.id.form_container)).addView(formView);
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(this, getString(R.string.unable_to_create_form), Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED);
            finish();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void openImagePicker(TruConsumer<String> pickedImageListener) {
        this.mPickedImageListener = pickedImageListener;
        ImagePicker.create(this).single() // single mode
                // Activity or Fragment
                .start(IMAGE_PICKER_CODE);
    }

    @Override
    public void onRequestData(TruConsumer<ArrayList<Pair<Object, String>>> listener, String selector, ArrayList<String> names, String url) {
        this.mDataFetchListener = listener;
        EnumDataFetcher fetcher = new EnumDataFetcher(mDataFetchListener, selector, names);
        int userId = 1365;  //This is just a temp value because i don't use this activity
        fetcher.requestData(url, getHttpCallback(selector, names));
        showProgressDialog();
    }

    private void showProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing())
            return;
        progressDialog = new ProgressDialog(TruFormActivity.this);
        progressDialog.setTitle(getString(R.string.loading));
        progressDialog.show();
    }

    private boolean isValidData() {
        return truFormView.getInputtedData() != null;
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


    @NonNull
    private Callback getHttpCallback(final String selector, final ArrayList<String> names) {
        return new TruCallback(TruFormActivity.this.getApplicationContext()) {
            @Override
            public void onUIFailure(String message) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                Toast.makeText(TruFormActivity.this, getString(R.string.cant_load_data) + message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUISuccess(String responseBody) {
                progressDialog.dismiss();
                mDataFetchListener.accept(EnumDataFormatter.getPairList(responseBody, selector, names));
            }
        };
    }


    public void onSubmitClicked(View view) {
        if (!isValidData()) {
            Toast.makeText(this, getString(R.string.please_correct_errors), Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, getString(R.string.submitted), Toast.LENGTH_SHORT).show();
        String result = truFormView.getInputtedData();
        Intent intent = new Intent();
        intent.putExtra(SchemaBuilder.RESULT_DATA_KEY, result);
        setResult(RESULT_OK, intent);
        finish();
    }
}

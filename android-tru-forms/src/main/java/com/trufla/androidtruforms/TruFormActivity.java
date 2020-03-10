package com.trufla.androidtruforms;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.trufla.androidtruforms.interfaces.FormContract;
import com.trufla.androidtruforms.interfaces.TruConsumer;
import com.trufla.androidtruforms.models.ImageModel;
import com.trufla.androidtruforms.truviews.TruFormView;
import com.trufla.androidtruforms.utils.BitmapUtils;
import com.trufla.androidtruforms.utils.EnumDataFormatter;
import com.trufla.androidtruforms.utils.PermissionsUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.Callback;

/**
 * Created by ohefny on 8/13/18.
 */

public class TruFormActivity extends AppCompatActivity implements FormContract {
    private static final int PICK_IMAGE_CODE = 1;
    private static final int CAPTURE_IMAGE_CODE = 2;
    private static final int PERMISSION_REQUEST_CODE = 1;

    private static final String SCHEMA_KEY = "SCHEMA_KEY";
    private static final String JSON_KEY = "JSON_KEY";
    private TruFormView truFormView;
    TruConsumer<ImageModel> mPickedImageListener;
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

//        intent.putExtra(JSON_KEY, jsonVal);
        FormModel formModel = new FormModel();
        formModel.setJsonStr(jsonVal);
        intent.putExtra(JSON_KEY, formModel);

        hostFragment.startActivityForResult(intent, SchemaBuilder.REQUEST_CODE);
    }

    public static void startActivityToRenderConstSchema(Activity context, String jsonStr, String jsonVal) {
        Intent intent = new Intent(context, TruFormActivity.class);
        intent.putExtra(SCHEMA_KEY, jsonStr);

//        intent.putExtra(JSON_KEY, jsonVal);

        FormModel formModel = new FormModel();
        formModel.setJsonStr(jsonVal);
        intent.putExtra(JSON_KEY, formModel);
        context.startActivityForResult(intent, SchemaBuilder.REQUEST_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tru_form);
        try {
            FormModel formModel = getIntent().getParcelableExtra(JSON_KEY);
            assert formModel != null;
            String jsonVal = formModel.getJsonStr();
            if (TextUtils.isEmpty(jsonVal))
                truFormView = SchemaBuilder.getInstance().buildSchemaView(Objects.requireNonNull(getIntent().getExtras()).getString(SCHEMA_KEY), this);
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

    public void openImagePicker(TruConsumer<ImageModel> pickedImageListener) {
        this.mPickedImageListener = pickedImageListener;

        if (Build.VERSION.SDK_INT >= 23)
            if (PermissionsUtils.checkPermission(TruFormActivity.this))
                pickFromGallery();
            else if (PermissionsUtils.requestPermission(TruFormActivity.this))
                Toast.makeText(TruFormActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
            else
                ActivityCompat.requestPermissions(TruFormActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        else
            pickFromGallery();
    }

    private void pickFromGallery() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(R.layout.bottom_dialog);

        ImageView ivCameraSelect = dialog.findViewById(R.id.iv_camera);
        ImageView ivGallerySelect = dialog.findViewById(R.id.iv_gallery);

        assert ivCameraSelect != null;
        ivCameraSelect.setOnClickListener(view -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, CAPTURE_IMAGE_CODE);
                dialog.hide();
            }
        });

        assert ivGallerySelect != null;
        ivGallerySelect.setOnClickListener(view -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, PICK_IMAGE_CODE);
            dialog.hide();
        });

        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    pickFromGallery();
                else
                    Toast.makeText(TruFormActivity.this, "You should allow the permission to upload photo", Toast.LENGTH_SHORT).show();
                break;
        }
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
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PICK_IMAGE_CODE:
                    Uri pickedImage = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), pickedImage);
                        String imagePath = BitmapUtils.getRealPathFromURI(TruFormActivity.this, pickedImage);

                        ImageModel imageModel = new ImageModel();
                        imageModel.setImagePath(imagePath);
                        imageModel.setImageBitmap(bitmap);

                        mPickedImageListener.accept(imageModel);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case CAPTURE_IMAGE_CODE:
                    ImageModel imageModel = new ImageModel();
                    imageModel.setImagePath("");
                    imageModel.setImageBitmap((Bitmap) data.getExtras().get("data"));

                    mPickedImageListener.accept(imageModel);
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

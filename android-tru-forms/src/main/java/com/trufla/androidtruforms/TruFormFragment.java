package com.trufla.androidtruforms;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.trufla.androidtruforms.interfaces.FormContract;
import com.trufla.androidtruforms.interfaces.TruConsumer;
import com.trufla.androidtruforms.models.ImageModel;
import com.trufla.androidtruforms.truviews.SchemaBaseView;
import com.trufla.androidtruforms.truviews.TruFormView;
import com.trufla.androidtruforms.utils.BitmapUtils;
import com.trufla.androidtruforms.utils.EnumDataFormatter;
import com.trufla.androidtruforms.utils.PermissionsUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.Callback;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TruFormFragment.OnFormActionsListener} interface
 * to handle interaction events.
 * Use the {@link TruFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class TruFormFragment extends Fragment implements FormContract {
    private static final int PICK_IMAGE_CODE = 1;
    private static final int CAPTURE_IMAGE_CODE = 2;
    private static final int PERMISSION_REQUEST_CODE = 1;

    private OnFormActionsListener mListener;
    private static final String SCHEMA_KEY = "SCHEMA_KEY";
    private static final String JSON_KEY = "JSON_VALUE";
    private static final String SCHEMA_TYPE = "schema_type";
    private TruFormView truFormView;
    TruConsumer<ImageModel> mPickedImageListener;
    TruConsumer<ArrayList<Pair<Object, String>>> mDataFetchListener;
    ProgressDialog progressDialog;
    private String schemaString;
    public static final String FRAGMENT_TAG = "TRU_FORM_FRAGMENT";
    public static int mySchemaType = 0;

    private static SharedData sharedData;

    public TruFormFragment() {

    }

    public static TruFormFragment newInstance(int schemaType, String schemaString) {
        TruFormFragment fragment = new TruFormFragment();
        Bundle args = new Bundle();
        args.putString(SCHEMA_KEY, schemaString);
        args.putInt(SCHEMA_TYPE, schemaType);
        sharedData = null;
        fragment.setArguments(args);
        return fragment;
    }

    public static TruFormFragment newInstanceWithConstJson(String schemaString, String jsonValue) {
        TruFormFragment fragment = new TruFormFragment();
        Bundle args = new Bundle();

        args.putString(SCHEMA_KEY, schemaString);
//        args.putString(JSON_KEY, jsonValue);

        sharedData = SharedData.getInstance();
        sharedData.setData(jsonValue);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            schemaString = getArguments().getString(SCHEMA_KEY);
            mySchemaType = getArguments().getInt(SCHEMA_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tru_form, container, false);
        try {

            String jsonVal = "";

            if (sharedData != null) {
                jsonVal = sharedData.getData();
            }

            if (TextUtils.isEmpty(jsonVal))
                truFormView = SchemaBuilder.getInstance().buildSchemaView(schemaString, getContext());
            else {
                truFormView = SchemaBuilder.getInstance().buildSchemaViewWithConstValues(schemaString, jsonVal, getContext());
                rootView.findViewById(R.id.submit_btn).setVisibility(View.GONE);
            }

            View formView = truFormView.build();
            ((LinearLayout) rootView.findViewById(R.id.form_container)).addView(formView);
            rootView.findViewById(R.id.submit_btn).setOnClickListener((v) -> onSubmitClicked());

        } catch (Exception ex) {
            mListener.onFormFailed();
            ex.printStackTrace();
        }
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFormActionsListener) {
            mListener = (OnFormActionsListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void openImagePicker(TruConsumer<ImageModel> pickedImageListener) {
        this.mPickedImageListener = pickedImageListener;

        if (Build.VERSION.SDK_INT >= 23)
            if (PermissionsUtils.checkPermission(getActivity()))
                pickFromGallery();
            else if (PermissionsUtils.requestPermission(getActivity()))
                Toast.makeText(getActivity(), "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
            else
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        else
            pickFromGallery();
    }

    private void pickFromGallery() {
        BottomSheetDialog dialog = new BottomSheetDialog(Objects.requireNonNull(getContext()));
        dialog.setContentView(R.layout.bottom_dialog);

        ImageView ivCameraSelect = dialog.findViewById(R.id.iv_camera);
        ImageView ivGallerySelect = dialog.findViewById(R.id.iv_gallery);

        assert ivCameraSelect != null;
        ivCameraSelect.setOnClickListener(view -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
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
                    Toast.makeText(getActivity(), "You should allow the permission to upload photo", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onRequestData(TruConsumer<ArrayList<Pair<Object, String>>> listener, String selector, ArrayList<String> names, String url) {
        this.mDataFetchListener = listener;
        EnumDataFetcher fetcher = new EnumDataFetcher(mDataFetchListener, selector, names);
        fetcher.requestData(url, getHttpCallback(selector, names));
        showProgressDialog();
    }

    private void showProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing())
            return;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle(getString(R.string.loading));
        progressDialog.show();
    }

    private boolean isValidData() {
        return truFormView.getInputtedData() != null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PICK_IMAGE_CODE:
                    Uri pickedImage = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), pickedImage);
                        String imagePath = BitmapUtils.getRealPathFromURI(getActivity(), pickedImage);

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
        return new TruCallback(Objects.requireNonNull(getContext()).getApplicationContext()) {
            @Override
            public void onUIFailure(String message) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                Toast.makeText(getContext(), getString(R.string.cant_load_data) + message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUISuccess(String responseBody) {
                progressDialog.dismiss();
                mDataFetchListener.accept(EnumDataFormatter.getPairList(responseBody, selector, names));
            }
        };
    }

    public void onSubmitClicked() {
        if (!isValidData()) {
            Toast.makeText(getContext(), getString(R.string.please_correct_errors), Toast.LENGTH_SHORT).show();
            return;
        }
        //Toast.makeText(getContext(), "submitted", Toast.LENGTH_SHORT).show();
        String result = truFormView.getInputtedData();

        if (mListener != null) {

            ArrayList<SchemaBaseView> views = truFormView.getChilds();
            CollectDataAsync collectDataAsync = new CollectDataAsync(mListener, truFormView.getInstanceKey());
            collectDataAsync.execute(views.toArray(new SchemaBaseView[0]));

//            mListener.onFormSubmitted(result);
        }
    }

    public interface OnFormActionsListener {
        void onFormSubmitted(String jsonReperesentation);

        void onFormFailed();
    }
}

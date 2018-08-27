package com.trufla.androidtruforms;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.trufla.androidtruforms.databinding.FragmentFormBinding;
import com.trufla.androidtruforms.truviews.TruFormView;


public class FormFragment extends Fragment {
    public static final String JSON_SCHEMA_OBJECT = "JSON_SCHEMA_OBJECT";
    private OnFormSubmitListener mListener;
    private TruFormView truFormView;
    private FragmentFormBinding binding;

    public FormFragment() {
        // Required empty public constructor
    }

    public static FormFragment newInstance(String jsonStr) {
        FormFragment fragment = new FormFragment();
        Bundle args = new Bundle();
        args.putString(JSON_SCHEMA_OBJECT, jsonStr);
        fragment.setArguments(args);
        return fragment;
    }

    public void setListener(OnFormSubmitListener listener) {
        this.mListener = listener;
    }

    public void setFormView(TruFormView truFormView) {
        this.truFormView = truFormView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (truFormView == null)
            throw new RuntimeException("TruFormView must not be null to build the view");
        if (getArguments() != null) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mListener == null)
            throw new RuntimeException("OnFormSubmitListener must not be null");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_form, container, false);
        binding.setFormView(this);
        try {
            binding.formContainer.addView(truFormView.build());
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(getContext(), "Unable to create the form ... please check the schema", Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
        }
        return binding.getRoot();


    }


    public void onSubmitClicked() {
        //mListener.onFormSubmitted();
    }

    public interface OnFormSubmitListener {
        void onFormSubmitted(JsonObject formInputs);
    }
}

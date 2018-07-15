package com.trufla.androidtruforms;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;
import com.trufla.androidtruforms.truviews.TruFormView;


public class FormFragment extends Fragment {
    public static final String JSON_SCHEMA_OBJECT = "JSON_SCHEMA_OBJECT";
    private OnFormSubmitListener mListener;
    private TruFormView truFormView;

    public FormFragment() {
        // Required empty public constructor
    }
    public static FormFragment newInstance(String jsonStr) {
        FormFragment fragment = new FormFragment();
        Bundle args = new Bundle();
        args.putString(JSON_SCHEMA_OBJECT,jsonStr);
        fragment.setArguments(args);
        return fragment;
    }

    public void setListener(OnFormSubmitListener listener){
        this.mListener=listener;
    }
    public void setFormView(TruFormView truFormView){
        this.truFormView=truFormView;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(truFormView==null)
            throw new RuntimeException("TruFormView must not be null to build the view");
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnFormSubmitListener){
            mListener= (OnFormSubmitListener) context;
        }
        else
            throw new RuntimeException("TruFormView must not be null to build the view");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

    }


    public interface OnFormSubmitListener {
        void onFormSubmitted(JsonObject formInputs);
    }
}

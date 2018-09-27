package com.trufla.androidtruforms.truviews;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.TruFormFragment;
import com.trufla.androidtruforms.interfaces.FormContract;
import com.trufla.androidtruforms.models.SchemaInstance;
import com.trufla.androidtruforms.utils.TruUtils;

/**
 * Created by ohefny on 6/26/18.
 */

public abstract class SchemaBaseView<T extends SchemaInstance> {
    protected T instance;
    protected Context mContext;
    protected LayoutInflater layoutInflater;
    protected View mView;
    @LayoutRes
    protected int layoutId;
    protected TruObjectView parentView;

    public SchemaBaseView(Context context, T instance) {
        this.instance = instance;
        this.mContext = context;
        this.layoutInflater = LayoutInflater.from(context);
        layoutId = getLayoutId();
    }

    public SchemaBaseView(Context mContext, T instance, int layoutId) {
        this(mContext, instance);
        this.layoutId = layoutId;

    }

    public View build() {
        if (mView == null) {
            mView = layoutInflater.inflate(layoutId, null);
            onViewCreated();
        }
        return mView;
    }

    protected void onViewCreated() {
        setInstanceData();
    }

    public View attachView(ViewGroup parent, boolean attach) {
        if (mView == null) {
            mView = layoutInflater.inflate(layoutId, parent, attach);
            onViewCreated();
        }
        return mView;
    }

    public TruObjectView getParentView() {
        return parentView;
    }

    public void setParentView(TruObjectView parentView) {
        this.parentView = parentView;
    }

    protected abstract void setInstanceData();

    public abstract String getInputtedData();

    protected @LayoutRes
    abstract int getLayoutId();

    protected FormContract getFormContract(View v) {
        Activity hostActivity;
        if (mContext instanceof FormContract)
            return (FormContract) mContext;
        hostActivity = TruUtils.getHostActivity(v);
        if (hostActivity instanceof FormContract)
            return (FormContract) hostActivity;
        return getFragmentFormContract((AppCompatActivity) hostActivity);
    }

    private FormContract getFragmentFormContract(AppCompatActivity hostActivity) {
        FormContract contract = (FormContract) ((AppCompatActivity) mContext).getSupportFragmentManager().findFragmentByTag(TruFormFragment.FRAGMENT_TAG);
        if (contract != null)
            return contract;
        contract = (FormContract) hostActivity.getFragmentManager().findFragmentByTag(TruFormFragment.FRAGMENT_TAG);
        return contract;
    }

    public LinearLayout.LayoutParams getLayoutParams() {
        return new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public boolean validate() {
        if (!isValidAgainstRequired()) {
            setError(getRequiredErrorMessage());
            return false;
        }
        if (!isValidAgainstOtherRules()) {
            setError(getOtherRulesErrorMessage());
            return false;
        }
        removeErrorMsg();
        return true;
    }

    public boolean isValidAgainstRequired() {
        if (instance.isRequiredField())
            return isFilled();
        return true;
    }

    //can't name it against pattern cuz it only valid on numeric and string
    public boolean isValidAgainstOtherRules() {
        return true;
    }

    protected String getRequiredErrorMessage() {
        return mView.getResources().getString(R.string.required_field);
    }

    protected String getOtherRulesErrorMessage() {
        return mView.getResources().getString(R.string.general_invalid_input);
    }

    protected void setError(String error) {

    }

    protected boolean isFilled() {
        return true;
    }

    protected void removeErrorMsg() {

    }
}
package com.trufla.androidtruforms.truviews;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    protected TextView errView;

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
            buildSubview();
            onViewCreated();
        }
        return mView;
    }

    protected void buildSubview() {

    }

    @CallSuper
    protected void onViewCreated() {
        setInstanceData();
        if (instance.getConstItem() != null) {
            setNonEditableValues(instance.getConstItem());
        }
    }


    public View attachView(ViewGroup parent, boolean attach) {
        if (mView == null) {
            mView = layoutInflater.inflate(layoutId, parent, attach);
            onViewCreated();
        }
        return mView;
    }

    public void addAfterBuildConstItem(Object constItem) {
        instance.setConstItem(constItem);
        if (mView != null)
            setNonEditableValues(instance.getConstItem());
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

    private void setError(String error) {
        if (errView == null)
            errView = (TextView) layoutInflater.inflate(R.layout.tru_error_view, null);

        if (mView instanceof ViewGroup) {
            errView.setText(error);
            if (!isErrorViewAdded()) {
                ((ViewGroup) mView).addView(errView);
                parentView.mView.invalidate();
            }
        }
    }

    private boolean isErrorViewAdded() {
        return errView != null && ((ViewGroup) mView).indexOfChild(errView) > -1;
    }

    protected abstract boolean isFilled();

    private void removeErrorMsg() {
        if (isErrorViewAdded() && mView != null) {
            ((ViewGroup) mView).removeView(errView);
            parentView.mView.invalidate();
        }
    }

    protected void setNonEditableValues(Object constItem) {

    }
}
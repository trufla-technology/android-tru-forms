package com.trufla.androidtruforms.truviews;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.TruFormActivity;
import com.trufla.androidtruforms.exceptions.UnableToFindObjectProperties;
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

    public View attachView(ViewGroup parent) {
        if (mView == null) {
            mView = layoutInflater.inflate(layoutId, parent, true);
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

    protected TruFormActivity getTruFormHostActivity(View v) {
        Activity hostActivity;
        if (mContext instanceof TruFormActivity)
            return (TruFormActivity) mContext;
        hostActivity = TruUtils.getHostActivity(v);
        if (hostActivity instanceof TruFormActivity)
            return (TruFormActivity) hostActivity;
        return null;
    }

}

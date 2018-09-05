package com.trufla.androidtruforms.truviews;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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

    public View attachView(ViewGroup parent,boolean attach) {
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

    protected FormContract getTruFormHostActivity(View v) {
        Activity hostActivity;
        if (mContext instanceof FormContract)
            return (FormContract) mContext;
        hostActivity = TruUtils.getHostActivity(v);
        if (hostActivity instanceof FormContract)
            return (FormContract) hostActivity;
        return null;
    }
    public LinearLayout.LayoutParams getLayoutParams(){
        return new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

}

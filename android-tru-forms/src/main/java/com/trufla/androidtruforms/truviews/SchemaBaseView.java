package com.trufla.androidtruforms.truviews;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.models.SchemaInstance;

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
    public SchemaBaseView(Context context, T instance){
        this.instance=instance;
        this.mContext=context;
        this.layoutInflater=LayoutInflater.from(context);
        layoutId=getLayoutId();
    }

    public SchemaBaseView(Context mContext,T instance, int layoutId) {
       this(mContext,instance);
       this.layoutId= layoutId;

    }
    @CallSuper
    public  View build(){
        if(mView==null) {
            mView = layoutInflater.inflate(layoutId,null);
        }
        setInstanceData();
        return mView;
    }
    @CallSuper
    public  View attachView(ViewGroup parent){
        if(mView==null) {
            mView = layoutInflater.inflate(layoutId,parent,true);
        }
        setInstanceData();
        return mView;
    }
    protected abstract void setInstanceData();
    protected @LayoutRes abstract int getLayoutId();
}

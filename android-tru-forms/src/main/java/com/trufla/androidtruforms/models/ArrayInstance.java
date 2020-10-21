package com.trufla.androidtruforms.models;

import android.content.Context;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;
import com.trufla.androidtruforms.truviews.TruArrayView;

import java.util.ArrayList;

/**
 * Created by ohefny on 6/26/18.
 */

@Keep
public class ArrayInstance extends SchemaInstance {
    @SerializedName("items")
    protected SchemaInstance items;
    @SerializedName("maxItems")
    protected int maxItems;
    @SerializedName("minItems")
    protected int minItems;
    @SerializedName("uniqueItems")
    protected boolean uniqueItems;

    public ArrayInstance(){

    }

    @Override
    public Object getDefaultConst() {
        ArrayList<Object> defaultConst=new ArrayList<Object>();
        defaultConst.add(items.getDefaultConst());
        return defaultConst;
    }

    public ArrayInstance(ArrayInstance copyInstance) {
        super(copyInstance);
        this.items=copyInstance.items;
        this.maxItems=copyInstance.maxItems;
        this.minItems=copyInstance.minItems;
        this.uniqueItems=copyInstance.uniqueItems;
    }

    public SchemaInstance getItems() {
        return items;
    }

    public int getMaxItems() {
        return maxItems;
    }

    public int getMinItems() {
        return minItems;
    }

    public boolean isUniqueItems() {
        return uniqueItems;
    }

    @Override
    public TruArrayView getViewBuilder(Context context) {
        return new TruArrayView(context,this);
    }
}

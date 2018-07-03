package com.trufla.androidtruforms.models;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.trufla.androidtruforms.TruUtils;
import com.trufla.androidtruforms.truviews.SchemaBaseView;

import java.sql.Types;

/**
 * Created by ohefny on 6/26/18.
 */

public abstract class SchemaInstance implements Comparable<SchemaInstance>{
    //the key of the object ex: "date_of_loss":{} here we use data_of_loss as title
    @SerializedName("title")
    protected String title;
    @SerializedName("type")
    protected String type="";
    @SerializedName("const")
    protected Object constItem;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getPresentationTitle(){
        return TruUtils.removeUnderscoresAndCapitalize(title);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getConstItem() {
        return constItem;
    }

    public void setConstItem(Object constItem) {
        this.constItem = constItem;
    }
    public abstract <T extends SchemaBaseView> T getViewBuilder(Context context);

    @Override
    public int compareTo(@NonNull SchemaInstance o) {
        //instances then array then objects
        if(this.getType().equals(InstanceTypes.OBJECT))
            return 1;
        if(o.getType().equals(this.getType()))
            return 0;
        if(o.getType().equals(InstanceTypes.OBJECT))
            return -1;
        if(o.getType().equals(InstanceTypes.ARRAY))
            return -1;
        return 0;
    }
}

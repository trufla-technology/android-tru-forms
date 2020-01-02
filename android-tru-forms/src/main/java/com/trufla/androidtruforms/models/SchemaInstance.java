package com.trufla.androidtruforms.models;

import android.content.Context;
import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.trufla.androidtruforms.utils.TruUtils;
import com.trufla.androidtruforms.truviews.SchemaBaseView;

/**
 * Created by ohefny on 6/26/18.
 */

public abstract class SchemaInstance implements Comparable<SchemaInstance>, Cloneable {
    //the parsed key of the instance .. used in the returned data
    protected String key;
    //the key of the object ex: "date_of_loss":{} here we use data_of_loss as title
    @SerializedName("title")
    protected String title;
    @SerializedName("type")
    protected String type = "";
    @SerializedName("const")
    protected Object constItem;

    protected boolean requiredField;

    public SchemaInstance() {

    }

    public abstract Object getDefaultConst();

    public SchemaInstance(SchemaInstance instance) {
        this.key = instance.getKey();
        this.title = instance.getTitle();
        this.type = instance.getType();
        this.constItem = instance.getConstItem();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPresentationTitle() {
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
        if (this.getType().equals(SchemaKeywords.InstanceTypes.OBJECT))
            return 1;
        if (o.getType().equals(this.getType()))
            return 0;
        if (o.getType().equals(SchemaKeywords.InstanceTypes.OBJECT))
            return -1;
        if (o.getType().equals(SchemaKeywords.InstanceTypes.ARRAY))
            return -1;
        return 0;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isRequiredField() {
        return requiredField;
    }

    public void setRequiredField(boolean required) {
        this.requiredField = required;
    }

}

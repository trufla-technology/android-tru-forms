package com.trufla.androidtruforms.models;

import android.content.Context;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.trufla.androidtruforms.SharedData;
import com.trufla.androidtruforms.utils.TruUtils;
import com.trufla.androidtruforms.truviews.SchemaBaseView;

import java.util.List;

/**
 * Created by ohefny on 6/26/18.
 */

@Keep
public abstract class SchemaInstance implements Comparable<SchemaInstance>, Cloneable
{
    //the parsed key of the instance .. used in the returned data
    protected String key;

    @SerializedName("title")
    protected List<TitleInstance> title;

    @SerializedName("type")
    protected String type = "";

    @SerializedName("const")
    protected Object constItem;

    protected boolean requiredField;

    private static SharedData sharedData;

    public SchemaInstance() {}

    public abstract Object getDefaultConst();

    public SchemaInstance(SchemaInstance instance) {
        this.key = instance.getKey();
        this.title = instance.getTitle();
        this.type = instance.getType();
        this.constItem = instance.getConstItem();
    }

    public List<TitleInstance> getTitle()
    {
        return title;
    }

    public void setTitle(List<TitleInstance> title) {
        this.title = title;
    }

    public String getTittleValue()
    {
        sharedData = SharedData.getInstance();
        String myLanguage = sharedData.getDefaultLanguage();

        if(getTitle() != null)
        {
            for (TitleInstance titleInstance: getTitle())
            {
                if(titleInstance != null)
                {
                    if(titleInstance.getLanguage().equals(myLanguage))
                        return titleInstance.getTitleValue();
                }
            }
        }
        return "" ;
    }

    public String getPresentationTitle()
    {
        return TruUtils.removeUnderscoresAndCapitalize(getTittleValue());
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
    public int compareTo(@NonNull SchemaInstance o)
    {
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

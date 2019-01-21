package com.trufla.androidtruforms.models;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.trufla.androidtruforms.truviews.TruObjectView;
import com.trufla.androidtruforms.truviews.TruSectionView;
import com.trufla.androidtruforms.utils.TruUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by ohefny on 6/27/18.
 */

public class ObjectInstance extends SchemaInstance {
    @SerializedName("maxProperties")
    protected int maxProperties;
    @SerializedName("minProperties")
    protected int minProperties;
    @SerializedName("required")
    protected ArrayList<String> required;
    @SerializedName("properties")
    protected ObjectProperties properties;
    @SerializedName("description")
    protected String description;

    public ObjectInstance() {

    }

    public ObjectInstance(ObjectInstance copyInstance) {
        super(copyInstance);
        this.maxProperties = copyInstance.getMaxProperties();
        this.minProperties = copyInstance.getMinProperties();
        this.required = copyInstance.getRequired();
    }

    public int getMaxProperties() {
        return maxProperties;
    }

    public void setMaxProperties(int maxProperties) {
        this.maxProperties = maxProperties;
    }

    public int getMinProperties() {
        return minProperties;
    }

    public void setMinProperties(int minProperties) {
        this.minProperties = minProperties;
    }

    public ArrayList<String> getRequired() {
        return required;
    }

    public void setRequired(ArrayList<String> required) {
        this.required = required;

    }

    public ObjectProperties getProperties() {
        return properties;
    }

    public void setProperties(ObjectProperties properties) {
        this.properties = properties;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public TruObjectView getViewBuilder(Context context) {
        return new TruSectionView(context, this);
    }

    public void markRequiredFields() {
        if (!TruUtils.isNullOrEmpty(required)) {
            for (SchemaInstance child : properties.vals) {
                if (required.contains(child.key))
                    child.setRequiredField(true);
            }
        }
    }
}

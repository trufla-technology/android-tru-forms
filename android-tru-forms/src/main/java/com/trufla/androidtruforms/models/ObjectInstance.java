package com.trufla.androidtruforms.models;

import com.google.gson.annotations.SerializedName;
import com.trufla.androidtruforms.truviews.TruSectionView;

import java.util.ArrayList;

/**
 * Created by ohefny on 6/27/18.
 */

public class ObjectInstance extends SchemaInstance{
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
    public ObjectInstance(int maxProperties, int minProperties, ArrayList<String> required) {
        this.maxProperties = maxProperties;
        this.minProperties = minProperties;
        this.required = required;
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
    public TruSectionView getViewBuilder() {
        return new TruSectionView(this);
    }
}

package com.trufla.androidtruforms.SchemaModels;

import com.google.gson.annotations.SerializedName;
import com.trufla.androidtruforms.SchemaViews.SchemaBaseView;
import com.trufla.androidtruforms.SchemaViews.TruSectionView;

import java.util.ArrayList;

/**
 * Created by ohefny on 6/27/18.
 */

public class ObjectInstance extends SchemaInstance{
    protected int maxProperties;
    protected int minProperties;
    @SerializedName("required")
    protected ArrayList<String> required;
    @SerializedName("properties")
    protected ObjectProperties properties;
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

    @Override
    public TruSectionView getViewBuilder() {
        return new TruSectionView(this);
    }
}

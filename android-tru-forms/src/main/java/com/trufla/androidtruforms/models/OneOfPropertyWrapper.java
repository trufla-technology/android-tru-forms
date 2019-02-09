package com.trufla.androidtruforms.models;

import com.google.gson.annotations.SerializedName;

public class OneOfPropertyWrapper {
    @SerializedName("properties")
    private OneOfProperty property;

    public OneOfProperty getProperty() {
        return property;
    }

    public void setProperty(OneOfProperty property) {
        this.property = property;
    }

}

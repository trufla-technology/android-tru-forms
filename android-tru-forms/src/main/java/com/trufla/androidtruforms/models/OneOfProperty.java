package com.trufla.androidtruforms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OneOfProperty {
    @SerializedName("enum")
    private ArrayList<String> enums;
    private String key;
    private ArrayList<String> required = new ArrayList<>();

    public ArrayList<String> getEnums() {
        return enums;
    }

    public void setEnums(ArrayList<String> enums) {
        this.enums = enums;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ArrayList<String> getRequired() {
        return required;
    }

    public void setRequired(ArrayList<String> required) {
        if (required != null)
            this.required = required;

    }
}

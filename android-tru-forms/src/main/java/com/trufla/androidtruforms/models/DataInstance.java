package com.trufla.androidtruforms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DataInstance {
    @SerializedName("href")
    private final String url;
    @SerializedName("enum")
    private final String identifierColumn;
    @SerializedName("enumNames")
    private final DataEnumNames names;

    public DataInstance(String url, String identifierColumn, DataEnumNames names) {
        this.url = url;
        this.identifierColumn = identifierColumn;
        this.names = names;
    }

    public String getUrl() {
        return url;
    }

    public String getIdentifierColumn() {
        return identifierColumn;
    }

    public ArrayList<String> getNames() {
        return names.getNames();
    }

}

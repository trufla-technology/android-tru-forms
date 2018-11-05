package com.trufla.androidtruforms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DataInstance {
    @SerializedName("href")
    private String url;
    @SerializedName("enum")
    private String identifierColumn;
    @SerializedName("enumNames")
    DataEnumNames names;

    public DataInstance() {

    }

    public DataInstance(DataInstance copyInstance) {
        this(copyInstance.getUrl(), copyInstance.getIdentifierColumn(), copyInstance.names);
    }

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

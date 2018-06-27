package com.trufla.androidtruforms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ohefny on 6/27/18.
 */

public class SchemaDocument extends ObjectInstance {
    @SerializedName("id")
    protected String id;
    @SerializedName("$schema")
    protected String schemaUrl;
    @SerializedName("definitions")
    protected ObjectProperties definitions;
    public SchemaDocument(int maxProperties, int minProperties, ArrayList<String> required) {
        super(maxProperties, minProperties, required);
    }
}

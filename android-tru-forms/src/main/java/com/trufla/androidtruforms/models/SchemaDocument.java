package com.trufla.androidtruforms.models;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.trufla.androidtruforms.truviews.TruFormView;
import com.trufla.androidtruforms.truviews.TruObjectView;
import com.trufla.androidtruforms.truviews.TruSectionView;

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
    public SchemaDocument(){

    }
    public SchemaDocument(SchemaDocument copyInstance) {
        super(copyInstance);
        this.id = copyInstance.id;
        this.schemaUrl = copyInstance.schemaUrl;
        this.definitions = copyInstance.definitions;
    }

    @Override
    public SchemaDocument getCopy() {
        return new SchemaDocument(this);
    }

    @Override
    public TruFormView getViewBuilder(Context context) {
        return new TruFormView(context, this);
    }


}

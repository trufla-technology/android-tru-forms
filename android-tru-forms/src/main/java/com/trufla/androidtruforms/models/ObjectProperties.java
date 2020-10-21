package com.trufla.androidtruforms.models;

import androidx.annotation.Keep;

import com.trufla.androidtruforms.SchemaBuilder;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by ohefny on 6/27/18.
 */

@Keep
public class ObjectProperties {
    protected ArrayList<SchemaInstance> vals = new ArrayList<>();

    public ObjectProperties(ArrayList<SchemaInstance> properties) {
        if (!SchemaBuilder.getInstance().isDefaultOrdering())
            Collections.sort(properties);
        this.vals = properties;
    }

    public ArrayList<SchemaInstance> getVals() {
        return vals;
    }
}

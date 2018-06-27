package com.trufla.androidtruforms.schema_models;

import java.util.ArrayList;

/**
 * Created by ohefny on 6/27/18.
 */

public class ObjectProperties {
    protected ArrayList<SchemaInstance>vals=new ArrayList<>();

    public ObjectProperties(ArrayList<SchemaInstance> properties) {
        this.vals = properties;
    }

    public ArrayList<SchemaInstance> getVals() {
        return vals;
    }
}

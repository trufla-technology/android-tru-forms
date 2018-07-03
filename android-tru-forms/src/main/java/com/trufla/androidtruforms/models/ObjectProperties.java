package com.trufla.androidtruforms.models;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by ohefny on 6/27/18.
 */

public class ObjectProperties {
    protected ArrayList<SchemaInstance>vals=new ArrayList<>();

    public ObjectProperties(ArrayList<SchemaInstance> properties) {
       Collections.sort(properties);
       this.vals =properties;
    }

    public ArrayList<SchemaInstance> getVals() {
        return vals;
    }
}

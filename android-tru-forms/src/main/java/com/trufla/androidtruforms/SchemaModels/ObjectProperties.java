package com.trufla.androidtruforms.SchemaModels;

import java.util.ArrayList;

/**
 * Created by ohefny on 6/27/18.
 */

public class ObjectProperties {
    protected ArrayList<SchemaInstance>properties=new ArrayList<>();

    public ObjectProperties(ArrayList<SchemaInstance> properties) {
        this.properties = properties;
    }

    public ArrayList<SchemaInstance> getProperties() {
        return properties;
    }
}

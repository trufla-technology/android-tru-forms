package com.trufla.androidtruforms;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trufla.androidtruforms.SchemaModels.ObjectInstance;
import com.trufla.androidtruforms.SchemaModels.ObjectProperties;
import com.trufla.androidtruforms.SchemaModels.SchemaInstance;
import com.trufla.androidtruforms.SerializationAdapters.ObjectPropertiesAdapter;
import com.trufla.androidtruforms.SerializationAdapters.SchemaInstanceAdapter;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by ohefny on 6/27/18.
 */

public class TestProperJsonConversion
{
    @Test
    public void testObjectInstanceJson(){
        Gson gson=new GsonBuilder().registerTypeAdapter(SchemaInstance.class,new SchemaInstanceAdapter()).registerTypeAdapter(ObjectProperties.class,new ObjectPropertiesAdapter()).create();
        String jsonStr="{\"type\": \"object\",\"properties\": {\"first_name\": {\"type\": \"string\"},\"last_name\": {\"type\": \"string\"}},\"required\": [\"make\"]}";

        SchemaInstance schemaObjInstance=gson.fromJson(jsonStr,SchemaInstance.class);

        Assert.assertTrue(schemaObjInstance instanceof ObjectInstance);

    }

}

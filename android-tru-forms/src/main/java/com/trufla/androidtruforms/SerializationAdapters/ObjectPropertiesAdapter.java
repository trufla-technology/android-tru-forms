package com.trufla.androidtruforms.SerializationAdapters;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.trufla.androidtruforms.SchemaModels.ObjectInstance;
import com.trufla.androidtruforms.SchemaModels.ObjectProperties;
import com.trufla.androidtruforms.SchemaModels.SchemaInstance;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * Created by ohefny on 6/27/18.
 */

public class ObjectPropertiesAdapter implements JsonDeserializer<ObjectProperties> {
    @Override
    public ObjectProperties deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        ArrayList<SchemaInstance>schemaInstances=new ArrayList<>();
        for(String key:jsonObject.keySet()){
                schemaInstances.add(getPropertyItem(key,jsonObject.getAsJsonObject(key),context));
        }
        return new ObjectProperties(schemaInstances);
    }

    public SchemaInstance getPropertyItem(String key, JsonObject jsonObject,JsonDeserializationContext context){
        SchemaInstance schemaInstance=context.deserialize(jsonObject,SchemaInstance.class);
        schemaInstance.setTitle(key);
        return schemaInstance;
    }
}

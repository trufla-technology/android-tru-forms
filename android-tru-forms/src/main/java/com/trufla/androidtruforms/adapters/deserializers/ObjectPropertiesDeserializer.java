package com.trufla.androidtruforms.adapters.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.trufla.androidtruforms.models.ArrayInstance;
import com.trufla.androidtruforms.models.ObjectInstance;
import com.trufla.androidtruforms.models.ObjectProperties;
import com.trufla.androidtruforms.models.SchemaInstance;
import com.trufla.androidtruforms.utils.TruUtils;
import com.trufla.androidtruforms.utils.ValueToSchemaMapper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by ohefny on 6/27/18.
 */

public class ObjectPropertiesDeserializer implements JsonDeserializer<ObjectProperties> {
    @Override
    public ObjectProperties deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        ArrayList<SchemaInstance> schemaInstances = new ArrayList<>();
        for (String key : jsonObject.keySet()) {
            schemaInstances.add(getPropertyItem(key, jsonObject.getAsJsonObject(key), context));
        }
        return new ObjectProperties(schemaInstances);
    }

    public SchemaInstance getPropertyItem(String key, JsonObject jsonObject, JsonDeserializationContext context) {
        SchemaInstance schemaInstance = context.deserialize(jsonObject, SchemaInstance.class);
        if (TruUtils.isEmpty(schemaInstance.getTitle()))
            schemaInstance.setTitle(key);
        schemaInstance.setKey(key);
        return schemaInstance;
    }

}

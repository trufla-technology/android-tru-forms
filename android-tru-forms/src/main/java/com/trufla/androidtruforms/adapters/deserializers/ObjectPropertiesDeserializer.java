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
    HashMap<String, Object> constValues;

    public ObjectPropertiesDeserializer() {
    }

    public ObjectPropertiesDeserializer(String values) throws IOException {
        this.constValues = ValueToSchemaMapper.flattenJson(values);
    }

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
        setConstValueIfExist(schemaInstance);
        return schemaInstance;
    }

    private void setConstValueIfExist(SchemaInstance schemaInstance) {
        if (!(schemaInstance instanceof ObjectInstance) && constValues != null) {
            if (schemaInstance instanceof ArrayInstance)
                schemaInstance.setConstItem(getArrayConst(schemaInstance.getKey()));
            else
                schemaInstance.setConstItem(getPrimitiveConst(schemaInstance.getKey()));
        }
    }

    private ArrayList getArrayConst(String key) {
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<String, Object> entry : constValues.entrySet()) {
            int lastDotIdx = entry.getKey().lastIndexOf('.');
            int firstBraket = entry.getKey().indexOf('[');
            if (firstBraket < 0)
                continue;
            String lastPathSegment = entry.getKey().substring(lastDotIdx + 1, firstBraket);
            if (lastPathSegment.equals(key)) {
                arrayList.add(entry.getValue());
            }
        }
        return arrayList;
    }

    private Object getPrimitiveConst(String key) {
        for (Map.Entry<String, Object> entry : constValues.entrySet()) {
            if (entry.getKey().endsWith(key)) {
                return entry.getValue();
            }
        }
        return "";
    }
}

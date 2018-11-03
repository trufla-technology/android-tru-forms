package com.trufla.androidtruforms.adapters.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.trufla.androidtruforms.models.ArrayInstance;
import com.trufla.androidtruforms.models.ObjectInstance;
import com.trufla.androidtruforms.models.SchemaInstance;
import com.trufla.androidtruforms.utils.ValueToSchemaMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ObjectPropertiesDeserializerWithConstValues extends ObjectPropertiesDeserializer {
    HashMap<String, Object> constValues;

    public ObjectPropertiesDeserializerWithConstValues(String values) throws IOException {
        this.constValues = ValueToSchemaMapper.flattenJson(values);
    }

    @Override
    public SchemaInstance getPropertyItem(String key, JsonObject jsonObject, JsonDeserializationContext context) {
        SchemaInstance instance = super.getPropertyItem(key, jsonObject, context);
        setConstValueIfExist(instance);
        return instance;
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

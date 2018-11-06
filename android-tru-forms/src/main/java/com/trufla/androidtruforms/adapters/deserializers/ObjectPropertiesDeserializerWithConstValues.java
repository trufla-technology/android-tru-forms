package com.trufla.androidtruforms.adapters.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
        JsonObject jsonObject = new JsonParser().parse(values).getAsJsonObject();
        this.constValues = ValueToSchemaMapper.flatJsonObject(jsonObject);
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
                schemaInstance.setConstItem(ValueToSchemaMapper.getArrayConst(schemaInstance.getKey(),constValues));
            else
                schemaInstance.setConstItem(ValueToSchemaMapper.getPrimitiveConst(schemaInstance.getKey(),constValues));
        }
    }


}

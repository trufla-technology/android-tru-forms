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

public class ObjectPropertiesDeserializerWithConstValues extends ObjectPropertiesDeserializer {
    HashMap<String, Object> constValues;

    public ObjectPropertiesDeserializerWithConstValues(String values) throws IOException {
        JsonObject jsonObject = new JsonParser().parse(values).getAsJsonObject();
        this.constValues = ValueToSchemaMapper.flatJsonObject(jsonObject);
    }

    @Override
    public SchemaInstance getPropertyItem(String key, JsonObject jsonObject, JsonDeserializationContext context) {
        SchemaInstance instance = super.getPropertyItem(key, jsonObject, context);
        instance.setConstItem(getConstValueIfExist(instance));
        return instance;
    }

    private Object getConstValueIfExist(SchemaInstance schemaInstance) {
        Object pluggableConst=schemaInstance.getDefaultConst();
        if (!(schemaInstance instanceof ObjectInstance) && constValues != null) {
            if (schemaInstance instanceof ArrayInstance) {
                ArrayList arrayConst = ValueToSchemaMapper.getArrayConst(schemaInstance.getKey(), constValues);
                if (arrayConst.size() == 0)
                    pluggableConst = null;
                else pluggableConst = arrayConst;
            } else {
                pluggableConst = ValueToSchemaMapper.getPrimitiveConst(schemaInstance.getKey(), constValues);
            }
            if (pluggableConst == null)
                pluggableConst=schemaInstance.getDefaultConst();
        }
        return pluggableConst;
    }


}

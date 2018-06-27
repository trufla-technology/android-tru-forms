package com.trufla.androidtruforms.adapters.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.trufla.androidtruforms.schema_models.InstanceTypes;
import com.trufla.androidtruforms.schema_models.SchemaInstance;
import com.trufla.androidtruforms.TruUtils;

import java.lang.reflect.Type;

/**
 * Created by ohefny on 6/26/18.
 */

public class SchemaInstanceAdapter implements JsonDeserializer<SchemaInstance> {
    final String TYPE_KEY="type";
    final String STRING_INSTANCE_CLASS="com.trufla.androidtruforms.SchemaModels.StringInstance";
    final String NUMBER_INSTANCE_CLASS="com.trufla.androidtruforms.SchemaModels.NumericInstance";
    final String BOOLEAN_INSTANCE_CLASS="com.trufla.androidtruforms.SchemaModels.BooleanInstance";
    final String ARRAY_INSTANCE_CLASS="com.trufla.androidtruforms.SchemaModels.ArrayInstance";
    final String OBJECT_INSTANCE_CLASS="com.trufla.androidtruforms.SchemaModels.ObjectInstance";

    @Override
    public SchemaInstance deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonPrimitive prim = (JsonPrimitive) jsonObject.get(TYPE_KEY);
        String type = TruUtils.getText(prim.getAsString(),"No_Type");
        String className;

        Class<?> klass = null;
        switch (type){
            case InstanceTypes.ARRAY:
                className=ARRAY_INSTANCE_CLASS;
                break;
            case InstanceTypes.BOOLEAN:
                className=BOOLEAN_INSTANCE_CLASS;
                break;
            case InstanceTypes.STRING:
                className=STRING_INSTANCE_CLASS;
                break;
            case InstanceTypes.OBJECT:
                className=OBJECT_INSTANCE_CLASS;
                break;
            case InstanceTypes.NUMBER:
                className=NUMBER_INSTANCE_CLASS;
                break;
            default:
                throw new JsonParseException(String.format("this type is not supported %s",type));

        }
        try {
            klass=Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new JsonParseException(e.getMessage());

        }
        SchemaInstance schemaInstance= context.deserialize(json,klass);
        return schemaInstance;
    }


}

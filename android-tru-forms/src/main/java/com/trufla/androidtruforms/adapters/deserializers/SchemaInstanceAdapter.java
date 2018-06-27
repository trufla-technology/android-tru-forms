package com.trufla.androidtruforms.adapters.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.trufla.androidtruforms.models.ArrayInstance;
import com.trufla.androidtruforms.models.BooleanInstance;
import com.trufla.androidtruforms.models.InstanceTypes;
import com.trufla.androidtruforms.models.NumericInstance;
import com.trufla.androidtruforms.models.ObjectInstance;
import com.trufla.androidtruforms.models.SchemaInstance;
import com.trufla.androidtruforms.TruUtils;
import com.trufla.androidtruforms.models.StringInstance;

import java.lang.reflect.Type;

/**
 * Created by ohefny on 6/26/18.
 */

public class SchemaInstanceAdapter implements JsonDeserializer<SchemaInstance> {
    final String TYPE_KEY="type";
    final String STRING_INSTANCE_CLASS="com.trufla.androidtruforms.models.StringInstance";
    final String NUMBER_INSTANCE_CLASS="com.trufla.androidtruforms.models.NumericInstance";
    final String BOOLEAN_INSTANCE_CLASS="com.trufla.androidtruforms.models.BooleanInstance";
    final String ARRAY_INSTANCE_CLASS="com.trufla.androidtruforms.models.ArrayInstance";
    final String OBJECT_INSTANCE_CLASS="com.trufla.androidtruforms.models.ObjectInstance";
    //todo make this adapter accept custom classes types
    @Override
    public SchemaInstance deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonPrimitive prim = (JsonPrimitive) jsonObject.get(TYPE_KEY);
        String type = TruUtils.getText(prim.getAsString(),"No_Type");
        String className;

        Class<?> klass = null;
        switch (type){
            case InstanceTypes.ARRAY:
                klass= ArrayInstance.class;
                break;
            case InstanceTypes.BOOLEAN:
                klass= BooleanInstance.class;
                break;
            case InstanceTypes.STRING:
                klass= StringInstance.class;
                break;
            case InstanceTypes.OBJECT:
                klass= ObjectInstance.class;
                break;
            case InstanceTypes.NUMBER:
                klass= NumericInstance.class;
                break;
            default:
                throw new JsonParseException(String.format("this type is not supported %s",type));

        }
        return context.deserialize(json,klass);
    }


}
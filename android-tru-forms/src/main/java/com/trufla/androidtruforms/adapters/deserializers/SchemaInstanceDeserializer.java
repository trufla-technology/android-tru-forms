package com.trufla.androidtruforms.adapters.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.trufla.androidtruforms.models.EnumInstance;
import com.trufla.androidtruforms.models.ObjectInstance;
import com.trufla.androidtruforms.models.SchemaKeywords;
import com.trufla.androidtruforms.models.SchemaInstance;
import com.trufla.androidtruforms.utils.TruUtils;

import java.lang.reflect.Type;

import static com.trufla.androidtruforms.models.SchemaKeywords.InstanceTypes.NUMBER;
import static com.trufla.androidtruforms.models.SchemaKeywords.InstanceTypes.STRING;


/**
 * Created by ohefny on 6/26/18.
 */

public class SchemaInstanceDeserializer implements JsonDeserializer<SchemaInstance> {

    Class arrayInstanceClass;
    Class booleanInstanceClass;
    Class stringInstanceClass;
    Class numericInstanceClass;
    Class objectInstanceClass;

    public SchemaInstanceDeserializer(Class arrayInstanceClass, Class booleanInstanceClass, Class stringInstanceClass, Class numericInstanceClass, Class objectInstanceClass) {
        this.arrayInstanceClass = arrayInstanceClass;
        this.booleanInstanceClass = booleanInstanceClass;
        this.stringInstanceClass = stringInstanceClass;
        this.numericInstanceClass = numericInstanceClass;
        this.objectInstanceClass = objectInstanceClass;
    }


    @Override
    public SchemaInstance deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonPrimitive prim = (JsonPrimitive) jsonObject.get(SchemaKeywords.TYPE_KEY);
        String type = TruUtils.getText(prim.getAsString(), "No_Type");
        Class<?> klass = null;
        if (jsonObject.has(SchemaKeywords.ENUM_KEY) || jsonObject.has(SchemaKeywords.TruVocabulary.DATA)) {
            klass = EnumInstance.class;
            return getProperEnumInstance(json, context, type, klass);

        }
        klass = getInstanceClass(type);
        SchemaInstance instance = context.deserialize(json, klass);
        if (instance instanceof ObjectInstance)
            setObjectRequiredFields((ObjectInstance) instance);
        return instance;
    }

    private Class<?> getInstanceClass(String type) {
        Class<?> klass;
        switch (type) {
            case SchemaKeywords.InstanceTypes.ARRAY:
                klass = arrayInstanceClass;
                break;
            case SchemaKeywords.InstanceTypes.BOOLEAN:
                klass = booleanInstanceClass;
                break;
            case STRING:
                klass = stringInstanceClass;
                break;
            case SchemaKeywords.InstanceTypes.OBJECT:
                klass = objectInstanceClass;
                break;
            case SchemaKeywords.InstanceTypes.NUMBER:
                klass = numericInstanceClass;

                break;
            default:
                throw new JsonParseException(String.format("this type is not supported %s", type));

        }
        return klass;
    }

    private SchemaInstance getProperEnumInstance(JsonElement json, JsonDeserializationContext context, String type, Class<?> klass) {
        if (type.equals(STRING))
            return (EnumInstance<String>) context.deserialize(json, klass);
        else if (type.equals(NUMBER))
            return (EnumInstance<Double>) context.deserialize(json, klass);
        return context.deserialize(json, klass);
    }

    private void setObjectRequiredFields(ObjectInstance objInstance) {
        if (!(objInstance.getRequired() == null || objInstance.getRequired().isEmpty())) {
            for (SchemaInstance instance : objInstance.getProperties().getVals()) {
                instance.setRequiredField(objInstance.getRequired().contains(instance.getKey()));
            }
        }
    }

}

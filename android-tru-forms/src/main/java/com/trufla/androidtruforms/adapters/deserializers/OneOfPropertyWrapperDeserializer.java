package com.trufla.androidtruforms.adapters.deserializers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.trufla.androidtruforms.models.DataEnumNames;
import com.trufla.androidtruforms.models.OneOfProperty;
import com.trufla.androidtruforms.models.OneOfPropertyWrapper;
import com.trufla.androidtruforms.models.SchemaKeywords;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

public class OneOfPropertyWrapperDeserializer implements JsonDeserializer<OneOfPropertyWrapper> {
    @Override
    public OneOfPropertyWrapper deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        OneOfPropertyWrapper oneOfPropertyWrapper = new OneOfPropertyWrapper();
        OneOfProperty property = null;
        for (Map.Entry<String, JsonElement> propetyEntry : json.getAsJsonObject().get(SchemaKeywords.ONE_OF_PROPERTIES).getAsJsonObject().entrySet()) {
            property = context.deserialize(propetyEntry.getValue(), OneOfProperty.class);
            property.setKey(propetyEntry.getKey());
            property.setRequired(new Gson().fromJson(json.getAsJsonObject().get("required"), ArrayList.class));
        }
        oneOfPropertyWrapper.setProperty(property);
        return oneOfPropertyWrapper;
    }
}

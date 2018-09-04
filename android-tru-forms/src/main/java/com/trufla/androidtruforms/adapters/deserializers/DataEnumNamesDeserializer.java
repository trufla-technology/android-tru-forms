package com.trufla.androidtruforms.adapters.deserializers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.trufla.androidtruforms.models.DataEnumNames;

import org.json.JSONStringer;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DataEnumNamesDeserializer implements JsonDeserializer<DataEnumNames> {

    @Override
    public DataEnumNames deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ArrayList<String> names = new ArrayList<>();
        Gson gson = new Gson();
        if (json instanceof JsonArray) {
            names = gson.fromJson(json, names.getClass());
        } else if (json instanceof JsonPrimitive) {
            names.add(gson.fromJson(json, String.class));
        }
        return new DataEnumNames(names);
    }


}

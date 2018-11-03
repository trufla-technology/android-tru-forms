package com.trufla.androidtruforms.utils;

import android.util.Pair;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

public class ValueToSchemaMapper {

    public static String map(String schema, String schemaValue) throws IOException {
        String schemaWithValueAsConst = "";
        JsonParser parser = new JsonParser();
        JsonObject schemaObj = parser.parse(schema).getAsJsonObject();
        JsonObject schemaValueObj = parser.parse(schemaValue).getAsJsonObject();
        return schemaWithValueAsConst;
    }

    /*public static ArrayList<Pair<String, Object>> flatJsonObject(JsonObject jsonObject) {
        ArrayList<Pair<String, Object>> flatList = new ArrayList<>();
        for (Map.Entry<String, JsonElement> pair : jsonObject.entrySet()) {
            if (pair.getValue() instanceof JsonObject) {
                flatList.addAll(flatJsonObject(((JsonObject) pair.getValue()).getAsJsonObject()));
            } else if (pair.getValue() instanceof JsonArray) {
                flatList.add(new Pair<>(pair.getKey(), pair.getValue()));
            } else if (pair.getValue() instanceof JsonPrimitive) {

            }
        }
        return flatList;
    }*/
    public static HashMap<String, Object> flattenJson(String json) throws IOException {
        HashMap<String, Object> flatList = new HashMap<>();
        JsonReader reader = new JsonReader(new StringReader(json));
        reader.setLenient(true);
        while (true) {
            JsonToken token = reader.peek();
            switch (token) {
                case BEGIN_ARRAY:
                    reader.beginArray();
                    break;
                case END_ARRAY:
                    reader.endArray();
                    break;
                case BEGIN_OBJECT:
                    reader.beginObject();
                    break;
                case END_OBJECT:
                    reader.endObject();
                    break;
                case NAME:
                    reader.nextName();
                    break;
                case STRING:
                    String s = reader.nextString();
                    flatList.put(reader.getPath(), s);
                    break;
                case NUMBER:
                    Double n = reader.nextDouble();
                    flatList.put(reader.getPath(), n);
                    break;
                case BOOLEAN:
                    boolean b = reader.nextBoolean();
                    flatList.put(reader.getPath(), b);
                    break;
                case NULL:
                    reader.nextNull();
                    flatList.put(reader.getPath(), "");
                    break;
                case END_DOCUMENT:
                    return flatList;
            }
        }

    }
}

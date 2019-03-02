package com.trufla.androidtruforms.utils;

import android.util.Pair;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ValueToSchemaMapper {

    public static String map(String schema, String schemaValue) throws IOException {
        String schemaWithValueAsConst = "";
        JsonParser parser = new JsonParser();
        JsonObject schemaObj = parser.parse(schema).getAsJsonObject();
        JsonObject schemaValueObj = parser.parse(schemaValue).getAsJsonObject();
        return schemaWithValueAsConst;
    }

    public static HashMap<String, Object> flatJsonObject(JsonObject jsonObject) {
        HashMap<String, Object> flatList = new HashMap<String, Object>();
        for (Map.Entry<String, JsonElement> pair : jsonObject.entrySet()) {
            if (pair.getValue() instanceof JsonObject) {
                flatList.putAll(flatJsonObject(((JsonObject) pair.getValue())));
            } else if (pair.getValue() instanceof JsonArray) {
                for (int i = 0; i < ((JsonArray) pair.getValue()).size(); i++) {
                    JsonElement jsonElement = ((JsonArray) pair.getValue()).get(i);
                    if (jsonElement instanceof JsonObject)
                        flatList.put(pair.getKey() + "[" + i + "]", jsonElement.toString());
                    else {
                        flatList.put(pair.getKey() + "[" + i + "]", jsonVal2Obj(jsonElement.getAsJsonPrimitive()));

                    }
                }
                flatList.put(pair.getKey(), pair.getValue());
            } else if (pair.getValue() instanceof JsonPrimitive) {
                flatList.put(pair.getKey(), jsonVal2Obj(pair.getValue().getAsJsonPrimitive()));
            }
        }
        return flatList;
    }

    public static Object jsonVal2Obj(JsonPrimitive jsonValue) {
        if (jsonValue.isBoolean()) return jsonValue.getAsBoolean();
        if (jsonValue.isString()) return jsonValue.getAsString();
        if (jsonValue.isNumber()) {
            double v = jsonValue.getAsDouble();
            if (!Double.isNaN(v) && !Double.isInfinite(v) && v == Math.rint(v))
                return jsonValue.getAsLong();
            else
                return jsonValue.getAsDouble();
        }

        return null;
    }

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

    public static ArrayList getArrayConst(String key, HashMap<String, Object> constValues) {
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<String, Object> entry : constValues.entrySet()) {
            int idx = entry.getKey().indexOf("[");
            if (idx >= 0 && entry.getKey().substring(0, idx).equals(key))
                arrayList.add(entry.getValue());
        }
        return arrayList;
    }

    public static Object getPrimitiveConst(String key, HashMap<String, Object> constValues) {
        for (Map.Entry<String, Object> entry : constValues.entrySet()) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public static ArrayList getArrayConst(JsonArray asJsonArray) {
        ArrayList list = new ArrayList();
        for (int i = 0; i < asJsonArray.size(); i++) {
            if (asJsonArray.get(i).isJsonPrimitive()) {
                list.add(jsonVal2Obj(asJsonArray.get(i).getAsJsonPrimitive()));
            } else if (asJsonArray.get(i).isJsonObject()) {
                list.add(asJsonArray.get(i).getAsJsonObject());
            }
        }
        return list;
    }
}

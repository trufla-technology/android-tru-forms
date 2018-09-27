package com.trufla.androidtruforms.utils;

import android.util.Log;
import android.util.Pair;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Map;

public class EnumDataFormatter {
    public static ArrayList<Pair<Object, String>> getPairList(String string, String selector, ArrayList<String> names) {
        ArrayList<Pair<Object, String>> list = new ArrayList<>();
        JsonElement jsonElement;
        JsonArray jsonArray;
        try {
            jsonElement = new JsonParser().parse(string);
            jsonArray = jsonElement.isJsonArray() ? jsonElement.getAsJsonArray() : null;
            if (jsonArray == null && jsonElement.isJsonObject() && jsonElement.getAsJsonObject().has("data")) {
                jsonArray = jsonElement.getAsJsonObject().get("data").getAsJsonArray();
            } else {
                jsonArray = findArrayField(jsonElement);
            }
            for (JsonElement element : jsonArray) {
                list.add(getPairFromObject(element.getAsJsonObject(), selector, names));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;

    }

    private static JsonArray findArrayField(JsonElement jsonElement) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        for (Map.Entry<String, JsonElement> stringJsonElementEntry : jsonObject.entrySet()) {
            if (stringJsonElementEntry.getValue().isJsonArray())
                return stringJsonElementEntry.getValue().getAsJsonArray();
        }
        return null;
    }

    private static Pair<Object, String> getPairFromObject(JsonObject asJsonObject, String selector, ArrayList<String> names) {
        Object key = new Gson().fromJson(asJsonObject.getAsJsonPrimitive(selector), Object.class);
        String name = "";
        for (String n : names) {
            try {
                name += String.valueOf(new Gson().fromJson(asJsonObject.getAsJsonPrimitive(n), Object.class)) + ",";
            } catch (Exception e) {
                name += "N/A";
            }
        }
        if (name.length() > 0 && name.charAt(name.length() - 1) == ',') {
            name = name.substring(0, name.length() - 1);
        }
        return new Pair<>(key, name);
    }
}

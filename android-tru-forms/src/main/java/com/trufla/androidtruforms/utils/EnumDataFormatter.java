package com.trufla.androidtruforms.utils;

import android.util.Pair;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class EnumDataFormatter {
    public static ArrayList<Pair<Object, String>> getPairList(String string, String selector, ArrayList<String> names) {
        ArrayList<Pair<Object, String>> list = new ArrayList<>();
        JsonArray jsonArray = new JsonParser().parse(string).getAsJsonArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            list.add(getPairFromObject(jsonArray.get(i).getAsJsonObject(), selector, names));
        }
        return list;
    }

    private static Pair<Object, String> getPairFromObject(JsonObject asJsonObject, String selector, ArrayList<String> names) {
        Object key = new Gson().fromJson(asJsonObject.getAsJsonPrimitive(selector), Object.class);
        String name = "";
        for (String n : names) {
            name += String.valueOf(new Gson().fromJson(asJsonObject.getAsJsonPrimitive(n), Object.class)) + ",";
        }
        if (name.length() > 0 && name.charAt(name.length() - 1) == ',') {
            name = name.substring(0, name.length() - 1);
        }
        return new Pair<>(key, name);
    }
}

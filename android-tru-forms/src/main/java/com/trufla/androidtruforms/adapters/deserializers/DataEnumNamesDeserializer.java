package com.trufla.androidtruforms.adapters.deserializers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.trufla.androidtruforms.SharedData;
import com.trufla.androidtruforms.models.DataEnumNames;

import java.lang.reflect.Type;
import java.util.ArrayList;



public class DataEnumNamesDeserializer implements JsonDeserializer<DataEnumNames>
{
    @Override
    public DataEnumNames deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ArrayList<String> names = new ArrayList<>();
        Gson gson = new Gson();
        if (json instanceof JsonArray)
        {
            names = getEnumList(json);
//            names = gson.fromJson(json, names.getClass());

        } else if (json instanceof JsonPrimitive)
            names.add(gson.fromJson(json, String.class));

        return new DataEnumNames(names);
    }

    public ArrayList<String> getEnumList(JsonElement json)
    {
        SharedData sharedData = SharedData.getInstance();
        String myLanguage = sharedData.getDefaultLanguage();

        ArrayList<String> newEnumList = new ArrayList<>();
        JsonArray parentArray = json.getAsJsonArray();

        for (int x= 0; x<parentArray.size(); x++)
        {
            JsonArray childArray = parentArray.get(x).getAsJsonArray();
            for (int i=0; i<childArray.size(); i++)
            {
                JsonObject myObj = childArray.get(i).getAsJsonObject();

                String lang = myObj.get("language").getAsString();
                String value = myObj.get("value").getAsString();

                if(lang.equals(myLanguage))
                    newEnumList.add(value);
            }
        }
        return newEnumList;
    }
}

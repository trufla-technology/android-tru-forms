package com.trufla.androidtruforms;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trufla.androidtruforms.adapters.deserializers.SchemaInstanceDeserializer;
import com.trufla.androidtruforms.models.ObjectInstance;
import com.trufla.androidtruforms.models.ObjectProperties;
import com.trufla.androidtruforms.models.SchemaInstance;
import com.trufla.androidtruforms.models.StringInstance;
import com.trufla.androidtruforms.adapters.deserializers.ObjectPropertiesDeserializer;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by ohefny on 6/27/18.
 */

public class TestProperJsonConversion {
    Gson gson;
    StringBuilder claimsJson;

    @Before
    public void buildSetup() {
        gson = new GsonBuilder().registerTypeAdapter(SchemaInstance.class, new SchemaInstanceDeserializer()).registerTypeAdapter(ObjectProperties.class, new ObjectPropertiesDeserializer()).create();

        buildClaimsJson();

    }

    private void buildClaimsJson() {
        claimsJson = new StringBuilder();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("claims");
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNext()) {
            claimsJson.append(scanner.nextLine());
        }
    }

    @Test
    public void testObjectInstanceJson() {
        String jsonStr = "{\"type\": \"object\",\"properties\": {\"first_name\": {\"type\": \"string\"},\"last_name\": {\"type\": \"string\"}},\"required\": [\"make\"]}";

        SchemaInstance schemaObjInstance = gson.fromJson(jsonStr, SchemaInstance.class);

        Assert.assertTrue(schemaObjInstance instanceof ObjectInstance);

    }

    @Test
    public void testObjectPropertiesSize() {
        String jsonStr = "{\"type\": \"object\",\"properties\": {\"first_name\": {\"type\": \"string\"},\"last_name\": {\"type\": \"string\"}},\"required\": [\"make\"]}";

        ObjectInstance schemaObjInstance = gson.fromJson(jsonStr, ObjectInstance.class);

        Assert.assertEquals(2, schemaObjInstance.getProperties().getVals().size());

    }

    @Test
    public void testEnumValuesParsedCorrectly() {
        String jsonStr = "{\"type\": \"object\",\"properties\": {\"first_name\": {\"type\": \"string\",\"enum\":[\"Toms\",\"Noha\",\"George\"]},\"last_name\": {\"type\": \"string\"}},\"required\": [\"make\"]}";
        ObjectInstance schemaObjInstance = gson.fromJson(jsonStr, ObjectInstance.class);
        Assert.assertEquals("Toms", ((StringInstance) (schemaObjInstance.getProperties().getVals().get(0))).getEnumArray().get(0));
    }

    @Test
    public void testConstKeywordParsedToString() {
        String jsonStr = "{\"type\": \"object\",\"properties\": {\"first_name\": {\"type\": \"string\",\"enum\":[\"Toms\",\"Noha\",\"George\"]},\"last_name\": {\"type\": \"string\", \"const\":\"Toms\"}},\"required\": [\"make\"]}";
        ObjectInstance schemaObjInstance = gson.fromJson(jsonStr, ObjectInstance.class);
        System.out.println(String.format("%s :: %s", "const type", schemaObjInstance.getProperties().getVals().get(1).getConstItem().getClass().getSimpleName()));
        Assert.assertTrue(schemaObjInstance.getProperties().getVals().get(1).getConstItem() instanceof String);

    }

    @Test
    public void testConstKeywordParsedToNumber() {
        String jsonStr = "{\"type\": \"object\",\"properties\": {\"first_name\": {\"type\": \"string\",\"enum\":[\"Toms\",\"Noha\",\"George\"]},\"last_name\": {\"type\": \"string\", \"const\":4}},\"required\": [\"make\"]}";
        ObjectInstance schemaObjInstance = gson.fromJson(jsonStr, ObjectInstance.class);
        System.out.println(String.format("%s :: %s", "const type", schemaObjInstance.getProperties().getVals().get(1).getConstItem().getClass().getSimpleName()));
        Assert.assertTrue(schemaObjInstance.getProperties().getVals().get(1).getConstItem() instanceof Double);

    }
    @Test
    public void testClaimsJson() {
        SchemaInstance schemaObjInstance = gson.fromJson(claimsJson.toString(), SchemaInstance.class);
        System.out.println(String.format("%s :: %s", "form json", gson.toJson(schemaObjInstance).toString()));
        Assert.assertTrue(schemaObjInstance instanceof ObjectInstance);

    }

}

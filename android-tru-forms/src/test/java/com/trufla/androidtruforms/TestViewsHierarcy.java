package com.trufla.androidtruforms;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trufla.androidtruforms.adapters.deserializers.ObjectPropertiesAdapter;
import com.trufla.androidtruforms.adapters.deserializers.SchemaInstanceAdapter;
import com.trufla.androidtruforms.models.ObjectInstance;
import com.trufla.androidtruforms.models.ObjectProperties;
import com.trufla.androidtruforms.models.SchemaInstance;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by ohefny on 6/28/18.
 */

public class TestViewsHierarcy {
    Gson gson;
    StringBuilder claimsJson;

    @Before
    public void buildSetup() {
        gson = new GsonBuilder().registerTypeAdapter(SchemaInstance.class, new SchemaInstanceAdapter()).registerTypeAdapter(ObjectProperties.class, new ObjectPropertiesAdapter()).create();

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
    public void testClaimsJsonViews() {
        SchemaInstance schemaObjInstance = gson.fromJson(claimsJson.toString(), SchemaInstance.class);
        System.out.println(String.format("%s :: %s", "form json", gson.toJson(schemaObjInstance).toString()));
        Assert.assertTrue(schemaObjInstance instanceof ObjectInstance);

    }
}

package com.trufla.androidtruformssample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trufla.androidtruforms.adapters.deserializers.ObjectPropertiesAdapter;
import com.trufla.androidtruforms.adapters.deserializers.SchemaInstanceAdapter;
import com.trufla.androidtruforms.models.ObjectProperties;
import com.trufla.androidtruforms.models.SchemaDocument;
import com.trufla.androidtruforms.models.SchemaInstance;

import java.io.InputStream;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private StringBuilder claimsJson=new StringBuilder();
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();
        InputStream inputStream = getResources().openRawResource(R.raw.claims);
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNext()) {
            claimsJson.append(scanner.nextLine());
        }
        gson = new GsonBuilder().registerTypeAdapter(SchemaInstance.class, new SchemaInstanceAdapter()).registerTypeAdapter(ObjectProperties.class, new ObjectPropertiesAdapter()).create();
        SchemaDocument schemaObjInstance = gson.fromJson(claimsJson.toString(), SchemaDocument.class);
        View view=schemaObjInstance.getViewBuilder(this).build();
        ((LinearLayout)(findViewById(R.id.container))).addView(view);
    }
}

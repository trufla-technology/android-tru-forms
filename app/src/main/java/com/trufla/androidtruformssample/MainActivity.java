package com.trufla.androidtruformssample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.trufla.androidtruforms.FormFragment;
import com.trufla.androidtruforms.SchemaBuilder;
import com.trufla.androidtruforms.adapters.deserializers.ObjectPropertiesAdapter;
import com.trufla.androidtruforms.adapters.deserializers.SchemaInstanceAdapter;
import com.trufla.androidtruforms.models.ObjectProperties;
import com.trufla.androidtruforms.models.SchemaDocument;
import com.trufla.androidtruforms.models.SchemaInstance;

import java.io.InputStream;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements FormFragment.OnFormSubmitListener{

    private StringBuilder jsonStrBuilder = new StringBuilder();
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getExtras().getString(FistActivity.JSON_STR).isEmpty()) {
            Toast.makeText(this, "No Json Entered ... Form from claims.json will be built", Toast.LENGTH_LONG).show();
            InputStream inputStream = getResources().openRawResource(R.raw.claims_edited);
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNext()) {
                jsonStrBuilder.append(scanner.nextLine());
            }
        } else
            jsonStrBuilder.append(getIntent().getExtras().getString(FistActivity.JSON_STR));

        SchemaBuilder schemaBuilder=new SchemaBuilder(new JsonParser().parse(jsonStrBuilder.toString()).getAsJsonObject());
        schemaBuilder.showFragment(this,getSupportFragmentManager().beginTransaction(),R.id.container);


    }

    @Override
    public void onFormSubmitted(JsonObject formInputs) {

    }
}

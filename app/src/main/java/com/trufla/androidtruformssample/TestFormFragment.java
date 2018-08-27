package com.trufla.androidtruformssample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.trufla.androidtruforms.FormFragment;
import com.trufla.androidtruforms.SchemaBuilder;
import com.trufla.androidtruforms.exceptions.UnableToParseSchemaException;

import java.io.InputStream;
import java.util.Scanner;

public class TestFormFragment extends AppCompatActivity implements FormFragment.OnFormSubmitListener{

    private StringBuilder jsonStrBuilder = new StringBuilder();
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_form_fragment_activity);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getExtras().getString(TestFormActivity.JSON_STR).isEmpty()) {
            Toast.makeText(this, "No Json Entered ... Form from claims.json will be built", Toast.LENGTH_LONG).show();
            InputStream inputStream = getResources().openRawResource(R.raw.claims_edited);
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNext()) {
                jsonStrBuilder.append(scanner.nextLine());
            }
        } else
            jsonStrBuilder.append(getIntent().getExtras().getString(TestFormActivity.JSON_STR));

        SchemaBuilder schemaBuilder=new SchemaBuilder();
        FormFragment frag = null;
        try {
            frag = schemaBuilder.buildSchemaFragment(jsonStrBuilder.toString(),this, this);
        } catch (UnableToParseSchemaException e) {
            e.printStackTrace();
            Toast.makeText(this, "Unable to create the form ... please check the schema", Toast.LENGTH_SHORT).show();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.container,frag).commit();
        //schemaBuilder.buildActivityForResult(this,jsonStrBuilder.toString());
    }

    @Override
    public void onFormSubmitted(JsonObject formInputs) {

    }
}

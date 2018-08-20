package com.trufla.androidtruformssample;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.trufla.androidtruforms.SchemaBuilder;

import java.io.InputStream;
import java.util.Scanner;

public class FistActivity extends AppCompatActivity {

    public static final String JSON_STR = "JSON_STR";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fist);

    }

    public FistActivity getFistActivity() {
        return this;
    }

    public void onParseClick(View view) {
        StringBuilder jsonStringBuilder = new StringBuilder();
        String js = ((EditText) findViewById(R.id.et)).getText().toString().trim();
        if (TextUtils.isEmpty(js)) {
            Toast.makeText(this, "No Json Entered ... Form from claims.json will be built", Toast.LENGTH_LONG).show();
            InputStream inputStream = getResources().openRawResource(R.raw.claims_edited);
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNext()) {
                jsonStringBuilder.append(scanner.nextLine());
            }
        } else
            jsonStringBuilder.append(js);

        SchemaBuilder schemaBuilder = new SchemaBuilder();
        /*FormFragment frag = schemaBuilder.buildSchemaFragment(jsonStrBuilder.toString(),this, this);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,frag).commit();*/
        schemaBuilder.buildActivityForResult(this, jsonStringBuilder.toString(), 44);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==44){

        }
    }
}

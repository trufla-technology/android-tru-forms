package com.trufla.androidtruformssample;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.trufla.androidtruforms.SchemaBuilder;

public class FistActivity extends AppCompatActivity {

    public static final String JSON_STR = "JSON_STR";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fist);

    }

    public void onParseClick(View view) {
        String js=((EditText)findViewById(R.id.et)).getText().toString().trim();
        Intent intent=new Intent(this,MainActivity.class);
        intent.putExtra(JSON_STR,js);
        startActivity(intent);
    }
}

package com.trufla.androidtruformssample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.trufla.androidtruforms.SchemaBuilder;
import com.trufla.androidtruforms.TruFormFragment;
import com.trufla.androidtruforms.TruNavigationActivity;
import com.trufla.androidtruforms.exceptions.UnableToParseSchemaException;

import java.io.InputStream;
import java.util.Scanner;

import okhttp3.HttpUrl;
import okhttp3.Request;

public class TestFormActivity extends AppCompatActivity implements TruFormFragment.OnFormActionsListener {

    public static final String JSON_STR = "JSON_STR";

    enum FormType {FRAGMENT_FORM, ACTIVITY_FORM}

    FormType type = FormType.FRAGMENT_FORM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (type == FormType.FRAGMENT_FORM) {
            setContentView(R.layout.test_form_fragment_activity);
            fragmentFormParseClicked();
        } else
            setContentView(R.layout.test_form_activity);

    }

    public void onParseClick(View view) {
        activityFormParseClicked();

    }

    private void fragmentFormParseClicked() {
        StringBuilder jsonStringBuilder = new StringBuilder();
        String js = "";
        if (TextUtils.isEmpty(js)) {
            InputStream inputStream = getResources().openRawResource(R.raw.claims_edited);
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNext()) {
                jsonStringBuilder.append(scanner.nextLine());
            }
        } else
            jsonStringBuilder.append(js);

        SchemaBuilder schemaBuilder = SchemaBuilder.getInstance().allowDefaultOrder(true);
        schemaBuilder.getRequestBuilder().url("http://www.mocky.io/v2");
        try {
            schemaBuilder.showFragment(jsonStringBuilder.toString(), this, getSupportFragmentManager().beginTransaction(), R.id.container);
        } catch (UnableToParseSchemaException e) {
            e.printStackTrace();
        }

    }

    private void activityFormParseClicked() {
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

        SchemaBuilder schemaBuilder = SchemaBuilder.getInstance().allowDefaultOrder(true);
        schemaBuilder.getRequestBuilder().url("http://www.mocky.io/v2");
        schemaBuilder.buildActivityForResult(this, jsonStringBuilder.toString());
        //startActivity(new Intent(this, TruNavigationActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SchemaBuilder.REQUEST_CODE && resultCode == RESULT_OK) {
            String str = data.getStringExtra(SchemaBuilder.RESULT_DATA_KEY);
            ((TextView) findViewById(R.id.submitted_data)).setText(str);
            Log.d("Json values", str);
        }
    }


    @Override
    public void onFormSubmitted(String jsonReperesentation) {
        String str = jsonReperesentation;
        //((TextView) findViewById(R.id.submitted_data)).setText(str);
        Log.d("Json values", str);
    }

    @Override
    public void onFormFailed() {
        Toast.makeText(this, "Unable to create the form ... please check the schema", Toast.LENGTH_SHORT).show();
    }

}

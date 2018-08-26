package com.trufla.androidtruforms;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.trufla.androidtruforms.databinding.ActivityFormBinding;
import com.trufla.androidtruforms.truviews.TruFormView;

/**
 * Created by ohefny on 8/13/18.
 */

public class FormActivity extends AppCompatActivity {
    private static final String JSON_KEY="JSON_KEY";
    private static SchemaBuilder sSchemaBuilder;
    private StringBuilder jsonStrBuilder = new StringBuilder();
    private Gson gson;
    private TruFormView truFormView;

    public static void startActivityForFormResult(Activity context, String jsonStr, SchemaBuilder schemaBuilder) {
        Intent intent=new Intent(context,FormActivity.class);
        sSchemaBuilder=schemaBuilder;
        intent.putExtra(JSON_KEY,jsonStr);
        context.startActivityForResult(intent,SchemaBuilder.REQUEST_CODE);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFormBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_form);
        truFormView =sSchemaBuilder.buildSchemaView(getIntent().getExtras().getString(JSON_KEY),this);
        View formView= truFormView.build();
        binding.formContainer.addView(formView);
        binding.setFormView(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    public void onSubmitClicked() {
        if(!isValidData())
            return;
        Toast.makeText(this,"submitted",Toast.LENGTH_SHORT).show();
        String result=truFormView.getInputtedData();
        Intent intent=new Intent();
        intent.putExtra(SchemaBuilder.RESULT_DATA_KEY,result);
        setResult(RESULT_OK,intent);
        finish();
        
    }

    private boolean isValidData() {
        return true;
    }

}

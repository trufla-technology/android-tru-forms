package com.trufla.androidtruforms;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.trufla.androidtruforms.adapters.deserializers.ObjectPropertiesAdapter;
import com.trufla.androidtruforms.adapters.deserializers.SchemaInstanceAdapter;
import com.trufla.androidtruforms.exceptions.UnableToParseSchemaException;
import com.trufla.androidtruforms.models.ArrayInstance;
import com.trufla.androidtruforms.models.BooleanInstance;
import com.trufla.androidtruforms.models.NumericInstance;
import com.trufla.androidtruforms.models.ObjectInstance;
import com.trufla.androidtruforms.models.ObjectProperties;
import com.trufla.androidtruforms.models.SchemaDocument;
import com.trufla.androidtruforms.models.SchemaInstance;
import com.trufla.androidtruforms.models.StringInstance;
import com.trufla.androidtruforms.truviews.TruFormView;

public class SchemaBuilder {
    public final static int REQUEST_CODE = 333;
    public final static String RESULT_DATA_KEY = "SCHEMA_DATA_KEY";
    private String dateFormat = "yyyy-MM-dd";
    private String dateTimeFormat = "yyyy-MM-dd hh:mm:ss";
    private Class<ArrayInstance> arrayInstanceClass;
    private Class<BooleanInstance> booleanInstanceClass;
    private Class<StringInstance> stringInstanceClass;
    private Class<NumericInstance> numericInstanceClass;
    private Class<ObjectInstance> objectInstanceClass;
    private Gson gson;
    private static SchemaBuilder instance;

    private SchemaBuilder() {
        setDefaultSettings();
    }

    public static SchemaBuilder getInstance() {
        if (instance == null)
            instance = new SchemaBuilder();
        return instance;
    }

    public void restoreDefaultSettings() {
        setDefaultSettings();
    }

    private void setDefaultSettings() {
        dateFormat = "yyyy-MM-dd";
        dateTimeFormat = "yyyy-MM-dd hh:mm:ss";
        arrayInstanceClass = ArrayInstance.class;
        booleanInstanceClass = BooleanInstance.class;
        stringInstanceClass = StringInstance.class;
        numericInstanceClass = NumericInstance.class;
        objectInstanceClass = ObjectInstance.class;
        gson = new GsonBuilder().registerTypeAdapter(SchemaInstance.class, new SchemaInstanceAdapter(arrayInstanceClass,booleanInstanceClass,stringInstanceClass,numericInstanceClass,objectInstanceClass)).registerTypeAdapter(ObjectProperties.class, new ObjectPropertiesAdapter()).create();

    }

    /*public SchemaBuilder(Class<ArrayInstance> arrayInstanceClass, Class<BooleanInstance> booleanInstanceClass, Class<StringInstance> stringInstanceClass, Class<NumericInstance> numericInstanceClass, Class<ObjectInstance> objectInstanceClass) {
            this.arrayInstanceClass = arrayInstanceClass;
            this.booleanInstanceClass = booleanInstanceClass;
            this.stringInstanceClass = stringInstanceClass;
            this.numericInstanceClass = numericInstanceClass;
            this.objectInstanceClass = objectInstanceClass;
            gson = new GsonBuilder().registerTypeAdapter(SchemaInstance.class, new SchemaInstanceAdapter(arrayInstanceClass, booleanInstanceClass, stringInstanceClass, numericInstanceClass, objectInstanceClass)).registerTypeAdapter(ObjectProperties.class, new ObjectPropertiesAdapter()).create();
        }
    */
    public SchemaBuilder addArrayInstanceClass(Class<ArrayInstance> arrayInstanceClass) {
        this.arrayInstanceClass = arrayInstanceClass;
        return this;
    }

    public SchemaBuilder addBooleanInstanceClass(Class<BooleanInstance> booleanInstanceClass) {
        this.booleanInstanceClass = booleanInstanceClass;
        return this;
    }

    public SchemaBuilder addNumericInstanceClass(Class<NumericInstance> numericInstanceClass) {
        this.numericInstanceClass = numericInstanceClass;
        return this;
    }

    public SchemaBuilder addStringInstanceClass(Class<StringInstance> stringInstanceClass) {
        this.stringInstanceClass = stringInstanceClass;
        return this;
    }

    public SchemaBuilder addObjectInstanceClass(Class<ObjectInstance> objectInstanceClass) {
        this.objectInstanceClass = objectInstanceClass;
        return this;
    }

    public SchemaDocument buildSchema(String schemaString) throws UnableToParseSchemaException {
        SchemaDocument document = null;
        try {
            JsonObject jsonObj = new JsonParser().parse(schemaString.toString()).getAsJsonObject();
            document = gson.fromJson(jsonObj, SchemaDocument.class);
        } catch (Exception ex) {
            throw new UnableToParseSchemaException(ex);
        }
        return document;
    }

    public FormFragment buildSchemaFragment(String schemaString, Context context, FormFragment.OnFormSubmitListener submitListener) throws UnableToParseSchemaException {
        FormFragment formFragment = FormFragment.newInstance(schemaString.toString());
        formFragment.setFormView(buildSchemaView(schemaString, context));
        formFragment.setListener(submitListener);
        return formFragment;
    }

    public void showFragment(String schemaString, Activity hostActivity, FormFragment.OnFormSubmitListener submitListener, FragmentTransaction fragmentTransaction, @IdRes int containerViewId) throws UnableToParseSchemaException {
        FormFragment formFragment = buildSchemaFragment(schemaString, hostActivity, submitListener);
        fragmentTransaction.replace(containerViewId, formFragment).commit();
    }

    public void buildActivityForResult(Activity context, String schemaString) {
        FormActivity.startActivityForFormResult(context, schemaString, this);
    }

    public TruFormView buildSchemaView(String schemaString, Context context) throws UnableToParseSchemaException {
        return buildSchema(schemaString).getViewBuilder(context);
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public SchemaBuilder setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        return instance;
    }

    public String getDateTimeFormat() {
        return dateTimeFormat;
    }

    public SchemaBuilder setDateTimeFormat(String dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
        return instance;
    }

}

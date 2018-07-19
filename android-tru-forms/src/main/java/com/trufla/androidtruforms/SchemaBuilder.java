package com.trufla.androidtruforms;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.trufla.androidtruforms.adapters.deserializers.ObjectPropertiesAdapter;
import com.trufla.androidtruforms.adapters.deserializers.SchemaInstanceAdapter;
import com.trufla.androidtruforms.models.ArrayInstance;
import com.trufla.androidtruforms.models.BooleanInstance;
import com.trufla.androidtruforms.models.NumericInstance;
import com.trufla.androidtruforms.models.ObjectInstance;
import com.trufla.androidtruforms.models.ObjectProperties;
import com.trufla.androidtruforms.models.SchemaDocument;
import com.trufla.androidtruforms.models.SchemaInstance;
import com.trufla.androidtruforms.models.StringInstance;

public class SchemaBuilder {
    private final JsonObject schemaString;
    private final SchemaDocument schemaObjInstance;
    private Class<ArrayInstance> arrayInstanceClass=ArrayInstance.class;
    private Class<BooleanInstance>booleanInstanceClass=BooleanInstance.class;
    private Class<StringInstance>stringInstanceClass=StringInstance.class;
    private Class<NumericInstance>numericInstanceClass=NumericInstance.class;
    private Class<ObjectInstance>objectInstanceClass=ObjectInstance.class;
    private Gson gson;

    public SchemaBuilder(JsonObject schemaString){
        this.schemaString=schemaString;
        gson= new GsonBuilder().registerTypeAdapter(SchemaInstance.class, new SchemaInstanceAdapter()).registerTypeAdapter(ObjectProperties.class, new ObjectPropertiesAdapter()).create();
        schemaObjInstance = gson.fromJson(schemaString, SchemaDocument.class);
    }

    public SchemaBuilder(JsonObject schemaString, Class<ArrayInstance> arrayInstanceClass, Class<BooleanInstance> booleanInstanceClass, Class<StringInstance> stringInstanceClass, Class<NumericInstance> numericInstanceClass, Class<ObjectInstance> objectInstanceClass) {
        this.schemaString=schemaString;
        this.arrayInstanceClass = arrayInstanceClass;
        this.booleanInstanceClass = booleanInstanceClass;
        this.stringInstanceClass = stringInstanceClass;
        this.numericInstanceClass = numericInstanceClass;
        this.objectInstanceClass = objectInstanceClass;
        gson= new GsonBuilder().registerTypeAdapter(SchemaInstance.class, new SchemaInstanceAdapter(arrayInstanceClass,booleanInstanceClass,stringInstanceClass,numericInstanceClass,objectInstanceClass)).registerTypeAdapter(ObjectProperties.class, new ObjectPropertiesAdapter()).create();
        schemaObjInstance = gson.fromJson(schemaString, SchemaDocument.class);
    }
    public SchemaBuilder addArrayInstanceClass(Class<ArrayInstance> arrayInstanceClass){
        this.arrayInstanceClass=arrayInstanceClass;
        return this;
    }
    public SchemaBuilder addBooleanInstanceClass(Class<BooleanInstance> booleanInstanceClass){
        this.booleanInstanceClass=booleanInstanceClass;
        return this;
    }
    public SchemaBuilder addNumericInstanceClass(Class<NumericInstance> numericInstanceClass){
        this.numericInstanceClass=numericInstanceClass;
        return this;
    }
    public SchemaBuilder addStringInstanceClass(Class<StringInstance> stringInstanceClass){
        this.stringInstanceClass=stringInstanceClass;
        return this;
    }
    public SchemaBuilder addObjectInstanceClass(Class<ObjectInstance> objectInstanceClass){
        this.objectInstanceClass=objectInstanceClass;
        return this;
    }

    FormFragment buildFragment(Context context){
        FormFragment formFragment=FormFragment.newInstance(schemaString.toString());
        formFragment.setFormView(schemaObjInstance.getViewBuilder(context));
        return formFragment;
    }
    public <T extends Activity & FormFragment.OnFormSubmitListener>void showFragment(T hostActivity, FragmentTransaction fragmentTransaction, @IdRes int containerViewId){
        FormFragment formFragment=buildFragment(hostActivity);
        fragmentTransaction.replace(containerViewId,formFragment).commit();
    }
    void buildActivityForResult(int activityCode){

    }

}

package com.trufla.androidtruforms;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.trufla.androidtruforms.adapters.deserializers.DataEnumNamesDeserializer;
import com.trufla.androidtruforms.adapters.deserializers.ObjectPropertiesDeserializer;
import com.trufla.androidtruforms.adapters.deserializers.ObjectPropertiesDeserializerWithConstValues;
import com.trufla.androidtruforms.adapters.deserializers.OneOfPropertyWrapperDeserializer;
import com.trufla.androidtruforms.adapters.deserializers.SchemaInstanceDeserializer;
import com.trufla.androidtruforms.exceptions.UnableToParseSchemaException;
import com.trufla.androidtruforms.models.ArrayInstance;
import com.trufla.androidtruforms.models.BooleanInstance;
import com.trufla.androidtruforms.models.DataEnumNames;
import com.trufla.androidtruforms.models.NumericInstance;
import com.trufla.androidtruforms.models.ObjectInstance;
import com.trufla.androidtruforms.models.ObjectProperties;
import com.trufla.androidtruforms.models.OneOfPropertyWrapper;
import com.trufla.androidtruforms.models.SchemaDocument;
import com.trufla.androidtruforms.models.SchemaInstance;
import com.trufla.androidtruforms.models.StringInstance;
import com.trufla.androidtruforms.truviews.TruFormView;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

public class SchemaBuilder {
    public final static int REQUEST_CODE = 333;
    public final static String RESULT_DATA_KEY = "SCHEMA_DATA_KEY";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    public static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
    public static final String DEFAULT_DATE_TIME_FORMAT = DEFAULT_DATE_FORMAT + " hh:mm:ss";
    private final SchemaViews schemaViews = new SchemaViews();
    private String timeFormat;
    private String dateFormat;
    private String dateTimeFormat;
    private Class<ArrayInstance> arrayInstanceClass;
    private Class<BooleanInstance> booleanInstanceClass;
    private Class<StringInstance> stringInstanceClass;
    private Class<NumericInstance> numericInstanceClass;
    private Class<ObjectInstance> objectInstanceClass;
    private Gson gson;
    private Request.Builder requestBuilder;
    private OkHttpClient okHttpClient;
    private static SchemaBuilder instance;
    private boolean allowDefaultOrder;

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
        timeFormat = DEFAULT_TIME_FORMAT;
        dateFormat = DEFAULT_DATE_FORMAT;
        dateTimeFormat = DEFAULT_DATE_TIME_FORMAT;
        arrayInstanceClass = ArrayInstance.class;
        booleanInstanceClass = BooleanInstance.class;
        stringInstanceClass = StringInstance.class;
        numericInstanceClass = NumericInstance.class;
        objectInstanceClass = ObjectInstance.class;
        allowDefaultOrder = false;
        requestBuilder = new Request.Builder();
        okHttpClient = new OkHttpClient.Builder().addInterceptor(getLoggingInterceptor()).build();
        gson = new GsonBuilder().registerTypeAdapter(SchemaInstance.class, new SchemaInstanceDeserializer(arrayInstanceClass, booleanInstanceClass, stringInstanceClass, numericInstanceClass, objectInstanceClass)).
                registerTypeAdapter(ObjectProperties.class, new ObjectPropertiesDeserializer()).
                registerTypeAdapter(DataEnumNames.class, new DataEnumNamesDeserializer())
                .registerTypeAdapter(OneOfPropertyWrapper.class,new OneOfPropertyWrapperDeserializer()).create();
    }

    /*public SchemaBuilder(Class<ArrayInstance> arrayInstanceClass, Class<BooleanInstance> booleanInstanceClass, Class<StringInstance> stringInstanceClass, Class<NumericInstance> numericInstanceClass, Class<ObjectInstance> objectInstanceClass) {
            this.arrayInstanceClass = arrayInstanceClass;
            this.booleanInstanceClass = booleanInstanceClass;
            this.stringInstanceClass = stringInstanceClass;
            this.numericInstanceClass = numericInstanceClass;
            this.objectInstanceClass = objectInstanceClass;
            gson = new GsonBuilder().registerTypeAdapter(SchemaInstance.class, new SchemaInstanceDeserializer(arrayInstanceClass, booleanInstanceClass, stringInstanceClass, numericInstanceClass, objectInstanceClass)).registerTypeAdapter(ObjectProperties.class, new ObjectPropertiesDeserializer()).create();
        }
    */
    public SchemaBuilder arrayInstanceClass(Class<ArrayInstance> arrayInstanceClass) {
        this.arrayInstanceClass = arrayInstanceClass;
        return this;
    }

    public SchemaBuilder booleanInstanceClass(Class<BooleanInstance> booleanInstanceClass) {
        this.booleanInstanceClass = booleanInstanceClass;
        return this;
    }

    public SchemaBuilder numericInstanceClass(Class<NumericInstance> numericInstanceClass) {
        this.numericInstanceClass = numericInstanceClass;
        return this;
    }

    public SchemaBuilder stringInstanceClass(Class<StringInstance> stringInstanceClass) {
        this.stringInstanceClass = stringInstanceClass;
        return this;
    }

    public SchemaBuilder objectInstanceClass(Class<ObjectInstance> objectInstanceClass) {
        this.objectInstanceClass = objectInstanceClass;
        return this;
    }

    public SchemaBuilder networkRequestBuilder(Request.Builder builder) {
        this.requestBuilder = builder;
        return this;
    }

    public SchemaDocument buildSchema(String schemaString) throws UnableToParseSchemaException {
        SchemaDocument document = null;
        try {
            JsonObject jsonObj = new JsonParser().parse(schemaString).getAsJsonObject();
            document = gson.fromJson(jsonObj, SchemaDocument.class);
            document.markRequiredFields();
        } catch (Exception ex) {
            throw new UnableToParseSchemaException(ex);
        }
        return document;
    }

    public SchemaDocument buildSchemaWithConstVals(String schemaString, String values) throws UnableToParseSchemaException {
        SchemaDocument document = null;
        try {
            JsonObject jsonObj = new JsonParser().parse(schemaString).getAsJsonObject();
            Gson tempGson = this.gson.newBuilder().registerTypeAdapter(ObjectProperties.class, new ObjectPropertiesDeserializerWithConstValues(values)).create();
            document = tempGson.fromJson(jsonObj, SchemaDocument.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new UnableToParseSchemaException(ex);
        }
        return document;
    }
    public TruFormView buildSchemaView(String schemaString, Context context) throws UnableToParseSchemaException {
        return buildSchema(schemaString).getViewBuilder(context);
    }

    public TruFormView buildSchemaViewWithConstValues(String schemaString, String values, Context context) throws UnableToParseSchemaException {
        return buildSchemaWithConstVals(schemaString, values).getViewBuilder(context);
    }

    public SchemaBuilder allowDefaultOrder(boolean allowDefaultOrder) {
        this.allowDefaultOrder = allowDefaultOrder;
        return this;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public SchemaBuilder setTimeFormat(String timeFormat)
    {
        this.timeFormat = timeFormat;
        return instance;
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

    public Request.Builder getRequestBuilder() {
        return requestBuilder;
    }

    public boolean isDefaultOrdering() {
        return allowDefaultOrder;
    }

    public HttpLoggingInterceptor getLoggingInterceptor() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor((logMessage) -> Log.d("Network", logMessage));

        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        return loggingInterceptor;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public SchemaBuilder OkHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
        return instance;
    }
}

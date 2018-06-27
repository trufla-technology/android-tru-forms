package com.trufla.androidtruforms.adapters.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.trufla.androidtruforms.schema_models.SchemaDocument;

import java.lang.reflect.Type;

/**
 * Created by ohefny on 6/27/18.
 */

public class SchemaDocumentSerializer implements JsonSerializer<SchemaDocument> {
    @Override
    public JsonElement serialize(SchemaDocument src, Type typeOfSrc, JsonSerializationContext context) {
        return null;
    }
}

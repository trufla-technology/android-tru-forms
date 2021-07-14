package com.trufla.androidtruforms.models;

import androidx.annotation.Keep;

/**
 * Created by ohefny on 6/26/18.
 */

@Keep
public interface SchemaKeywords {
    String TYPE_KEY = "type";
    String ENUM_KEY = "enum";
    String FORMAT_KEY = "format";
    String CONST_KEY = "const";
    String ONE_OF_PROPERTIES = "properties";

    interface InstanceTypes {
        String NUMBER = "number", STRING = "string", BOOLEAN = "boolean", OBJECT = "object", ARRAY = "array";

    }

    interface StringFormats {
        String DATE_TIME = "datetime";
        String PHOTO = "photo";
        String MAP_LOCATION = "map_lat_long";
        String DATE = "date";
        String TIME = "time";
        String EMAIL = "email";
        String PHONE = "tel";
        String TEXT_AREA = "textarea";
    }

    interface TruVocabulary {
        String DATA = "_data";
    }
}

package com.trufla.androidtruforms.models;

/**
 * Created by ohefny on 6/26/18.
 */

public interface SchemaKeywords {
    String TYPE_KEY="type";
    String ENUM_KEY="enum";
    String FORMAT_KEY="format";
    interface InstanceTypes {
        String NUMBER = "number", STRING = "string", BOOLEAN = "boolean", OBJECT = "object", ARRAY = "array";

    }
    interface StringFormats{
        String DATE_TIME="datetime";
        String PHOTO="photo";
        String MAP_LOCATION="map_lat_long";
        String DATE ="date" ;
    }
}

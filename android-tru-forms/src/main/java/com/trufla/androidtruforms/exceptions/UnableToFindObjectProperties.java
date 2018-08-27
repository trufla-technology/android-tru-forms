package com.trufla.androidtruforms.exceptions;

public class UnableToFindObjectProperties extends UnableToParseSchemaException{
    public UnableToFindObjectProperties(String objectName) {
        super("unable to find properties field for the "+objectName+ " object");
    }
}

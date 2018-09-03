package com.trufla.androidtruforms.exceptions;

public class UnableToFindObjectPropertiesException extends UnableToParseSchemaException{
    public UnableToFindObjectPropertiesException(String objectName) {
        super("unable to find properties field for the "+objectName+ " object");
    }
}

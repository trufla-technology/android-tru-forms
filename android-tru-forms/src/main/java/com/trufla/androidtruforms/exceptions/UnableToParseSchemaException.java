package com.trufla.androidtruforms.exceptions;

public class UnableToParseSchemaException extends Exception{
    public UnableToParseSchemaException() {
        super();
    }

    public UnableToParseSchemaException(String message) {
        super(message);
    }

    public UnableToParseSchemaException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnableToParseSchemaException(Throwable cause) {
        this("This Json String Doesn't follow the recognized JSON Schema ...",cause);
    }

}

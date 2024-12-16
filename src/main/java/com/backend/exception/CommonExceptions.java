package com.backend.exception;

public class CommonExceptions extends Exception{

    public CommonExceptions(String message,Exception e) {
        super(message,e);
    }
    public CommonExceptions(String message) {
        super(message);
    }
    
}

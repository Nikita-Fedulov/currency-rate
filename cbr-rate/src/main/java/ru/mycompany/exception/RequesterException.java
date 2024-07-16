package ru.mycompany.exception;

public class RequesterException extends RuntimeException{
    public RequesterException (Throwable cause){
        super(cause);
    }
}

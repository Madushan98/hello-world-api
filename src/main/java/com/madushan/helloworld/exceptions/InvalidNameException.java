package com.madushan.helloworld.exceptions;

public class InvalidNameException extends RuntimeException {

    public InvalidNameException() {
        super("Invalid Input");
    }
}

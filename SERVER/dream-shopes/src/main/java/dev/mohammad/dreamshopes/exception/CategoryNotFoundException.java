package dev.mohammad.dreamshopes.exception;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException (String message) {
        super(message);
    }
}

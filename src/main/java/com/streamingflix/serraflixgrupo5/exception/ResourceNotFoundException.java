package com.streamingflix.serraflixgrupo5.exception;

public class ResourceNotFoundException extends RuntimeException {
    
	public ResourceNotFoundException(String message) {
        super(message);
    }
}

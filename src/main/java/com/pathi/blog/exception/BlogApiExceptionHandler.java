package com.pathi.blog.exception;

import org.springframework.http.HttpStatus;

public class BlogApiExceptionHandler extends RuntimeException{
    private HttpStatus httpStatus;
    private String message;

    public BlogApiExceptionHandler(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public BlogApiExceptionHandler(String message, HttpStatus httpStatus, String message1) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message1;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

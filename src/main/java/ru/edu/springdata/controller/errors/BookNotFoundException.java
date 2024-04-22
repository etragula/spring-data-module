package ru.edu.springdata.controller.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RestClientException {

    public BookNotFoundException() {
        super("Book doesn't exist.");
    }
}

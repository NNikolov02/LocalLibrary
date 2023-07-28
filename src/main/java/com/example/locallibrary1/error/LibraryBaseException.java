package com.example.locallibrary1.error;

import lombok.Getter;

import java.util.UUID;

@Getter
public class LibraryBaseException extends RuntimeException {

    private final UUID errorId;

    public LibraryBaseException(String message) {
        super(message);
        this.errorId = UUID.randomUUID();
    }
}
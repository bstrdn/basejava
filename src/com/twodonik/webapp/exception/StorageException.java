package com.twodonik.webapp.exception;

public class StorageException extends RuntimeException {
    private final String uuid;

    public StorageException(String uuid) {
        this.uuid = uuid;
    }
}

package com.example;

public class StorageFileNotFoundException extends StorageException {

    public StorageFileNotFoundException(String filename) {
        super("Could not find file: " + filename);
    }

    public StorageFileNotFoundException(String filename, Throwable cause) {
        super("Could not find file: " + filename, cause);
    }
}

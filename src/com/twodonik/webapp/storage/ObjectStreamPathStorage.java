package com.twodonik.webapp.storage;

public class ObjectStreamPathStorage extends AbstractPathStorage {
    public ObjectStreamPathStorage(String directory) {
        super(directory, new ObjectStreamStrategy());
    }
}

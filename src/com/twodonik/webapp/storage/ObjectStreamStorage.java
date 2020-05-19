package com.twodonik.webapp.storage;

import java.io.File;

public class ObjectStreamStorage extends AbstractFileStorage {
    public ObjectStreamStorage(File directory) {
        super(directory, new ObjectStreamStrategy());
    }
}

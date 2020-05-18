package com.twodonik.webapp.storage;

import com.twodonik.webapp.exception.StorageException;
import com.twodonik.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class ObjectStreamStorageTest extends AbstractStorageTest {

    public ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(STORAGE_DIR));
    }

}
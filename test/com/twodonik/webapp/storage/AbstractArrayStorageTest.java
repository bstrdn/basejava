package com.twodonik.webapp.storage;

import com.twodonik.webapp.exception.ExistStorageException;
import com.twodonik.webapp.exception.NotExistStorageException;
import com.twodonik.webapp.exception.StorageException;
import com.twodonik.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

public abstract class AbstractArrayStorageTest {
    private final Storage storage = newStorage();

    private static final String UUID_1 = "UUID_1";
    private static final String UUID_2 = "UUID_2";
    private static final String UUID_3 = "UUID_3";
    private static final String UUID_4 = "UUID_4";

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
        storage.save(new Resume(UUID_4));
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        try {
            storage.update(new Resume("UUID7_3"));
        } catch (NotExistStorageException e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("UUID_3333"));
    }

    @Test
    public void save() {
        for (int i = storage.size(); i <= 9999; i++) {
            storage.save(new Resume("uuid_" + i));
        }
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        for (int i = storage.size(); i <= 10000; i++) {
            storage.save(new Resume("uuid_" + i));
        }
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume("UUID_3"));
    }

    @Test
    public void get() {
        Assert.assertEquals("UUID_2", storage.get("UUID_2").toString());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("UUID_5");
    }

    @Test
    public void delete() {
        try {
            storage.delete("UUID_3");
        } catch (NotExistStorageException e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("UUID_1");
    }

    @Test
    public void getAll() {
        Assert.assertEquals(4, storage.size());
    }

    @Test
    public void size() {
        Assert.assertEquals(4, storage.size());
    }

    abstract Storage newStorage();
}
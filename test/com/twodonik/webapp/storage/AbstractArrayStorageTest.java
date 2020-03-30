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
    AbstractArrayStorage storage;

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
        Resume test1 = new Resume(UUID_1);
        storage.update(test1);
        Assert.assertEquals(test1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("UUID_3333"));
    }

    @Test
    public void save() {
        Resume test = new Resume("UUID_6");
        storage.save(test);
        Assert.assertEquals(test, storage.get("UUID_6"));
        if (storage.size() >= storage.STORAGE_LIMIT) {
            fail();
        }
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        try {
            for (int i = storage.size(); i < storage.STORAGE_LIMIT; i++) {
                storage.save(new Resume("uuid_" + i));
            }
        } catch (Exception a) {
            Assert.fail();
        }
        storage.save(new Resume("uuid_" + 10000));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void get() {
        Resume test = new Resume(UUID_2);
        Assert.assertEquals(test, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("UUID_5");
    }

    @Test
    public void delete() {
        int size = storage.size;
       storage.delete(UUID_2);
        if (storage.size() == size) {
            fail("Removal failed");
        }
        try {
            storage.get(UUID_2);
            fail("Removal failed");
        } catch (Exception e) {

        }
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("uuuu_3");
    }

    @Test
    public void getAll() {

        Resume[] resumes = storage.getAll();
        Assert.assertEquals(resumes, storage.getAll());
    }

    @Test
    public void size() {
        Assert.assertEquals(4, storage.size());
    }
}
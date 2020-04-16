package com.twodonik.webapp.storage;

import com.twodonik.webapp.exception.ExistStorageException;
import com.twodonik.webapp.exception.NotExistStorageException;
import com.twodonik.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {

    Storage storage;

    protected static final String UUID_1 = "UUID_1";
    protected static final String FULL_NAME_1 = "Ivanov Petr Sergeevich";
    protected static final String UUID_2 = "UUID_2";
    protected static final String FULL_NAME_2 = "Smirnov Denis Grigorevich";
    protected static final String UUID_3 = "UUID_3";
    protected static final String FULL_NAME_3 = "Smirnov Denis Grigorevich";
    protected static final String UUID_4 = "UUID_4";
    protected static final String FULL_NAME_4 = "Petrov Vasiliy Timofeevich";
    protected static final String UUID_5 = "UUID_5";
    protected static final String FULL_NAME_5 = "Sidorov Artur Denisovich";
    protected static final Resume RESUME_1 = new Resume(UUID_1, FULL_NAME_1);
    protected static final Resume RESUME_2 = new Resume(UUID_2, FULL_NAME_2);
    protected static final Resume RESUME_3 = new Resume(UUID_3, FULL_NAME_3);
    protected static final Resume RESUME_4 = new Resume(UUID_4, FULL_NAME_4);
    protected static final Resume RESUME_5 = new Resume(UUID_5, FULL_NAME_5);

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
        storage.save(RESUME_4);
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume test1 = new Resume(UUID_1, FULL_NAME_1);
        storage.update(test1);
        assertEquals(test1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_5);
    }

    @Test
    public void save() {
        storage.save(RESUME_5);
        assertEquals(RESUME_5, storage.get(UUID_5));
        if (storage.size() >= AbstractArrayStorage.STORAGE_LIMIT) {
            fail();
        }
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_3);
    }

    @Test
    public void get() {
        assertEquals(RESUME_2, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_5);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_2);
        assertEquals(3, storage.size());
        assertNotNull(storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_5);
    }

    @Test
    public void getAllSorted() {
        List<Resume> expectedResume = storage.getAllSorted();
        List<Resume> actualResume = new ArrayList<>();
        actualResume.add(RESUME_1);
        actualResume.add(RESUME_4);
        actualResume.add(RESUME_2);
        actualResume.add(RESUME_3);
        assertEquals(4, expectedResume.size());
        assertEquals(actualResume, expectedResume);
    }

    @Test
    public void size() {
        assertEquals(4, storage.size());
    }
}

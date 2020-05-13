package com.twodonik.webapp.storage;

import com.twodonik.webapp.ResumeTestData;
import com.twodonik.webapp.exception.ExistStorageException;
import com.twodonik.webapp.exception.NotExistStorageException;
import com.twodonik.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public abstract class AbstractStorageTest {

    Storage storage;

    protected static final String UUID_1 = "UUID_1";
    protected static final String UUID_2 = "UUID_2";
    protected static final String UUID_3 = "UUID_3";
    protected static final String UUID_4 = "UUID_4";
    protected static final String UUID_5 = "UUID_5";
    protected static final Resume RESUME_1 = ResumeTestData.getTestResume(UUID_1, "Ivanov Petr Sergeevich");
    protected static final Resume RESUME_2 = ResumeTestData.getTestResume(UUID_2, "Smirnov Denis Grigorevich");
    protected static final Resume RESUME_3 = ResumeTestData.getTestResume(UUID_3, "Smirnov Denis Grigorevich");
    protected static final Resume RESUME_4 = ResumeTestData.getTestResume(UUID_4, "Petrov Vasiliy Timofeevich");
    protected static final Resume RESUME_5 = ResumeTestData.getTestResume(UUID_5, "Sidorov Artur Denisovich");

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
        Resume test1 = new Resume(UUID_1, "Ivanov Petr Sergeevich");
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
        assertEquals(5, storage.size());
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
        List<Resume> actualResume = storage.getAllSorted();
        List<Resume> expectedResume = Arrays.asList(RESUME_1, RESUME_4, RESUME_2, RESUME_3);
        assertEquals(4, expectedResume.size());
        assertEquals(expectedResume, actualResume);
    }

    @Test
    public void size() {
        assertEquals(4, storage.size());
    }
}

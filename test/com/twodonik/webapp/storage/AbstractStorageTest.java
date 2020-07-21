package com.twodonik.webapp.storage;

import com.twodonik.webapp.Config;
import com.twodonik.webapp.ResumeTestData;
import com.twodonik.webapp.exception.ExistStorageException;
import com.twodonik.webapp.exception.NotExistStorageException;
import com.twodonik.webapp.model.ContactType;
import com.twodonik.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.twodonik.webapp.TestData.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = Config.get().getStorageDir();
    Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_4);
        storage.save(RESUME_1);
        storage.save(RESUME_3);
        storage.save(RESUME_2);
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume test1 = ResumeTestData.getTestResume2(UUID_1, "TEST");
//        Resume test1 = new Resume(UUID_1, "TEST");
        RESUME_1.addContact(ContactType.MAIL, "64645@dsf.ru");
        RESUME_1.addContact(ContactType.SKYPE, "64sdf645@ddsf.ru");
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
        List<Resume> expectedResume = Arrays.asList(RESUME_1, RESUME_2, RESUME_3, RESUME_4);
        Collections.sort(expectedResume);
        assertEquals(4, expectedResume.size());
        assertEquals(expectedResume, actualResume);
    }

    @Test
    public void size() {
        assertEquals(4, storage.size());
    }
}

package com.twodonik.webapp.storage;

import com.twodonik.webapp.model.Resume;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class MapStorageTest extends AbstractStorageTest {
    public MapStorageTest() {
        super(new MapStorage());
    }

    @Override
    public void getAll() {
        Resume[] expectedResume = storage.getAll();
        Resume[] actualResume = {RESUME_3, RESUME_2, RESUME_1, RESUME_4};
        assertEquals(4, storage.size());
        assertArrayEquals(actualResume, expectedResume);
    }
}

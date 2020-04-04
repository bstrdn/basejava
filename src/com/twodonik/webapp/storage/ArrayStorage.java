package com.twodonik.webapp.storage;

import com.twodonik.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {


    public ArrayStorage() {
        super(new Resume[STORAGE_LIMIT]);
    }

    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void deleteByIndex(int index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected void saveByIndex(Resume resume, int index) {
        storage[size] = resume;
    }

    Map<String, Resume> map = new HashMap<>();
}

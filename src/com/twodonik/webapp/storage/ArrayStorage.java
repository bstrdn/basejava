package com.twodonik.webapp.storage;

import com.twodonik.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveToStorage(Resume resume, int index) {
        storage[size] = resume;
    }

    @Override
    protected void deleteFromStorage(int index) {
        storage[index] = storage[size - 1];
    }

    protected int findKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}

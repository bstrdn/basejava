package com.twodonik.webapp.storage;

import com.twodonik.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage<Integer> {

    @Override
    protected void saveToStorage(Resume resume, Integer index) {
        storage[size] = resume;
    }

    @Override
    protected void deleteFromStorage(Integer index) {
        storage[index] = storage[size - 1];
    }

    protected Integer findKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}

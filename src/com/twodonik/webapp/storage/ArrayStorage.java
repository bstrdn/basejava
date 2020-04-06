package com.twodonik.webapp.storage;

import com.twodonik.webapp.exception.StorageException;
import com.twodonik.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveByIndex(Resume resume, int index) {
        if (size < 10000) {
        storage[size] = resume;
            size++;
        } else {
            throw new StorageException("Overflow", resume.getUuid());
        }
    }

    protected int findIndex(Resume resume) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(resume.getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected Resume findResume(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    @Override
    public void deleteByResume(Resume r) {
        storage[findIndex(r)] = storage[size - 1];
        size--;
    }

}

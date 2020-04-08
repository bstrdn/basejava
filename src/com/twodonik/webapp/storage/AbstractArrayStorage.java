package com.twodonik.webapp.storage;

import com.twodonik.webapp.exception.StorageException;
import com.twodonik.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected int size = 0;
    Resume[] storage = new Resume[STORAGE_LIMIT];


    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void updateByIndex(Resume resume, int index) {
        storage[index] = resume;
    }

    @Override
    protected void saveByIndex(Resume resume, int index) {
        if (size < 10000) {
            saveArray(resume, index);
            size++;
        } else {
            throw new StorageException("Overflow", resume.getUuid());
        }
    }

    @Override
    protected Resume getByIndex(int index) {
        return storage[index];
    }

    @Override
    protected void deleteByIndex(int index) {
        deleteArray(index);
        size--;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    protected abstract void saveArray(Resume resume, int index);

    protected abstract void deleteArray(int index);

}

package com.twodonik.webapp.storage;

import com.twodonik.webapp.exception.StorageException;
import com.twodonik.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected int size = 0;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void updateResume(Resume resume, Object index) {
        storage[(int) index] = resume;
    }

    @Override
    protected void saveResume(Resume resume, Object index) {
        if (size < STORAGE_LIMIT) {
            saveToStorage(resume, (int) index);
            size++;
        } else {
            throw new StorageException("Overflow", resume.getUuid());
        }
    }

    @Override
    protected Resume getResume(Object index) {
        return storage[(int) index];
    }

    @Override
    protected void deleteResume(Object index) {
        deleteFromStorage((int) index);
        size--;
    }

    @Override
    protected boolean isExist(Object index) {
        return ((Integer) index) >= 0;
    }

    @Override
    protected List<Resume> getList() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }


    public int size() {
        return size;
    }

    protected abstract void saveToStorage(Resume resume, int index);

    protected abstract void deleteFromStorage(int index);
}

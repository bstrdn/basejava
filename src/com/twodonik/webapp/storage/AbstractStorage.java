package com.twodonik.webapp.storage;

import com.twodonik.webapp.exception.ExistStorageException;
import com.twodonik.webapp.exception.NotExistStorageException;
import com.twodonik.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume resume) {
        updateResume(resume, getKeyIfExist(resume.getUuid()));
    }

    public void save(Resume resume) {
        int searchKey = findKey(resume.getUuid());
        if (searchKey >= 0) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            saveResume(resume, searchKey);
        }
    }

    public Resume get(String uuid) {
        return getResume(getKeyIfExist(uuid));
    }

    public void delete(String uuid) {
        deleteResume(getKeyIfExist(uuid));
    }

    protected abstract int findKey(String uuid);

    protected abstract void updateResume(Resume resume, int key);

    protected abstract void saveResume(Resume resume, int key);

    protected abstract Resume getResume(int key);

    protected abstract void deleteResume(int key);

    int getKeyIfExist(String uuid) {
        int searchKey = findKey(uuid);
        if (searchKey >= 0) {
            return searchKey;
        }
        throw new NotExistStorageException(uuid);
    }
}

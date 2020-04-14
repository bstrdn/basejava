package com.twodonik.webapp.storage;

import com.twodonik.webapp.exception.ExistStorageException;
import com.twodonik.webapp.exception.NotExistStorageException;
import com.twodonik.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume resume) {
        updateResume(resume, getKeyIfExist(resume.getUuid()));
    }

    public void save(Resume resume) {
        Object searchKey = findKey(resume.getUuid());
        if (ifExist(searchKey)) {
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

    protected abstract void updateResume(Resume resume, Object key);

    protected abstract void saveResume(Resume resume, Object key);

    protected abstract Resume getResume(Object key);

    protected abstract void deleteResume(Object key);

    protected abstract Object findKey(String uuid);

    protected abstract boolean ifExist(Object searchKey);

    Object getKeyIfExist(String uuid) {
        Object searchKey = findKey(uuid);
        if (ifExist(searchKey)) {
            return searchKey;
        }
        throw new NotExistStorageException(uuid);
    }
}

package com.twodonik.webapp.storage;

import com.twodonik.webapp.exception.ExistStorageException;
import com.twodonik.webapp.exception.NotExistStorageException;
import com.twodonik.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume resume) {
        updateResume(resume, getIndexIfExist(resume.getUuid()));
    }

    public void save(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            saveResume(resume, index);
        }
    }

    public Resume get(String uuid) {
        return getResume(getIndexIfExist(uuid));
    }

    public void delete(String uuid) {
        deleteResume(getIndexIfExist(uuid));
    }

    protected abstract int findIndex(String uuid);

    protected abstract void updateResume(Resume resume, int index);

    protected abstract void saveResume(Resume resume, int index);

    protected abstract Resume getResume(int index);

    protected abstract void deleteResume(int index);

    int getIndexIfExist(String uuid) {
        int searchKey = findIndex(uuid);
        if (searchKey >= 0) {
            return searchKey;
        }
        throw new NotExistStorageException(uuid);
    }
}

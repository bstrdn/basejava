package com.twodonik.webapp.storage;

import com.twodonik.webapp.exception.ExistStorageException;
import com.twodonik.webapp.exception.NotExistStorageException;
import com.twodonik.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume resume) {
        int index = findIndex(resume);
        if (index >= 0) {
            updateByIndex(resume, index);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    public void save(Resume resume) {
        int index = findIndex(resume);
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            saveByIndex(resume, index);
        }
    }

    public Resume get(String uuid) {
        Resume r = findResume(uuid);
        if (r != null) {
            return r;
        }
        throw new NotExistStorageException(uuid);
    }

    public void delete(String uuid) {
        Resume r = findResume(uuid);
        if (r != null) {
            deleteByResume(r);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract void updateByIndex(Resume resume, int index);

    protected abstract void saveByIndex(Resume resume, int index);

    protected abstract int findIndex(Resume resume);

    protected abstract Resume findResume(String uuid);

    protected abstract void deleteByResume(Resume r);

}

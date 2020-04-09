package com.twodonik.webapp.storage;

import com.twodonik.webapp.exception.ExistStorageException;
import com.twodonik.webapp.exception.NotExistStorageException;
import com.twodonik.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    private int index;

    public void update(Resume resume) {
        if (ifExist(resume.getUuid())) {
            updateResume(resume, index);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    public void save(Resume resume) {
        if (ifExist(resume.getUuid())) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            saveResume(resume, index);
        }
    }

    public Resume get(String uuid) {
        if (ifExist(uuid)) {
            return getResume(index);
        }
        throw new NotExistStorageException(uuid);
    }

    public void delete(String uuid) {
        if (ifExist(uuid)) {
            deleteResume(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract int findIndex(String uuid);

    protected abstract void updateResume(Resume resume, int index);

    protected abstract void saveResume(Resume resume, int index);

    protected abstract Resume getResume(int index);

    protected abstract void deleteResume(int index);

    boolean ifExist(String uuid) {
        index = findIndex(uuid);
        if (index >= 0) {
            return true;
        }
        return false;
    }
}

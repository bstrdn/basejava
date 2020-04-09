package com.twodonik.webapp.storage;

import com.twodonik.webapp.exception.ExistStorageException;
import com.twodonik.webapp.exception.NotExistStorageException;
import com.twodonik.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume resume) {
        toDo("update", resume);
    }

    public void save(Resume resume) {
        toDo("save", resume);
    }

    public Resume get(String uuid) {
        return toDo("get", new Resume(uuid));
    }

    public void delete(String uuid) {
        toDo("delete", new Resume(uuid));
    }

    protected abstract int findIndex(String uuid);

    protected abstract void updateResume(Resume resume, int index);

    protected abstract void saveResume(Resume resume, int index);

    protected abstract Resume getResume(int index);

    protected abstract void deleteResume(int index);

    protected Resume toDo(String action, Resume resume) {
        String uuid = resume.getUuid();
        int index = findIndex(uuid);
        if (index >= 0) {
            switch (action) {
                case "update":
                    updateResume(resume, index);
                    break;
                case "save":
                    throw new ExistStorageException(uuid);
                case "get":
                    return getResume(index);
                case "delete":
                    deleteResume(index);
                    break;
            }
        } else if (action.equals("save")) {
            saveResume(resume, index);
        } else {
            throw new NotExistStorageException(uuid);
        }
        return null;
    }
}

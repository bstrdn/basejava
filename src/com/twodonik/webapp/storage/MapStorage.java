package com.twodonik.webapp.storage;

import com.twodonik.webapp.exception.NotExistStorageException;
import com.twodonik.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    private Map<String, Resume> storage = new HashMap<>();

    @Override
    protected int findKey(String uuid) {
        if (storage.containsKey(uuid)) {
            return 1;
        }
        return -1;
    }

    @Override
    public Resume get(String uuid) {
        if (findKey(uuid) > 0) {
            return storage.get(uuid);
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        if (findKey(uuid) < 0) {
            storage.remove(uuid);
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    protected void updateResume(Resume resume, int key) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void saveResume(Resume resume, int key) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getResume(int key) {
        return null;
    }

    @Override
    protected void deleteResume(int key) {
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}

package com.twodonik.webapp.storage;

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
    protected void updateResume(Resume resume, int key) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void saveResume(Resume resume, int key) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getResume(int key, String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void deleteResume(int key, String uuid) {
        storage.remove(uuid);
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

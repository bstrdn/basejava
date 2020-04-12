package com.twodonik.webapp.storage;

import com.twodonik.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.abs;

public class MapStorage extends AbstractStorage {

    private Map<Integer, Resume> storage = new HashMap<>();

    @Override
    protected int findIndex(String uuid) {
        int key = uuid.hashCode();
        if (storage.containsKey(key)) {
            return abs(key);
        }
        return key;
    }

    @Override
    protected void updateResume(Resume resume, int index) {
        saveResume(resume, index);
    }

    @Override
    protected void saveResume(Resume resume, int index) {
        storage.put(index, resume);
    }

    @Override
    protected Resume getResume(int index) {
        return storage.get(-index);
    }

    @Override
    protected void deleteResume(int index) {
        storage.remove(-index);
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

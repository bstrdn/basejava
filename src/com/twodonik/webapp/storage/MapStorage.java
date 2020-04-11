package com.twodonik.webapp.storage;

import com.twodonik.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.abs;

public class MapStorage extends AbstractStorage {

    private Map<String, Resume> storage = new HashMap<>();

    @Override
    protected int findIndex(String uuid) {
        if (storage.containsKey(uuid)) {
            return abs(uuid.hashCode());
        }
        return -1;
    }

    @Override
    protected void updateResume(Resume resume, int index) {
        saveResume(resume, index);
    }

    @Override
    protected void saveResume(Resume resume, int index) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getResume(int index) {
        return storage.get(findUuid(index));
    }

    @Override
    protected void deleteResume(int index) {
        storage.remove(findUuid(index));
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

    String findUuid(int index) {
        for (String uuid : storage.keySet()) {
            if (abs(uuid.hashCode()) == index) {
                return uuid;
            }
        }
        return null;
    }
}

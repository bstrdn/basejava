package com.twodonik.webapp.storage;

import com.twodonik.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage {

    private Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Object findKey(String uuid) {
        if (storage.containsKey(uuid)) {
            return uuid;
        }
        return null;
    }

    @Override
    protected void updateResume(Resume resume, Object uuid) {
        storage.put((String) uuid, resume);
    }

    @Override
    protected void saveResume(Resume resume, Object uuid) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getResume(Object uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void deleteResume(Object uuid) {
        storage.remove(uuid);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected List<Resume> getList() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }
}

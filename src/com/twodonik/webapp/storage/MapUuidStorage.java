package com.twodonik.webapp.storage;

import com.twodonik.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage {

    private Map<String, Resume> map = new HashMap<>();

    @Override
    protected Object findKey(String uuid) {
        if (map.containsKey(uuid)) {
            return uuid;
        }
        return null;
    }

    @Override
    protected void updateResume(Resume resume, Object uuid) {
        map.put((String) uuid, resume);
    }

    @Override
    protected void saveResume(Resume resume, Object uuid) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getResume(Object uuid) {
        return map.get(uuid);
    }

    @Override
    protected void deleteResume(Object uuid) {
        map.remove(uuid);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    protected List<Resume> getList() {
        return new ArrayList<>(map.values());
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }
}

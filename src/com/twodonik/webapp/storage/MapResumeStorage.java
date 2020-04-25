package com.twodonik.webapp.storage;

import com.twodonik.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {

    private Map<String, Resume> map = new HashMap<>();

    @Override
    protected Resume findKey(String uuid) {
        return map.getOrDefault(uuid, null);
    }

    @Override
    protected void updateResume(Resume resume, Resume r) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected void saveResume(Resume resume, Resume r) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getResume(Resume r) {
        return r;
    }

    @Override
    protected void deleteResume(Resume r) {
        map.remove(r.getUuid());
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
    protected boolean isExist(Resume searchKey) {
        return searchKey != null;
    }

}

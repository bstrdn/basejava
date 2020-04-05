package com.twodonik.webapp.storage;

import com.twodonik.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Iterator;

public class ListStorage extends AbstractStorage {

    public ListStorage() {
        super(new ArrayList<Resume>());
    }

    public void clear() {
        storages.clear();
    }

    public void update(Resume resume) {
        storages.remove(resume);
        storages.add(resume);
    }

    public void save(Resume resume) {
        storages.add(resume);
    }

    public Resume get(String uuid) {
        return findResume(uuid);
    }

    public void delete(String uuid) {
        storages.remove(findResume(uuid));
    }

    public Resume findResume(String uuid) {
        Iterator<Resume> iterator = storages.iterator();
        while (iterator.hasNext()) {
            Resume r = iterator.next();
            if (r.getUuid().equals(uuid)) {
                return r;
            }
        }
        return null;
    }

    public Resume[] getAll() {
        return storages.toArray(new Resume[size()]);
    }

    @Override
    public int size() {
        return storages.size();
    }
}

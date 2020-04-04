package com.twodonik.webapp.storage;

import com.twodonik.webapp.model.Resume;

import java.util.Collection;
import java.util.Iterator;

public abstract class AbstractStorage implements Storage {

    protected Resume[] storage;
    protected Collection<Resume> storages;

    public AbstractStorage(Resume[] storage) {
        this.storage = storage;
    }

    public AbstractStorage(Collection<Resume> storages) {
        this.storages = storages;
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

    public int size() {
        return storages.size();
    }

}

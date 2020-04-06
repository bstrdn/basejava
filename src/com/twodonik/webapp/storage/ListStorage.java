package com.twodonik.webapp.storage;

import com.twodonik.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Iterator;

public class ListStorage extends AbstractStorage {

    protected ArrayList<Resume> storages = new ArrayList<>();


    public void update(Resume resume) {
        storages.remove(resume);
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
    public void clear() {
        storages.clear();
    }

    @Override
    public void save(Resume resume) {
        storages.add(resume);
    }

    @Override
    public int size() {
        return storages.size();
    }

    @Override
    protected int findIndex(Resume resume) {
        int i = storages.indexOf(resume);
        return i;
    }

    @Override
    protected void saveByIndex(Resume resume, int index) {
        storages.add(index, resume);
    }

    @Override
    protected void updateByIndex(Resume resume, int index) {
        storages.remove(index);
        storages.add(index, resume);
    }


    @Override
    protected void deleteByResume(Resume r) {
        storages.remove(r);
    }

}

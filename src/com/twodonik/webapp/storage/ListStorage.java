package com.twodonik.webapp.storage;

import com.twodonik.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private List<Resume> list = new ArrayList<>();

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    protected void updateResume(Resume resume, Object index) {
        list.set((int) index, resume);
    }

    @Override
    protected void saveResume(Resume resume, Object index) {
        list.add(resume);
    }

    @Override
    protected Resume getResume(Object index) {
        return list.get((int) index);
    }

    @Override
    protected void deleteResume(Object index) {
        list.remove((int) index);
    }

    @Override
    protected List<Resume> getList() {
        return list;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    protected Object findKey(String uuid) {
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        return -1;
    }

    @Override
    protected boolean isExist(Object index) {
        return ((Integer) index) >= 0;
    }
}

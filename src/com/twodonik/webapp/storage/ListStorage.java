package com.twodonik.webapp.storage;

import com.twodonik.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

    private List<Resume> list = new ArrayList<>();

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    protected void updateResume(Resume resume, Integer index) {
        list.set(index, resume);
    }

    @Override
    protected void saveResume(Resume resume, Integer index) {
        list.add(resume);
    }

    @Override
    protected Resume getResume(Integer index) {
        return list.get(index);
    }

    @Override
    protected void deleteResume(Integer index) {
        list.remove(index);
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
    protected Integer findKey(String uuid) {
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        return -1;
    }

    @Override
    protected boolean isExist(Integer index) {
        return index >= 0;
    }
}

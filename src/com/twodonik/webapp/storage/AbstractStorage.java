package com.twodonik.webapp.storage;

import com.twodonik.webapp.exception.ExistStorageException;
import com.twodonik.webapp.exception.NotExistStorageException;
import com.twodonik.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    static class FullNameComparator implements Comparator<Resume> {

        @Override
        public int compare(Resume r1, Resume r2) {
            return r1.getFullName().compareTo(r2.getFullName());
        }
    }

    static class UuidComparator implements Comparator<Resume> {

        @Override
        public int compare(Resume r1, Resume r2) {
            return r1.getUuid().compareTo(r2.getUuid());
        }
    }

    protected static Comparator<Resume> COMPARATOR = new FullNameComparator().thenComparing(new UuidComparator());

    public void update(Resume resume) {
        updateResume(resume, getKeyIfExist(resume.getUuid()));
    }

    public void save(Resume resume) {
        Object searchKey = findKey(resume.getUuid());
        if (isExist(searchKey)) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            saveResume(resume, searchKey);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = getList();
        list.sort(COMPARATOR);
        return list;
    }

    public Resume get(String uuid) {
        return getResume(getKeyIfExist(uuid));
    }

    public void delete(String uuid) {
        deleteResume(getKeyIfExist(uuid));
    }

    protected abstract void updateResume(Resume resume, Object key);

    protected abstract void saveResume(Resume resume, Object key);

    protected abstract Resume getResume(Object key);

    protected abstract void deleteResume(Object key);

    protected abstract Object findKey(String uuid);

    protected abstract boolean isExist(Object searchKey);

    protected abstract List<Resume> getList();

    Object getKeyIfExist(String uuid) {
        Object searchKey = findKey(uuid);
        if (isExist(searchKey)) {
            return searchKey;
        }
        throw new NotExistStorageException(uuid);
    }
}

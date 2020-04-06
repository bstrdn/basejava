package com.twodonik.webapp.storage;

import com.twodonik.webapp.exception.StorageException;
import com.twodonik.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveByIndex(Resume resume, int index) {
        if (size < 10000) {
            index = -(index) - 1;
            System.arraycopy(storage, index, storage, index + 1, size - index);
            storage[index] = resume;
            size++;
        } else {
            throw new StorageException("Overflow", resume.getUuid());
        }
    }

    @Override
    protected int findIndex(Resume resume) {
        Resume searchKey = new Resume(resume.getUuid());
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected Resume findResume(String uuid) {
        Resume searchKey = new Resume(uuid);
        int i = Arrays.binarySearch(storage, 0, size, searchKey);
        return (i < 0) ? null : storage[i];
    }

    @Override
    public void deleteByResume(Resume r) {
        int index = findIndex(r);
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
        size--;
    }
}

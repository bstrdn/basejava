package com.twodonik.webapp.storage;

import com.twodonik.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume resume) {
        int binary = -(Arrays.binarySearch(storage, 0, size, resume)) - 1;
        if (size == 0) {
            storage[0] = resume;
            size++;
        } else if (binary < 0) {
            System.out.println("Exist");
        } else {
            System.arraycopy(storage, binary, storage, binary + 1, size - binary);
            storage[binary] = resume;
            size++;
        }
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}

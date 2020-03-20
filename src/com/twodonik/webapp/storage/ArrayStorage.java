package com.twodonik.webapp.storage;

import com.twodonik.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void save(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            System.out.println("Resume " + storage[index] + " exists");
        } else if (size >= STORAGE_LIMIT) {
            System.out.println("Overflow");
        } else {
            storage[size] = resume;
            size++;
        }
    }

    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}

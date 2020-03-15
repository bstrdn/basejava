package com.twodonik.webapp.storage;

import com.twodonik.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int storageNumb = 0;

    public void clear() {
        Arrays.fill(storage, null);
        storageNumb = 0;
    }

    public void update(Resume resume) {
        if (findId(resume.getUuid()) >= 0) {
            storage[findId(resume.getUuid())] = resume;
            System.out.println("Resume updated");
        } else {
            System.out.println("Resume does not exist");
        }
    }

    public void save(Resume resume) {
        if (findId(resume.getUuid()) >= 0) {
            System.out.println("Resume exists");
        } else if (storageNumb >= 10000) {
            System.out.println("Overflow");
        } else {
            storage[storageNumb] = resume;
            storageNumb++;
        }
    }

    public Resume get(String uuid) {
        if (findId(uuid) >= 0) {
            return storage[findId(uuid)];
        } else {
            System.out.println("Resume does not exist");
            return null;
        }
    }

    public void delete(String uuid) {
        if (findId(uuid) >= 0) {
            storage[findId(uuid)] = storage[storageNumb - 1];
            storage[storageNumb - 1] = null;
            storageNumb--;
        } else {
            System.out.println("Resume does not exist");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, storageNumb);
    }

    public int size() {
        return storageNumb;
    }

    int findId(String uuid) {
        for (int i = 0; i < storageNumb; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;

    }
}

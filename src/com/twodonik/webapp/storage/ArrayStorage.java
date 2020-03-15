package com.twodonik.webapp.storage;

import com.twodonik.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10_000];
    private int storageNum = 0;

    public void clear() {
        Arrays.fill(storage, 0, storageNum, null);
        storageNum = 0;
    }

    public void update(Resume resume) {
        int findId = findId(resume.getUuid());
        if (findId >= 0) {
            storage[findId] = resume;
            System.out.println("Resume " + storage[findId] + " updated");
        } else {
            System.out.println("Resume " + resume + " does not exist");
        }
    }

    public void save(Resume resume) {
        int findId = findId(resume.getUuid());
        if (findId >= 0) {
            System.out.println("Resume " + storage[findId] + " exists");
        } else if (storageNum >= storage.length) {
            System.out.println("Overflow");
        } else {
            storage[storageNum] = resume;
            storageNum++;
        }
    }

    public Resume get(String uuid) {
        int findId = findId(uuid);
        if (findId >= 0) {
            return storage[findId];
        }
        System.out.println("Resume " + uuid + " does not exist");
        return null;
    }

    public void delete(String uuid) {
        int findId = findId(uuid);
        if (findId >= 0) {
            storage[findId] = storage[storageNum - 1];
            storage[storageNum - 1] = null;
            storageNum--;
        } else {
            System.out.println("Resume " + uuid + " does not exist");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, storageNum);
    }

    public int size() {
        return storageNum;
    }

    private int findId(String uuid) {
        for (int i = 0; i < storageNum; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}

package com.twodonik.webapp.storage;

import com.twodonik.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;


    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
            System.out.println("Resume " + storage[index] + " updated");
        } else {
            System.out.println("Resume " + resume + " does not exist");
        }
    }


    public void save(Resume resume) {
        int index = findIndex(resume.getUuid());

        if (size >= STORAGE_LIMIT) {
            System.out.println("Overflow");
        } else if (index >= 0) {
            System.out.println("Resume " + storage[index] + " exists");
        } else {
            saveByIndex(resume, index);
            size++;
        }

    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        System.out.println("Resume " + uuid + " does not exist");
        return null;
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            deleteByIndex(index);
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Resume " + uuid + " does not exist");
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    protected abstract int findIndex(String uuid);

    protected abstract void deleteByIndex(int index);

    protected abstract void saveByIndex(Resume resume, int index);

}

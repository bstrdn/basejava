package com.twodonik.webapp;

import com.twodonik.webapp.model.Resume;
import com.twodonik.webapp.storage.ListStorage;
import com.twodonik.webapp.storage.Storage;

public class MainCollections {
    private static final String UUID_1 = "UUID_1";
    private static final Resume RESUME_1 = new Resume(UUID_1);

    private static final String UUID_2 = "UUID_2";
    private static final Resume RESUME_2 = new Resume(UUID_2);

    private static final String UUID_3 = "UUID_3";
    private static final Resume RESUME_3 = new Resume(UUID_3);

    public static void main(String[] args) {

        Storage storage = new ListStorage();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
        storage.save(RESUME_3);

        storage.delete(UUID_3);

        Resume[] r = storage.getAll();
        for (Resume res : r) {
            System.out.println(res);
        }
        System.out.println("__________________");
        System.out.println(storage.size());
        System.out.println(storage.get(UUID_2));

        storage.clear();

        System.out.println(storage.size());
    }
}

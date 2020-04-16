package com.twodonik.webapp;

import com.twodonik.webapp.model.Resume;
import com.twodonik.webapp.storage.ListStorage;
import com.twodonik.webapp.storage.Storage;

import java.util.Arrays;
import java.util.List;

public class MainCollections {


    protected static final String UUID_1 = "UUID_1";
    protected static final String FULL_NAME_1 = "Ivanov Petr Sergeevich";
    protected static final String UUID_2 = "UUID_2";
    protected static final String FULL_NAME_2 = "Smirnov Denis Grigorevich";
    protected static final String UUID_3 = "UUID_3";
    protected static final String FULL_NAME_3 = "Smirnov Denis Grigorevich";
    protected static final String UUID_4 = "UUID_4";
    protected static final String FULL_NAME_4 = "Petrov Vasiliy Timofeevich";
    protected static final String UUID_5 = "UUID_5";
    protected static final String FULL_NAME_5 = "Sidorov Artur Denisovich";
    protected static final Resume RESUME_1 = new Resume(UUID_1, FULL_NAME_1);
    protected static final Resume RESUME_2 = new Resume(UUID_2, FULL_NAME_2);
    protected static final Resume RESUME_3 = new Resume(UUID_3, FULL_NAME_3);
    protected static final Resume RESUME_4 = new Resume(UUID_4, FULL_NAME_4);
    protected static final Resume RESUME_5 = new Resume(UUID_5, FULL_NAME_5);


    public static void main(String[] args) {

        Storage storage = new ListStorage();
        storage.save(RESUME_1);
        storage.save(RESUME_3);
        storage.save(RESUME_2);
        storage.save(RESUME_4);
        storage.save(RESUME_5);
        // storage.save(RESUME_3);

        //storage.delete(UUID_3);

        List<Resume> r = storage.getAllSorted();
        for (Resume res : r) {
            System.out.println(res.getUuid() + " " + res.getFullName());
        }
        System.out.println("__________________");
        System.out.println(storage.size());
        System.out.println(storage.get(UUID_2) + " " + storage.get(UUID_2).getFullName());

        storage.clear();

        System.out.println(storage.size());
    }
}

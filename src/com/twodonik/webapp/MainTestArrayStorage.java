package com.twodonik.webapp;

import com.twodonik.webapp.model.Resume;
import com.twodonik.webapp.storage.MapUuidStorage;
import com.twodonik.webapp.storage.Storage;

/**
 * Test for your com.twodonik.webapp.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final Storage ARRAY_STORAGE = new MapUuidStorage();

    public static void main(String[] args) {
        final Resume r1 = new Resume("R1");
        final Resume r2 = new Resume("R2");
        final Resume r3 = new Resume("R3");
        final Resume r4 = new Resume("R4");
        final Resume r5 = new Resume("R5");
        final Resume r6 = new Resume("R6");

        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r6);
        ARRAY_STORAGE.save(r5);
        ARRAY_STORAGE.save(r4);
        ARRAY_STORAGE.save(r3);

        ARRAY_STORAGE.update(r1);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        ARRAY_STORAGE.delete(r5.getUuid());
        ARRAY_STORAGE.delete("R2");
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}

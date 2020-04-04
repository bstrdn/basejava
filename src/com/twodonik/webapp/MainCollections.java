package com.twodonik.webapp;

import com.twodonik.webapp.model.Resume;

import java.util.*;

public class MainCollections {
    private static final String UUID_1 = "UUID_1";
    private static final Resume RESUME_1 = new Resume(UUID_1);

    private static final String UUID_2 = "UUID_2";
    private static final Resume RESUME_2 = new Resume(UUID_2);

    private static final String UUID_3 = "UUID_3";
    private static final Resume RESUME_3 = new Resume(UUID_3);

    public static void main(String[] args) {
        Collection<Resume> collection = new ArrayList<>();
        collection.add(RESUME_1);
        collection.add(RESUME_2);
        collection.add(RESUME_3);

        for (Resume resume : collection) {
     //       System.out.println(resume);
        }

        Iterator<Resume> iterator = collection.iterator();
        while (iterator.hasNext()) {
            Resume r = iterator.next();
            if (r.equals(RESUME_1)) {
                iterator.remove();
            }
        }
     //   System.out.println(collection);

        Map<String, Resume> map = new HashMap<>();
        map.put(UUID_1, RESUME_1);
        map.put(UUID_2, RESUME_2);
        map.put(UUID_3, RESUME_3);
        for (String uuid : map.keySet()) {
            System.out.println(map.get(uuid));
        }

    }
}

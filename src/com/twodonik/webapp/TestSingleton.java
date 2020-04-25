package com.twodonik.webapp;

import com.twodonik.webapp.model.SectionType;

public class TestSingleton {
    private static TestSingleton instance;

    public static TestSingleton getInstance() {
        if (instance == null) {
            instance = new TestSingleton();
        }
        return instance;
    }

    private TestSingleton() {
    }

    public static void main(String[] args) {
        for (SectionType type : SectionType.values()) {

            System.out.println(type.getTitle());
        }

    }

}

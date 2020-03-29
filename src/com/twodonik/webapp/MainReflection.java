package com.twodonik.webapp;

import com.twodonik.webapp.model.Resume;

import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) {
        Resume resume = new Resume();
        System.out.println(resume.getClass().getMethods()[2]);
    }
}

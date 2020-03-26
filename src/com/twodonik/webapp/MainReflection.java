package com.twodonik.webapp;

import com.twodonik.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) {
        Resume resume = new Resume();
        System.out.println(resume);
        Method method = resume.getClass().getMethods()[1];
        System.out.println(method.getName());
    }
}

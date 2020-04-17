package com.twodonik.webapp;

import com.twodonik.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Resume resume = new Resume("Dmitriy Dibrov");
        Field field = resume.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(resume));
        field.set(resume, "newuuid");
        System.out.println(resume);
        Method method = resume.getClass().getMethod("toString");
        Object result = method.invoke(resume);
        System.out.println(result);
    }
}

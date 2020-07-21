package com.twodonik.webapp;

import com.twodonik.webapp.model.Resume;

import java.util.UUID;

public class TestData {
    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();
    public static final String UUID_5 = UUID.randomUUID().toString();
    public static final Resume RESUME_1 = ResumeTestData.getTestResume1(UUID_1, "Ivanov Petr Sergeevich");
    public static final Resume RESUME_2 = ResumeTestData.getTestResume1(UUID_2, "Smirnov Denis Grigorevich");
    public static final Resume RESUME_3 = ResumeTestData.getTestResume1(UUID_3, "Smirnov Denis Grigorevich");
    public static final Resume RESUME_4 = ResumeTestData.getTestResume1(UUID_4, "Petrov Vasiliy Timofeevich");
    public static final Resume RESUME_5 = ResumeTestData.getTestResume1(UUID_5, "Sidorov Artur Denisovich");
//    protected static final Resume RESUME_1 = new Resume(UUID_1, "Ivanov Petr Sergeevich");
//    protected static final Resume RESUME_2 = new Resume(UUID_2, "Smirnov Denis Grigorevich");
//    protected static final Resume RESUME_3 = new Resume(UUID_3, "Smirnov Denis Grigorevich");
//    protected static final Resume RESUME_4 = new Resume(UUID_4, "Petrov Vasiliy Timofeevich");
//    protected static final Resume RESUME_5 = new Resume(UUID_5, "Sidorov Artur Denisovich");
}

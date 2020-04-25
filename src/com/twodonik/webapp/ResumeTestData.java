package com.twodonik.webapp;

import com.twodonik.webapp.model.Resume;
import com.twodonik.webapp.model.SectionType;
import com.twodonik.webapp.model.SectionTypeContact;

import static com.twodonik.webapp.model.SectionType.*;
import static com.twodonik.webapp.model.SectionTypeContact.*;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("uuid_1", "Petrov");

        resume.setContact(PHONE, "+79211234567");
        resume.setContact(SKYPE, "petrov.sk");
        resume.setContact(MAIL, "petrov.sk@gmail.com");


        resume.setSection(PERSONAL, "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        resume.setSection(OBJECTIVE, "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        resume.setSection(ACHIEVEMENT, "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        resume.setSection(ACHIEVEMENT, "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        resume.setSection(ACHIEVEMENT, "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        resume.setSection(QUALIFICATION, "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        resume.setSection(QUALIFICATION, "Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        resume.setSection(EXPERIENCE, "date:2016-01-16 Автор проекта.\n" +
                "Создание, организация и проведение Java онлайн проектов и стажировок.");
        resume.setSection(EXPERIENCE, "date:2018-01-16 \nСтарший разработчик (backend)\n" +
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        resume.setSection(EDUCATION, "date:2019-12-16\"Functional Programming Principles in Scala\" by Martin Odersky");


        System.out.println(resume.getFullName() + "\n");

        for (SectionTypeContact typeContact : SectionTypeContact.values()) {
            System.out.println(typeContact.getTitle());
            System.out.println(resume.storageSectionTypeContact.get(typeContact));
        }

        System.out.println();

        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle());
            if (type.ordinal() < 2) {
                System.out.println(resume.storageSectionType1.get(type) + "\n");
            } else if (type.ordinal() < 4 && type.ordinal() > 1) {
                System.out.println(resume.storageSectionType2.get(type) + "\n");
            } else if (type.ordinal() > 3 && type.ordinal() < 6) {
                System.out.println(resume.storageSectionType3.get(type) + "\n");
            }

        }
    }
}

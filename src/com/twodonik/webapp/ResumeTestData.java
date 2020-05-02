package com.twodonik.webapp;

import com.twodonik.webapp.model.Resume;
import com.twodonik.webapp.model.SectionType;
import com.twodonik.webapp.model.SectionTypeContact;
import com.twodonik.webapp.section.*;

import java.util.GregorianCalendar;

import static com.twodonik.webapp.model.SectionType.*;
import static com.twodonik.webapp.model.SectionTypeContact.*;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("uuid_1", "Petrov Sergey Semenovich");

        resume.storageContact.put(PHONE, "+79211234567");
        resume.storageContact.put(SKYPE, "petrov.sk");
        resume.storageContact.put(MAIL, "petrov.sk@gmail.com");

        resume.storageSection.put(PERSONAL, new PersonalSection("Аналитический склад ума, " +
                "сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        resume.storageSection.put(OBJECTIVE, new PersonalSection("Ведущий стажировок и " +
                "корпоративного обучения по Java Web и Enterprise технологиям"));

        ListSection achievement = new ListSection();
        ListSection qualification = new ListSection();

        achievement.addList("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java " +
                "Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). " +
                "Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. " +
                "Более 1000 выпускников.");
        achievement.addList("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievement.addList("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция " +
                "с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery." +
                " Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");

        qualification.addList("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualification.addList("Version control: Subversion, Git, Mercury, ClearCase, Perforce");

        resume.storageSection.put(ACHIEVEMENT, achievement);
        resume.storageSection.put(QUALIFICATION, qualification);

        ExperienceSection experience = new ExperienceSection();
        Company jOP = new Company("Java Online Projects",
                new GregorianCalendar(2013, 10, 1), null,
                "Автор проекта", "Создание, организация и проведение Java онлайн проектов и стажировок.");

        Company wrike = new Company("Wrike",
                new GregorianCalendar(2014, 10, 1), new GregorianCalendar(2016, 1, 1),
                "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами " +
                "Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, " +
                "авторизация по OAuth1, OAuth2, JWT SSO.");

        experience.addList(jOP);
        experience.addList(wrike);

        resume.storageSection.put(EXPERIENCE, experience);

        ExperienceSection education = new ExperienceSection();

        Company coursera = new Company("Coursera",
                new GregorianCalendar(2013, 03, 1),
                new GregorianCalendar(2013, 05, 1), null,
                "\"Functional Programming Principles in Scala\" by Martin Odersky");
        Company luxoft = new Company("Luxoft",
                new GregorianCalendar(2011, 03, 1),
                new GregorianCalendar(2011, 04, 1), null,
                "\"Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"");

        education.addList(coursera);
        education.addList(luxoft);

        resume.storageSection.put(EDUCATION, education);

        System.out.println(resume.getFullName() + "\n");

        for (SectionTypeContact sectionTypeContact : SectionTypeContact.values()) {
            String contact = resume.getStorageContact(sectionTypeContact);
            if (contact != null) {
                System.out.print(sectionTypeContact.getTitle() + ": ");
                System.out.println(contact);
            }
        }

        System.out.println();

        for (SectionType sectionType : SectionType.values()) {
            System.out.println();
            Sections sections = resume.getStorageSection(sectionType);
            System.out.println(sectionType.getTitle());
            if (sections != null) {
                System.out.print(sections + "\n");
            } else {
                System.out.println("----not completed----");
            }
        }
    }
}

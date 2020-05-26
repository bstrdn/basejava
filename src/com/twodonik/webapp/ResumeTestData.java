package com.twodonik.webapp;

import com.twodonik.webapp.model.*;
import com.twodonik.webapp.util.DateUtil;

import java.io.File;
import java.io.IOException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static com.twodonik.webapp.model.ContactType.*;
import static com.twodonik.webapp.model.SectionType.*;

public class ResumeTestData {
    public static void main(String[] args) throws IOException {
        Resume r = getTestResume1("UUID_1", "Sidorov Artur Denisovich");
        File file = new File(".\\storage\\" + r.getUuid());
        System.out.println(file.getCanonicalPath());

    }

    public static Resume getTestResume1(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        resume.contact.put(PHONE, "+79211234567");
        resume.contact.put(SKYPE, "petrov.sk");
        resume.contact.put(MAIL, "petrov.sk@gmail.com");

        resume.section.put(PERSONAL, new TextSection("Аналитический склад ума, " +
                "сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        resume.section.put(OBJECTIVE, new TextSection("Ведущий стажировок и " +
                "корпоративного обучения по Java Web и Enterprise технологиям"));

        List<String> achievement = new ArrayList<>();
        achievement.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java " +
                "Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). " +
                "Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. " +
                "Более 1000 выпускников.");
        achievement.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievement.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция " +
                "с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery." +
                " Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        resume.section.put(ACHIEVEMENT, new ListSection(achievement));

        List<String> qualification = new ArrayList<>();
        qualification.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualification.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        resume.section.put(QUALIFICATION, new ListSection(qualification));

        List<Organization> experienceList = new ArrayList<>();

        List<Position> positionsJOP = new ArrayList<>();
        positionsJOP.add(new Position(YearMonth.of(2013, 10), DateUtil.NOW,
                "Автор проекта", "Создание, организация и проведение Java онлайн проектов и " +
                "стажировок."));
        Link linkJOP = new Link("Java Online Projects", "http://javaops.ru/");
        Organization jOP = new Organization(linkJOP, positionsJOP);
        experienceList.add(jOP);

        List<Position> positionsWrike = new ArrayList<>();
        positionsWrike.add(new Position(YearMonth.of(2014, 10), YearMonth.of(2016, 1),
                "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами " +
                "Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, " +
                "авторизация по OAuth1, OAuth2, JWT SSO."));
        Link linkWrike = new Link("Wrike", "https://www.wrike.com/");
        Organization wrike = new Organization(linkWrike, positionsWrike);
        experienceList.add(wrike);

        List<Position> positionsAlcatel = new ArrayList<>();
        positionsAlcatel.add(new Position(YearMonth.of(1997, 9), YearMonth.of(2005, 1),
                "Инженер по аппаратному и программному тестрованию", "Тестирование, отладка" +
                ", внедрение ПО цифровой телефонной станции Alcatel 1000"));
        Link linkAlcatel = new Link("Alcatel", "http://www.alcatel.ru/");
        Organization alcatel = new Organization(linkAlcatel, positionsAlcatel);
        experienceList.add(alcatel);

        OrganizationSection organizationSection = new OrganizationSection(experienceList);
        resume.section.put(EXPERIENCE, organizationSection);


        List<Organization> educationList = new ArrayList<>();

        List<Position> positionsAlcatel2 = new ArrayList<>();
        positionsAlcatel2.add(new Position(YearMonth.of(1997, 9), YearMonth.of(1998, 3),
                " ", "6 месяцев обучения цифровым телефонным сетям (Москва)"));
        Organization Alcatel2 = new Organization(linkAlcatel, positionsAlcatel2);
        educationList.add(Alcatel2);

        Link linkITMO = new Link("ITMO", "http://www.itmo.ru/");
        List<Position> positionsITMO = new ArrayList<>();
        positionsITMO.add(new Position(YearMonth.of(1993, 9), YearMonth.of(1996, 7), " ", "Аспирантура (Программист С, С++)"));
        positionsITMO.add(new Position(YearMonth.of(1987, 9), YearMonth.of(1993, 7), " ", "Инженер (программист Fortran, C)"));
        Organization itmo = new Organization(linkITMO, positionsITMO);
        educationList.add(itmo);

        OrganizationSection educationSection = new OrganizationSection(educationList);
        resume.section.put(EDUCATION, educationSection);

        return resume;
    }

    public static Resume getTestResume2(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        resume.contact.put(PHONE, "+79211234567");

        resume.section.put(PERSONAL, new TextSection("PERSONAL"));
        resume.section.put(OBJECTIVE, new TextSection("OBJECTIVE"));

        List<String> achievement = new ArrayList<>();
        achievement.add("1");
        achievement.add("2");
        achievement.add("3");
        resume.section.put(ACHIEVEMENT, new ListSection(achievement));

        List<String> qualification = new ArrayList<>();
        qualification.add("11");
        qualification.add("22");
        resume.section.put(QUALIFICATION, new ListSection(qualification));

        List<Organization> experienceList = new ArrayList<>();

        List<Position> positionsJOP = new ArrayList<>();
        positionsJOP.add(new Position(YearMonth.of(2013, 10), DateUtil.NOW,
                "33", "444"));
        Link linkJOP = new Link("Java Online Projects", "http://javaops.ru/");
        Organization jOP = new Organization(linkJOP, positionsJOP);
        experienceList.add(jOP);

        List<Position> positionsWrike = new ArrayList<>();
        positionsWrike.add(new Position(YearMonth.of(2014, 10), YearMonth.of(2016, 1),
                "55", "333"));
        Link linkWrike = new Link("Wrike", "https://www.wrike.com/");
        Organization wrike = new Organization(linkWrike, positionsWrike);
        experienceList.add(wrike);

        List<Position> positionsAlcatel = new ArrayList<>();
        positionsAlcatel.add(new Position(YearMonth.of(1997, 9), YearMonth.of(2005, 1),
                "66", "77"));
        Link linkAlcatel = new Link("Alcatel", "http://www.alcatel.ru/");
        Organization alcatel = new Organization(linkAlcatel, positionsAlcatel);
        experienceList.add(alcatel);

        OrganizationSection organizationSection = new OrganizationSection(experienceList);
        resume.section.put(EXPERIENCE, organizationSection);


        List<Organization> educationList = new ArrayList<>();

        List<Position> positionsAlcatel2 = new ArrayList<>();
        positionsAlcatel2.add(new Position(YearMonth.of(1997, 9), YearMonth.of(1998, 3),
                " ", "88"));
        Organization Alcatel2 = new Organization(linkAlcatel, positionsAlcatel2);
        educationList.add(Alcatel2);

        Link linkITMO = new Link("ITMO", "http://www.itmo.ru/");
        List<Position> positionsITMO = new ArrayList<>();
        positionsITMO.add(new Position(YearMonth.of(1993, 9), YearMonth.of(1996, 7), " ", "00"));
        positionsITMO.add(new Position(YearMonth.of(1987, 9), YearMonth.of(1993, 7), " ", "99"));
        Organization itmo = new Organization(linkITMO, positionsITMO);
        educationList.add(itmo);

        OrganizationSection educationSection = new OrganizationSection(educationList);
        resume.section.put(EDUCATION, educationSection);

        return resume;
    }

    public void printRecume(Resume resume) {
        System.out.println(resume.getFullName() + "\n");

        for (ContactType contactType : ContactType.values()) {
            String contact = resume.getStorageContact(contactType);
            if (contact != null) {
                System.out.print(contactType.getTitle() + ": ");
                System.out.println(contact);
            }
        }

        System.out.println();

        for (SectionType sectionType : SectionType.values()) {
            System.out.println();
            AbstractSection abstractSection = resume.getStorageSection(sectionType);
            System.out.println(sectionType.getTitle());
            if (abstractSection != null) {
                System.out.print(abstractSection + "\n");
            } else {
                System.out.println("----not completed----");
            }
        }
    }
}

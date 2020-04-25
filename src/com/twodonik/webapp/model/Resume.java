package com.twodonik.webapp.model;

import java.sql.Date;
import java.util.*;

import static com.twodonik.webapp.model.SectionType.*;

/**
 * Initial resume class
 */
public class Resume {

    // Unique identifier
    private final String uuid;

    private final String fullName;

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    //тут private должно быть
    public Map<SectionType, String> storageSectionType1 = new HashMap<>();
    public Map<SectionType, List> storageSectionType2 = new HashMap<>();
    public Map<SectionType, Map> storageSectionType3 = new HashMap<>();

    public Map<SectionTypeContact, String> storageSectionTypeContact = new HashMap<>();

    public void setContact(SectionTypeContact contact, String body) {
        storageSectionTypeContact.put(contact, body);
    }

    public void setSection(SectionType section, String body) {
        if (section == PERSONAL || section == OBJECTIVE) {
            storageSectionType1.put(section, body);
        } else if (section == ACHIEVEMENT || section == QUALIFICATION) {
            if (!storageSectionType2.containsKey(section)) {
                storageSectionType2.put(section, new ArrayList());
            }
            storageSectionType2.get(section).add(body);
        } else if (section == EXPERIENCE || section == EDUCATION) {
            if (!storageSectionType3.containsKey(section)) {
                storageSectionType3.put(section, new HashMap());
            }
            //тут нужен парсер даты из строки body
            Date moment = new Date(1451665447567L);
            storageSectionType3.get(section).put(moment, body);
        }
    }


    public String getFullName() {
        return fullName;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "UUID: " + uuid + " Full name: " + fullName;
    }
}

package com.twodonik.webapp.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume {

    private final String uuid;
    private final String fullName;


    public Map<SectionTypeContact, String> contact = new HashMap<>();
    public Map<SectionType, AbstractSection> section = new HashMap<>();

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getStorageContact(SectionTypeContact typeContact) {
        return contact.get(typeContact);
    }

    public AbstractSection getStorageSection(SectionType type) {
        return section.get(type);
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
        if (!fullName.equals(resume.fullName)) return false;
        if (!contact.equals(resume.contact)) return false;
        return section.equals(resume.section);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        result = 31 * result + contact.hashCode();
        result = 31 * result + section.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "UUID: " + uuid + " Full name: " + fullName;
    }
}

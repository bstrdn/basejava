package com.twodonik.webapp.model;

import com.twodonik.webapp.section.Sections;

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


    public Map<SectionTypeContact, String> storageContact = new HashMap<>();
    public Map<SectionType, Sections> storageSection = new HashMap<>();

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
        return storageContact.get(typeContact);
    }

    public Sections getStorageSection(SectionType type) {
        return storageSection.get(type);
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

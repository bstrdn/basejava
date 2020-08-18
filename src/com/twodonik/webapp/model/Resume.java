package com.twodonik.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Serializable, Comparable<Resume> {
    private static final long serialVersionUID = 1L;


    private String uuid;
    private String fullName;


    public Map<ContactType, String> contact = new EnumMap<>(ContactType.class);
    public Map<SectionType, AbstractSection> section = new EnumMap<>(SectionType.class);

    public Resume() {
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public void addSection(SectionType type, AbstractSection abstractSection) {
        section.put(type, abstractSection);
    }

    public Map<ContactType, String> getContact() {
        return contact;
    }

    public void setSection(Map<SectionType, AbstractSection> section) {
        this.section = section;
    }

    public void addContact(ContactType contactType, String string) {
        contact.put(contactType, string);
    }

    public String getStorageContact(ContactType typeContact) {
        return contact.get(typeContact);
    }

    public AbstractSection getStorageSection(SectionType type) {
        return section.get(type);
    }

    public Map<SectionType, AbstractSection> getSection() {
        return section;
    }
    public AbstractSection getSection(SectionType type) {
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
        return uuid.equals(resume.uuid) &&
                fullName.equals(resume.fullName) &&
                contact.equals(resume.contact) &&
                section.equals(resume.section);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contact, section);
    }

    @Override
    public String toString() {
        return "UUID: " + uuid + " Full name: " + fullName;
    }

    @Override
    public int compareTo(Resume o) {
        int i = fullName.compareTo(o.fullName);
        return i != 0 ? i : uuid.compareTo(o.uuid);
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}

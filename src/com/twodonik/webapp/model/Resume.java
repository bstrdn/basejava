package com.twodonik.webapp.model;

import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume {

    // Unique identifier
    private final String uuid;

    private String fullName;

    public Resume(String fullName) {
        uuid = UUID.randomUUID().toString();
        this.fullName = fullName;

    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
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

        return uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return uuid;
    }
}

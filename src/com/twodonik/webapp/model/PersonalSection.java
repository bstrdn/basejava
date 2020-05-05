package com.twodonik.webapp.model;

public class PersonalSection extends AbstractSection {

    String body;

    public PersonalSection(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonalSection that = (PersonalSection) o;

        return body.equals(that.body);
    }

    @Override
    public int hashCode() {
        return body.hashCode();
    }
}

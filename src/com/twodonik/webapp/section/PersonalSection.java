package com.twodonik.webapp.section;

public class PersonalSection extends Sections {

    String body;

    public PersonalSection(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return body;
    }
}

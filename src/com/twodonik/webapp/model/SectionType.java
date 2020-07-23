package com.twodonik.webapp.model;

public enum SectionType {
    PERSONAL("Личные качества"),
    OBJECTIVE("Позиция"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATION("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    public String getTitle() {
        return title;
    }

    private String title;

    private SectionType(String title) {
        this.title = title;
    }

    public <T> String toHtml(T section) {
        String result = "";
        if (section instanceof TextSection) {
            result += section;
        } else if (section instanceof ListSection) {
            ListSection listSection = (ListSection) section;
            for (String value : listSection.getItems()) {
                result+="<p>" + value + "</p>";
            }
        } else if (section instanceof OrganizationSection) {
            OrganizationSection s = (OrganizationSection) section;
            for (Organization org : s.getOrganizations()) {
                Link link = org.getLink();
                result += "<p>" + "<a href=\"" + link.getUrl() + "\">" + link.getName() + "</a><p>";
                for (Position p : org.getPositions()) {
                    result += "<p>" + p.getStartDate() + " - " + p.getEndDate() + " " + p.getTitle() + "<br>" + p.getDescription() + "</p>";
                }
            }
        }
        return result;
    }
}

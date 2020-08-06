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

    SectionType(String title) {
        this.title = title;
    }

    public <T> String toHtml(T section) {
        String result = "";

        switch (section.getClass().getSimpleName()) {
            case "TextSection":
                result += section;
                break;
            case "ListSection":
                ListSection listSection = (ListSection) section;
                for (String value : listSection.getItems()) {
                    result += "<p>" + value + "</p>";
                }
                break;
            case "OrganizationSection":
                OrganizationSection s = (OrganizationSection) section;
                for (Organization org : s.getOrganizations()) {
                    Link link = org.getLink();
                    result += "<p>" + "<a href=\"" + link.getUrl() + "\">" + link.getName() + "</a><p>";
                    for (Position p : org.getPositions()) {
                        result += "<p>" + p.getStartDate() + " - " + (p.getEndDate().toString().equals("3000-01") ? "now" : p.getEndDate()) + " " + p.getTitle() + "<br>" + p.getDescription() + "</p>";
                    }
                }
                break;
        }
        return result;
    }
}

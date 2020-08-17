package com.twodonik.webapp.model;

import java.util.Map;

public enum SectionType {
    OBJECTIVE("Позиция"),
    PERSONAL("Личные качества"),
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

    public <T> String toHtml(Map.Entry<SectionType, AbstractSection> m) {
        String result = "";
        SectionType st = m.getKey();
        AbstractSection as = m.getValue();
        switch (st) {
            case OBJECTIVE:
            case PERSONAL:
                result += as;
                break;
            case ACHIEVEMENT:
            case QUALIFICATION:
                ListSection listSection = (ListSection) as;
                for (String value : listSection.getItems()) {
                    result += "<p>" + "• " + value + "</p>";
                }
                break;
            case EXPERIENCE:
            case EDUCATION:
                OrganizationSection s = (OrganizationSection) as;
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

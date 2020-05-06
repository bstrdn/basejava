package com.twodonik.webapp.model;

public enum ContactType {
    PHONE("Тел."),
    SKYPE("Skype"),
    MAIL("Почта"),
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    SITE("Домашняя страница");

    public String getTitle() {
        return title;
    }

    private String title;

    private ContactType(String title) {
        this.title = title;
    }
}

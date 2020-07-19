package com.twodonik.webapp.model;

public enum ContactType {
    PHONE("Тел."),
    SKYPE("Skype"),
    MAIL("Почта") {
        @Override
        public String toHtml0(String value) {
            return "<a href='mailto:" + value + "'>" + value + "</a>";
        }
    },
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    SITE("Домашняя страница");

    private String title;

    private ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

public String toHtml0(String value) {
    return this.title + ": " + value;
}

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }

}

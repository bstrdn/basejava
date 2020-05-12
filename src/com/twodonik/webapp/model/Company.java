package com.twodonik.webapp.model;

import java.util.Objects;

public class Company {
    private String name;
    private String url;

    public Company(String name, String url) {
        Objects.requireNonNull(name, "name mast not be null");
        this.name = name;
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return name.equals(company.name) &&
                Objects.equals(url, company.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url);
    }

    @Override
    public String toString() {
        return name + " (url='" + url + "')\n";
    }
}

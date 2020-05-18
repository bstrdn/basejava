package com.twodonik.webapp.model;

import java.io.Serializable;
import java.util.Objects;

public class Link implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String url;

    public Link(String name, String url) {
        Objects.requireNonNull(name, "name mast not be null");
        this.name = name;
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return name.equals(link.name) &&
                Objects.equals(url, link.url);
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

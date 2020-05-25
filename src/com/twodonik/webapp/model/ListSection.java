package com.twodonik.webapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private List<String> items = new ArrayList<>();

    public ListSection() {
    }

    public ListSection(String... items) {
        this(Arrays.asList(items));
    }
    public ListSection(List<String> items) {
        Objects.requireNonNull(items, "items must not be null");
        this.items = items;
    }

    public void addList(String s) {
        items.add(s);
    }

    public List<String> getItems() {
        return items;
    }


    @Override
    public String toString() {

        String out = "";

        for (String s : items) {
            out += "• " + s + "\n";
        }

        return out;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return items.equals(that.items);
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }
}

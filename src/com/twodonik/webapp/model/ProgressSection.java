package com.twodonik.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class ProgressSection extends AbstractSection {
    private List<String> list = new ArrayList<>();

    public void addList(String s) {
        list.add(s);
    }

    @Override
    public String toString() {

        String out = "";

        for (String s : list) {
            out += "• " + s + "\n";
        }

        return out;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProgressSection that = (ProgressSection) o;

        return list.equals(that.list);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }
}

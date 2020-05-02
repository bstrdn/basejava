package com.twodonik.webapp.section;

import java.util.ArrayList;
import java.util.List;

public class ListSection extends Sections {
    List<String> list = new ArrayList<>();

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
}

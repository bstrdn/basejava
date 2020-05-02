package com.twodonik.webapp.section;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ExperienceSection extends Sections {
    List<Company> list = new ArrayList();

    public void addList(Company company) {
        list.add(company);
    }

    @Override
    public String toString() {
        String out = "";

        for (Company company : list) {
            out += company.company + "\n" + company.start.get(Calendar.MONTH) + "/"
                    + company.start.get(Calendar.YEAR) + " - "
                    + (company.end == null ? "Сейчас" : company.end.get(Calendar.MONTH) + "/"
                    + company.end.get(Calendar.YEAR)) + "     "
                    + ((company.position == null) ? "" : company.position + "\n") + company.body + "\n";
        }

        return out;
    }
}

package com.twodonik.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class ExperienceSection extends AbstractSection {
    private List<Company> list = new ArrayList();

    public void addList(Company company) {
        list.add(company);
    }

    @Override
    public String toString() {
        String out = "";

        for (Company company : list) {
            out += company.getCompany() + "\n" + company.getStartData() + " - "
                    + (company.getEndData() == null ? "Сейчас" : company.getEndData()) + "     "
                    + ((company.getPosition() == null) ? "" : company.getPosition() + "\n") + company.getBody() + "\n";
        }

        return out;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExperienceSection that = (ExperienceSection) o;

        return list.equals(that.list);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }
}

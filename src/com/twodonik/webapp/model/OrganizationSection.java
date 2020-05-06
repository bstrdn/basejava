package com.twodonik.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {
    private List<Organization> organizations = new ArrayList();

    public void addList(Organization organization) {
        organizations.add(organization);
    }

    public OrganizationSection(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "organization must not be null");
        this.organizations = organizations;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    @Override
    public String toString() {
        String out = "";

        for (Organization organization : organizations) {
            out += organization.getCompany() + "\n" + organization.getStartData() + " - "
                    + (organization.getEndData() == null ? "Сейчас" : organization.getEndData()) + "     "
                    + ((organization.getPosition() == null) ? "" : organization.getPosition() + "\n") + organization.getBody() + "\n";
        }

        return out;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection that = (OrganizationSection) o;

        return organizations.equals(that.organizations);
    }

    @Override
    public int hashCode() {
        return organizations.hashCode();
    }
}

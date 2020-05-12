package com.twodonik.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {

    private final Company company;
    List<Position> positions = new ArrayList<>();


    public Organization(Company company, List<Position> positions) {
        Objects.requireNonNull(positions, "positions must not be null");
        Objects.requireNonNull(company, "company must not be null");
        this.company = company;
        this.positions = positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return company.equals(that.company) &&
                positions.equals(that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(company, positions);
    }

    @Override
    public String toString() {
        String out = "";
        for (Position position : positions) {
            out += position.toString() + "\n";
        }
        return company + out;
    }
}

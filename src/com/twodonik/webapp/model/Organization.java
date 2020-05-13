package com.twodonik.webapp.model;

import java.util.List;
import java.util.Objects;

public class Organization {

    private final Link link;
    private List<Position> positions;


    public Organization(Link link, List<Position> positions) {
        Objects.requireNonNull(positions, "positions must not be null");
        Objects.requireNonNull(link, "company must not be null");
        this.link = link;
        this.positions = positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return link.equals(that.link) &&
                positions.equals(that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link, positions);
    }

    @Override
    public String toString() {
        String out = "";
        for (Position position : positions) {
            out += position.toString() + "\n";
        }
        return link + out;
    }
}

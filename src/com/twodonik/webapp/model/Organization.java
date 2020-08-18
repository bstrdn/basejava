package com.twodonik.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final Organization EMPTY = new Organization("", "", Position.EMPTY);

    public Organization() {
    }

    private Link link;

    private List<Position> positions;

    public Organization(String name, String url, Position... positions) {
        this(new Link(name, url), positions);
    }

    public Organization(Link link, Position... positions) {
        this(link, Arrays.asList(positions));

    }

    public Organization(Link link, List<Position> positions) {
        Objects.requireNonNull(positions, "positions must not be null");
        Objects.requireNonNull(link, "company must not be null");
        this.link = link;
        this.positions = positions;
    }
    public Link getLink() {
        return link;
    }

    public List<Position> getPositions() {
        return positions;
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

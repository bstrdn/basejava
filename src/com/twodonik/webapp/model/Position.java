package com.twodonik.webapp.model;

import com.twodonik.webapp.util.YearMonthAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.YearMonth;
import java.util.Objects;
import static com.twodonik.webapp.util.DateUtil.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Position implements Serializable {
    private static final long serialVersionUID = 1L;

    @XmlJavaTypeAdapter(YearMonthAdapter.class)
    private YearMonth startDate;
    @XmlJavaTypeAdapter(YearMonthAdapter.class)
    private YearMonth endDate;
    private String title;
    private String description;

    public Position(YearMonth startDate, YearMonth endDate, String title, String description) {
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "startDate must not be null");
        Objects.requireNonNull(description, "startDate must not be null");
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    public Position(YearMonth startDate, String title, String description) {
        this(startDate, NOW, title,description);
    }

    public Position() {
    }

    public YearMonth getStartDate() {
        return startDate;
    }

    public YearMonth getEndDate() {
        return endDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return startDate.equals(position.startDate) &&
                Objects.equals(endDate, position.endDate) &&
                Objects.equals(title, position.title) &&
                description.equals(position.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, title, description);
    }

    @Override
    public String toString() {
        return startDate + " - "
                + (endDate == null ? "Сейчас" : endDate) + "     "
                + ((title == null) ? "" : title + "\n") + description + "\n";

    }
}

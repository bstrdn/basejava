package com.twodonik.webapp.section;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Company {
    String company;
    Calendar start;
    Calendar end;
    String position;
    String body;

    public Company(String company, GregorianCalendar start, GregorianCalendar end, String position, String body) {
        this.company = company;
        this.start = start;
        this.end = end;
        this.position = position;
        this.body = body;
    }

}

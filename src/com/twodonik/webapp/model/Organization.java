package com.twodonik.webapp.model;

import java.time.YearMonth;

public class Organization {
    private String company;

    private YearMonth startData;
    private YearMonth endData;
    private String position;
    private String body;

    public Organization(String company, YearMonth startData, YearMonth endData, String position, String body) {
        this.company = company;
        this.startData = startData;
        this.endData = endData;
        this.position = position;
        this.body = body;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public YearMonth getStartData() {
        return startData;
    }

    public void setStartData(YearMonth startData) {
        this.startData = startData;
    }

    public YearMonth getEndData() {
        return endData;
    }

    public void setEndData(YearMonth endData) {
        this.endData = endData;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


}

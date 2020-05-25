package com.twodonik.webapp.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.YearMonth;

public class YearMonthAdapter extends XmlAdapter<String, YearMonth> {
    @Override
    public YearMonth unmarshal(String v) throws Exception {
        return YearMonth.parse(v);
    }

    @Override
    public String marshal(YearMonth v) throws Exception {
        return v.toString();
    }
}

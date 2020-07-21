package com.twodonik.webapp.util;

import com.twodonik.webapp.model.AbstractSection;
import com.twodonik.webapp.model.ListSection;
import com.twodonik.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

import static com.twodonik.webapp.TestData.RESUME_1;

public class JsonParserTest {

    @Test
    public void testResume() {
        String json = JsonParser.write(RESUME_1);
        System.out.println(json);
        Resume resume2 = JsonParser.read(json, Resume.class);
        Assert.assertEquals(RESUME_1, resume2);
    }

    @Test
    public void testSection() {
        AbstractSection as1 = new ListSection();
        String s1 = JsonParser.write(as1, AbstractSection.class);
        System.out.println(s1);
        AbstractSection as2 = JsonParser.read(s1, AbstractSection.class);
        Assert.assertEquals(as1, as2);

    }

    @Test
    public void write() {
    }
}
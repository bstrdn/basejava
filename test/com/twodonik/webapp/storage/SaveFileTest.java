package com.twodonik.webapp.storage;

import com.twodonik.webapp.ResumeTestData;
import com.twodonik.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class SaveFileTest {
    protected static final File STORAGE_DIR = new File("E:\\Java\\basejava\\storage");
    protected static final ObjectStreamPathStorage osps = new ObjectStreamPathStorage("E:\\Java\\basejava\\storage");
    protected static final ObjectStreamStorage oss = new ObjectStreamStorage(STORAGE_DIR);
    SaveFile saveFile = new SaveFile(osps);

    protected static final String UUID_1 = "UUID_1";
    protected static final Resume RESUME_1 = ResumeTestData.getTestResume(UUID_1, "Ivanov Petr Sergeevich");

    @Before
    public void setUp() {
        saveFile.clear();
    }

    @Test
    public void saveFile() {
        saveFile.setSaveStrategy(osps);
        saveFile.save(RESUME_1);
        assertEquals(RESUME_1, saveFile.get(UUID_1));
    }

    @Test
    public void savePath() {
        saveFile.setSaveStrategy(oss);
        saveFile.save(RESUME_1);
        assertEquals(RESUME_1, saveFile.get(UUID_1));
    }
}

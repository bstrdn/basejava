package com.twodonik.webapp;


import com.twodonik.webapp.model.Resume;
import com.twodonik.webapp.storage.ObjectStreamPathStorage;
import com.twodonik.webapp.storage.ObjectStreamStorage;
import com.twodonik.webapp.storage.SaveFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainFile {
    protected static final File STORAGE_DIR = new File("E:\\Java\\basejava\\storage");
    private static Path directory2 = Paths.get("E:\\Java\\basejava\\storage");

    public static void main(String[] args) throws IOException {
        File directory = new File(".\\src");
        getAllFile(directory, "");

        ObjectStreamPathStorage osps = new ObjectStreamPathStorage("E:\\Java\\basejava\\storage");
        ObjectStreamStorage oss = new ObjectStreamStorage(STORAGE_DIR);
        Resume r1 = new Resume("uuid11", "Dima");
        Resume r2 = new Resume("uuid12", "Olya");

        SaveFile sf = new SaveFile(osps);

        sf.save(r1);

        sf.setSaveStrategy(oss);

        sf.save(r2);

    }


    public static void getAllFile(File directory, String backspace) {
        String bs = backspace;
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file1 : files) {
                if (file1.isDirectory()) {
                    bs += " ";
                    System.out.print(backspace);
                    System.out.println("[" + file1.getName() + "]");
                    getAllFile(file1, bs);
                } else {
                    System.out.print(backspace);
                    System.out.println(file1.getName());
                }
            }
        }
    }

}

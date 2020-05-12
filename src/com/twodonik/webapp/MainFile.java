package com.twodonik.webapp;


import java.io.File;
import java.io.IOException;

public class MainFile {

    public static void main(String[] args) throws IOException {
        File directory = new File(".\\");
        getAllFile(directory);
    }

    public static void getAllFile(File directory) {
        for (File file1 : directory.listFiles()) {
            if (file1.isDirectory()) {
                System.out.println("DIRECTORY: " + file1.getName());
                getAllFile(file1);
            }
            System.out.println(file1.getName());
        }
    }
}

package com.twodonik.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    protected static final File PROPS = new File(getProp() + "config\\resumes.properties");

    private static final Config INSTANCE = new Config();

    private Properties prop = new Properties();
    private File storageDir;
    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            prop.load(is);
            storageDir = new File (prop.getProperty("storage.dir"));
            dbUrl = prop.getProperty("db.url");
            dbUser = prop.getProperty("db.user");
            dbPassword = prop.getProperty("db.password");
        } catch (IOException e) {
            throw new IllegalStateException("invalid config file " + PROPS.getAbsolutePath());
        }
    }

    private static String getProp() {
        String home = System.getProperty("homeDir");
        if (home == null)
            return ".\\";
        else return home;
    }

    public File getStorageDir() {
        return storageDir;
    }
    public String getDbUrl() {
        return dbUrl;
    }
    public String getDbUser() {
        return dbUser;
    }
    public String dbPassword() {
        return dbPassword;
    }
}

package com.twodonik.webapp.storage;

import com.twodonik.webapp.model.Resume;

public class SaveFile {
    private SaveStrategy saveStrategy;

    public SaveFile(SaveStrategy saveStrategy) {
        this.saveStrategy = saveStrategy;
    }

    public void setSaveStrategy(SaveStrategy saveStrategy) {
        this.saveStrategy = saveStrategy;
    }

    public void save(Resume r) {
        saveStrategy.save(r);
    }

    public Resume get(String uuid) {
        return saveStrategy.get(uuid);
    }

    public void clear() {
        saveStrategy.clear();
    }
}

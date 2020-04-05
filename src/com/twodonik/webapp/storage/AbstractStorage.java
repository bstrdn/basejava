package com.twodonik.webapp.storage;

import com.twodonik.webapp.model.Resume;

import java.util.Collection;
import java.util.Iterator;

public abstract class AbstractStorage implements Storage {

    protected Resume[] storage;
    protected Collection<Resume> storages;

    public AbstractStorage(Resume[] storage) {
        this.storage = storage;
    }

    public AbstractStorage(Collection<Resume> storages) {
        this.storages = storages;
    }

    public abstract int size();

}

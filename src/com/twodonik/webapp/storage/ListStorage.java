package com.twodonik.webapp.storage;

import com.twodonik.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {

    public ListStorage() {
        super(new ArrayList<Resume>());
    }

    @Override
    public int size() {
        return storages.size();
    }
}

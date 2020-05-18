package com.twodonik.webapp.storage;

import com.twodonik.webapp.exception.StorageException;
import com.twodonik.webapp.model.Resume;

import java.io.*;

public class ObjectStreamStorage extends AbstractFileStorage implements SaveStrategy {
    public ObjectStreamStorage(File directory) {
        super(directory);
    }

    @Override
    protected void doWrite(Resume resume, OutputStream os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(resume);
        }

    }

    @Override
    protected Resume doRead(InputStream is) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("error", null, e);
        }
    }


}

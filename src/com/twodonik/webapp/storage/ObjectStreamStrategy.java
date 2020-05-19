package com.twodonik.webapp.storage;

import com.twodonik.webapp.exception.StorageException;
import com.twodonik.webapp.model.Resume;

import java.io.*;

public class ObjectStreamStrategy implements SaveStrategy {

    @Override
    public void doWrite(Resume resume, OutputStream os) {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(resume);
        } catch (IOException e) {
            throw new StorageException("write error", null, e);
        }
    }

    @Override
    public Resume doRead(InputStream is) {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new StorageException("read error", null, e);
        }
    }
}

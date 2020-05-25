package com.twodonik.webapp.storage.serializer;

import com.twodonik.webapp.exception.StorageException;
import com.twodonik.webapp.model.Resume;
import com.twodonik.webapp.storage.serializer.StreamSerializer;

import java.io.*;

public class ObjectStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume resume, OutputStream os) {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(resume);
        } catch (IOException e) {
            throw new StorageException("write error", e);
        }
    }

    @Override
    public Resume doRead(InputStream is) {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new StorageException("read error", e);
        }
    }
}

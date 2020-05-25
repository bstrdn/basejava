package com.twodonik.webapp.storage.serializer;

import com.twodonik.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {

    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        return null;
    }
}

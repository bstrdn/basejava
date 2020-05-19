package com.twodonik.webapp.storage;

import com.twodonik.webapp.model.Resume;

import java.io.InputStream;
import java.io.OutputStream;

public interface SaveStrategy {

    void doWrite(Resume resume, OutputStream os);

    Resume doRead(InputStream is);
}

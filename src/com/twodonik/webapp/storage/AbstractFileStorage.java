package com.twodonik.webapp.storage;

import com.twodonik.webapp.exception.StorageException;
import com.twodonik.webapp.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected abstract void doWrite(Resume resume, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected void updateResume(Resume resume, File file) {
        try {
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", resume.getUuid(), e);
        }
    }

    @Override
    protected void saveResume(Resume resume, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("IO error", resume.getUuid(), e);
        }
        updateResume(resume, file);
    }

    @Override
    protected Resume getResume(File file) {
        try {
            return doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("io error", file.getName(), e);
        }
    }

    @Override
    protected void deleteResume(File file) {
        if (!file.delete()) {
            throw new StorageException("File delete error", file.getName());
        }
    }

    @Override
    protected File findKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected List<Resume> getList() {
        File[] file = directory.listFiles();
        if (file == null) {
            throw new StorageException("file error", null);
        }
        List<Resume> lr = new ArrayList<>(file.length);
        for (File f : file) {
            lr.add(getResume(f));
        }
        return lr;
    }

    @Override
    public void clear() {
        File[] file = directory.listFiles();
        if (file != null) {
            for (File f : file) {
                deleteResume(f);
            }
        }
    }

    @Override
    public int size() {
        String[] list = directory.list();
        if (list == null) {
            throw new StorageException("directory read error", null);
        }
        return list.length;
    }
}


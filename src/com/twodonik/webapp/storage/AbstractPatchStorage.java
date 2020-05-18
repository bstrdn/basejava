package com.twodonik.webapp.storage;

import com.twodonik.webapp.exception.StorageException;
import com.twodonik.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractPatchStorage extends AbstractStorage<Path> {
    private Path directory;

    protected abstract void doWrite(Resume resume, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;

    protected AbstractPatchStorage(String dir) {
        Path directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + "is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected void updateResume(Resume resume, Path dir) {
        try {
            doWrite(resume, new BufferedOutputStream(Files.newOutputStream(dir)));
        } catch (IOException e) {
            throw new StorageException("IO error", resume.getUuid(), e);
        }
    }

    @Override
    protected void saveResume(Resume resume, Path dir) {
        try {
            Files.createFile(dir);
        } catch (IOException e) {
            throw new StorageException("IO error", resume.getUuid(), e);
        }
        updateResume(resume, dir);

    }

    @Override
    protected Resume getResume(Path dir) {
        try {
            return doRead(new BufferedInputStream(Files.newInputStream(dir)));
        } catch (IOException e) {
            throw new StorageException("io error", dir.getFileName().toString(), e);
        }
    }

    @Override
    protected void deleteResume(Path dir) {
        try {
            Files.deleteIfExists(dir);
        } catch (IOException e) {
            throw new StorageException("delet error", dir.getFileName().toString(), e);
        }
    }

    @Override
    protected Path findKey(String uuid) {
        return Paths.get(directory.toString(), uuid);
    }

    @Override
    protected boolean isExist(Path dir) {
        return Files.exists(dir);
    }

    @Override
    protected List<Resume> getList() {
        List<Resume> list = new ArrayList<>();
        try {
            Files.list(directory).forEach(path -> {
                list.add(getResume(path));
            });
            return list;
        } catch (IOException e) {
            throw new StorageException("get list exception", null, e);
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::deleteResume);
        } catch (IOException e) {
            throw new StorageException("delete exception", null);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("io exception", null, e);
        }
    }
}


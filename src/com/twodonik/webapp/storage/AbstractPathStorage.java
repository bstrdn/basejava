package com.twodonik.webapp.storage;

import com.twodonik.webapp.exception.StorageException;
import com.twodonik.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private Path directory;
    SaveStrategy saveStrategy;

    protected void doWrite(Resume resume, OutputStream os) {
        saveStrategy.doWrite(resume, os);
    }

    protected Resume doRead(InputStream is) {
        return saveStrategy.doRead(is);
    }

    protected AbstractPathStorage(String dir, SaveStrategy saveStrategy) {
        Path directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + "is not readable/writable");
        }
        this.directory = directory;
        this.saveStrategy = saveStrategy;
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
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path dir) {
        return Files.isRegularFile(dir);
    }


    @Override
    protected List<Resume> getList() {
        try {
            return Files.list(directory).map(this::getResume).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("get list exception", null, e);
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::deleteResume);
        } catch (IOException e) {
            throw new StorageException("delete exception", null, e);
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


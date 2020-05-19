package com.twodonik.webapp.storage;

import com.twodonik.webapp.exception.StorageException;
import com.twodonik.webapp.model.Resume;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;
    SaveStrategy saveStrategy;

    protected PathStorage(String dir, SaveStrategy saveStrategy) {
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
            saveStrategy.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(dir)));
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
            return saveStrategy.doRead(new BufferedInputStream(Files.newInputStream(dir)));
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
        return getStream().map(this::getResume).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        getStream().forEach(this::deleteResume);
    }

    @Override
    public int size() {
        return (int) getStream().count();
    }

    Stream<Path> getStream() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("io exception", null, e);
        }
    }
}


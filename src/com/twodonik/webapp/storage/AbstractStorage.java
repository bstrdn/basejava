package com.twodonik.webapp.storage;

import com.twodonik.webapp.exception.ExistStorageException;
import com.twodonik.webapp.exception.NotExistStorageException;
import com.twodonik.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

    protected static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    private static Comparator<Resume> resumeUuidNameComparator = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    public void update(Resume resume) {
        LOG.info("Update " + resume);
        updateResume(resume, getKeyIfExist(resume.getUuid()));
    }

    public void save(Resume resume) {
        LOG.info("Save " + resume);
        SK searchKey = findKey(resume.getUuid());
        if (isExist(searchKey)) {
            LOG.warning("Resume " + resume + " already exist");
            throw new ExistStorageException(resume.getUuid());
        } else {
            saveResume(resume, searchKey);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = getList();
        list.sort(resumeUuidNameComparator);
        return list;
    }

    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return getResume(getKeyIfExist(uuid));
    }

    public void delete(String uuid) {
        LOG.info("Delete " + uuid);

        deleteResume(getKeyIfExist(uuid));
    }

    protected abstract void updateResume(Resume resume, SK key);

    protected abstract void saveResume(Resume resume, SK key);

    protected abstract Resume getResume(SK key);

    protected abstract void deleteResume(SK key);

    protected abstract SK findKey(String uuid);

    protected abstract boolean isExist(SK searchKey);

    protected abstract List<Resume> getList();

    SK getKeyIfExist(String uuid) {
        SK searchKey = findKey(uuid);
        if (isExist(searchKey)) {
            return searchKey;
        }
        LOG.warning("Resume " + uuid + " not exist");
        throw new NotExistStorageException(uuid);
    }
}

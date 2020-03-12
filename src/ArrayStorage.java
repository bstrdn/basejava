import java.sql.Array;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int storageNumb = 0;

    void clear() {
        for (int i = 0; i < storageNumb; i++) {
            storage[i] = null;
        }
        storageNumb = 0;
    }

    void save(Resume r) {
        storage[storageNumb] = r;
        storageNumb++;
    }

    Resume get(String uuid) {
        for (int i = 0; i < storageNumb; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }

        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < storageNumb; i++) {
            if (storage[i].uuid.equals(uuid)) {
                storage[i] = storage[storageNumb - 1];
                storage[storageNumb - 1] = null;
                storageNumb--;
            }
        }

    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resumes = new Resume[storageNumb];
        resumes = Arrays.copyOf(storage, storageNumb);
        return resumes;
    }

    int size() {
        return storageNumb;
    }
}

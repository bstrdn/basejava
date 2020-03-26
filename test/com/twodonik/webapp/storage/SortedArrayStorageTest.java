package com.twodonik.webapp.storage;

import static org.junit.Assert.*;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {

    @Override
    Storage newStorage() {
        return new SortedArrayStorage();
    }
}
package com.twodonik.webapp.storage;

import static org.junit.Assert.*;

public class ArrayStorageTest extends AbstractArrayStorageTest {

    @Override
    Storage newStorage() {
        return new ArrayStorage();
    }
}
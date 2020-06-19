package com.twodonik.webapp.storage;

import com.twodonik.webapp.Config;
import com.twodonik.webapp.sql.SqlHelper;

public class SqlStorageTest extends AbstractStorageTest {

    public SqlStorageTest() {
        super(new SqlStorage(new SqlHelper(Config.get().getDbUrl(), Config.get().getDbUser(), Config.get().dbPassword())));
    }

}
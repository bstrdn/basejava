package com.twodonik.webapp.storage;

import com.twodonik.webapp.Config;
import com.twodonik.webapp.sql.SqlHelper;
import com.twodonik.webapp.storage.serializer.JsonStreamSerializer;

public class SqlStorageTest extends AbstractStorageTest {

    public SqlStorageTest() {
        super(new SqlStorage(new SqlHelper(Config.get().getDbUrl(), Config.get().getDbUser(), Config.get().dbPassword())));
    }

}
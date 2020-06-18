package com.twodonik.webapp.storage;

import java.sql.Connection;
import java.sql.SQLException;

public interface CodeBlock<T> {
    T execute(Connection conn) throws SQLException;
}

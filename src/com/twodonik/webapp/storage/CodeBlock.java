package com.twodonik.webapp.storage;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface CodeBlock<T> {
    T execute(PreparedStatement ps) throws SQLException;
}

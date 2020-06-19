package com.twodonik.webapp.sql;

import com.twodonik.webapp.exception.StorageException;
import com.twodonik.webapp.storage.CodeBlock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(String url, String user, String pass) {
        connectionFactory = () -> DriverManager.getConnection(url, user, pass);
    }

    public <T> T query(CodeBlock<T> cb) {
        try (Connection conn = connectionFactory.getConnection()) {
            return cb.execute(conn);
        } catch (SQLException throwables) {
            throw new StorageException(throwables);
        }
    }
}


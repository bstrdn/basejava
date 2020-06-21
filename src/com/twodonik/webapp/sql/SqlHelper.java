package com.twodonik.webapp.sql;

import com.twodonik.webapp.storage.SqlExecutor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(String url, String user, String pass) {
        connectionFactory = () -> DriverManager.getConnection(url, user, pass);
    }

    public void execute (String q) {
        execute(q, PreparedStatement::execute);
    }

    public <T> T execute(String q, SqlExecutor<T> cb) {
        try (Connection conn = connectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(q);
            return cb.execute(ps);
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }
}


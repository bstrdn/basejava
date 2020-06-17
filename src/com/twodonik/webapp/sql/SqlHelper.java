package com.twodonik.webapp.sql;

import com.twodonik.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    public final ConnectionFactory connectionFactory;

    public SqlHelper(String db, String login, String password) {
        connectionFactory = () -> DriverManager.getConnection(db, login, password);
    }

    public void delete(String s) {
        try (Connection conn = connectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(s)) {
            ps.execute();
        } catch (
                SQLException throwables) {
            throw new StorageException(throwables);
        }
    }


}


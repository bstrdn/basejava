package com.twodonik.webapp.storage;

import com.twodonik.webapp.model.Resume;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface CodeBlock {
Resume execute(Connection conn) throws SQLException;
}

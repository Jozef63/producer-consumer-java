package org.example.db.connector;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface JdbcConnector {
    Boolean statement(String sql) throws SQLException;
    ResultSet query(String sql) throws SQLException;
}

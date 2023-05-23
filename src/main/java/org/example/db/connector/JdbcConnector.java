package org.example.db.connector;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface JdbcConnector {
    Boolean executeStatement(String sql) throws SQLException;
    ResultSet fetchQuery(String sql) throws SQLException;
}

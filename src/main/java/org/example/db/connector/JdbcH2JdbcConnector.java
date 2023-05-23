package org.example.db.connector;

import java.sql.*;

public class JdbcH2JdbcConnector implements JdbcConnector {

    private final Connection connection;
    private static JdbcH2JdbcConnector instance = null;

    public static JdbcH2JdbcConnector getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new JdbcH2JdbcConnector();
        }
        return instance;
    }

    private JdbcH2JdbcConnector() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        String jdbcURL = "jdbc:h2:mem:test";
        connection = DriverManager.getConnection(jdbcURL);
        System.out.println("Connected to H2 in-memory database.");
        connection.createStatement().execute("CREATE TABLE SUSERS (USER_ID INT,USER_GUID VARCHAR(20),USER_NAME VARCHAR(50),PRIMARY KEY (USER_GUID));");
    }

    public Boolean executeStatement(String sql) throws SQLException {
        int rows = 0;
        try (Statement statement = connection.createStatement()) {
            rows = statement.executeUpdate(sql);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return rows > 0;
    }

    public ResultSet fetchQuery(String sql) throws SQLException {
        ResultSet resultSet = null;
        Statement statement = connection.createStatement();
        try {
            resultSet = statement.executeQuery(sql);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return resultSet;
    }

    @Override
    protected void finalize() throws Throwable {
        connection.close();
        System.out.println("Connection to H2 in-memory database closed.");
        super.finalize();
    }
}

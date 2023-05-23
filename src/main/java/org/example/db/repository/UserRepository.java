package org.example.db.repository;

import org.example.db.connector.JdbcConnector;
import org.example.db.model.UserEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements Repository<UserEntity>{

    private final JdbcConnector dbConnector;
    public UserRepository (JdbcConnector dbConnector) {
        this.dbConnector = dbConnector;
    }
    @Override
    public UserEntity create(UserEntity userEntity) {
        String createSqlStatement = String.format("INSERT INTO SUSERS (USER_ID, USER_GUID, USER_NAME) " +
                "VALUES (%s, '%s', '%s');", userEntity.getId(), userEntity.getGuid(), userEntity.getName());
        try {
            if (dbConnector.statement(createSqlStatement)) {
                return userEntity;
            } else {
                return null;
            }
        } catch (SQLException sqlException) {
            System.err.printf("Creating user %s with statement %s threw following exception %s!%n",
                    userEntity, createSqlStatement, sqlException);
        }
        return null;
    }

    @Override
    public List<UserEntity> getAll() {
        String querySqlStatement = "SELECT USER_ID, USER_GUID, USER_NAME FROM SUSERS;";
        List<UserEntity> result = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            resultSet = dbConnector.query(querySqlStatement);
            while (resultSet.next()) {
                UserEntity userEntity = new UserEntity(resultSet.getString("USER_NAME"),
                        resultSet.getInt("USER_ID"), resultSet.getString("USER_GUID"));
               result.add(userEntity);
            }
        } catch (SQLException sqlException) {
            System.err.printf("Getting users with query %s threw following exception %s!%n",
                    querySqlStatement, sqlException);
            sqlException.printStackTrace();
            return null;
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    @Override
    public Boolean deleteAll() {
        String deleteSqlStatement = "DELETE FROM SUSERS;";
        try {
            return dbConnector.statement(deleteSqlStatement);
        } catch (SQLException sqlException) {
            System.err.printf("Deleting all users with statement %s threw following exception %s!%n",
                    deleteSqlStatement, sqlException);
        }
        return false;
    }
}

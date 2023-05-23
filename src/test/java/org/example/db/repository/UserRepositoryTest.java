package org.example.db.repository;

import org.example.db.connector.JdbcConnector;
import org.example.db.model.UserEntity;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserRepositoryTest {

    private UserRepository userRepository;

    @Mock
    JdbcConnector dbConnector;

    @Before
    public void init() {
        dbConnector = mock();
        userRepository = new UserRepository(dbConnector);
    }

    @Test
    public void createSuccessTest() throws SQLException {
        when(dbConnector.executeStatement(any())).thenReturn(true);
        UserEntity userEntity = new UserEntity("name", 1, "guid");
        userRepository.create(new UserEntity("name", 1, "guid"));
        String expected = String.format("INSERT INTO SUSERS (USER_ID, USER_GUID, USER_NAME) " +
                "VALUES (%s, '%s', '%s');", userEntity.getId(), userEntity.getGuid(), userEntity.getName());
       verify(dbConnector, times(1)).executeStatement(expected);
    }

//                      . negative scenarios
//                      . all other public methods
}

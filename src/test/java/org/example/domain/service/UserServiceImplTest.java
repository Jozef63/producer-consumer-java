package org.example.domain.service;

import org.assertj.core.api.Assertions;
import org.example.domain.model.User;
import org.example.message.command.CreateUserCommand;
import org.example.message.query.GetAllUsersQuery;
import org.example.message.query.Query;
import org.example.message.query.response.GetAllUsersQueryResponse;
import org.example.message.service.MessagingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private UserService userService;

    @Mock
    MessagingService messagingService;

    @Captor
    ArgumentCaptor<CreateUserCommand> cmdCaptor;

    @Captor
    ArgumentCaptor<Query> queryCaptor;

    private User user;

    @Before
    public void init() {
        messagingService = mock();
        user = new User("name", "guid", 1);
        this.userService = new UserServiceImpl(messagingService);
    }

    @Test
    public void addUserSuccessTest() {
        userService.add(user);
        verify(messagingService).send(cmdCaptor.capture());
        CreateUserCommand cmd = cmdCaptor.getValue();
        Assertions.assertThat(cmd.getGuid()).isEqualTo(user.getGuid());
        Assertions.assertThat(cmd.getId()).isEqualTo(user.getId());
        Assertions.assertThat(cmd.getName()).isEqualTo(user.getName());
    }

    @Test
    public void getAllUsersTest() throws ExecutionException, InterruptedException, TimeoutException {
        List<User> users = new ArrayList<>();
        users.add(user);
        GetAllUsersQueryResponse response = new GetAllUsersQueryResponse(users, new GetAllUsersQuery());
        when(messagingService.sendAndRecieve(any())).thenReturn(CompletableFuture.completedFuture(response));
        List<User> actual = userService.getAll();
        verify(messagingService).sendAndRecieve(queryCaptor.capture());
        Assertions.assertThat(queryCaptor.getValue()).isInstanceOf(GetAllUsersQuery.class);
        Assertions.assertThat(actual).usingRecursiveAssertion()
                .isEqualTo(users);
    }
}

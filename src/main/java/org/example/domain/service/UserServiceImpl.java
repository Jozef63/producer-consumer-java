package org.example.domain.service;

import org.example.message.service.MessagingService;
import org.example.domain.model.User;
import org.example.message.command.CreateUserCommand;
import org.example.message.command.DeleteAllUsersCommand;
import org.example.message.query.GetAllUsersQuery;
import org.example.message.query.response.GetAllUsersQueryResponse;

import java.util.List;
import java.util.concurrent.*;

public class UserServiceImpl implements UserService {

    private final MessagingService messagingService;

    public UserServiceImpl(MessagingService messagingService) {
        this.messagingService = messagingService;
    }

    @Override
    public void add(User user) {
        messagingService.send(new CreateUserCommand(user.getId(), user.getName(), user.getGuid()));
    }

    @Override
    public void deleteAll() {
        messagingService.send(new DeleteAllUsersCommand());
    }

    @Override
    public List<User> getAll() throws ExecutionException, InterruptedException, TimeoutException {
        GetAllUsersQueryResponse response = messagingService
                .<GetAllUsersQueryResponse>sendAndRecieve(
                        new GetAllUsersQuery()).get(1000L, TimeUnit.MILLISECONDS);
        return response.getUsers();
    }
}

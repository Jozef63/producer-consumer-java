package org.example.domain.service;

import org.example.domain.model.User;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface UserService {

    void add(User user);

    void deleteAll();

    List<User> getAll() throws ExecutionException, InterruptedException, TimeoutException;
}

package org.example.message.query.response;

import org.example.domain.model.User;
import org.example.message.query.GetAllUsersQuery;

import java.util.List;

public class GetAllUsersQueryResponse implements QueryResponse {
    private final List<User> users;
    private final GetAllUsersQuery initialQuery;

    public GetAllUsersQueryResponse(List<User> users, GetAllUsersQuery initialQuery) {
        this.users = users;
        this.initialQuery = initialQuery;
    }

    public List<User> getUsers() {
        return users;
    }

    public GetAllUsersQuery getInitialQuery() {
        return initialQuery;
    }
}

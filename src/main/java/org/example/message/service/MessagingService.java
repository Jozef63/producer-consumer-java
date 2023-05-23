package org.example.message.service;

import org.example.message.Message;
import org.example.message.command.Command;
import org.example.message.query.Query;
import org.example.message.query.response.QueryResponse;

import java.util.concurrent.Future;

public interface MessagingService {

    void send(Command command);

   <T extends QueryResponse> Future<T> sendAndRecieve(Query query);
}

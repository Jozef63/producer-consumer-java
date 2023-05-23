package org.example.message.service;

import org.example.message.Message;
import org.example.message.command.Command;
import org.example.message.query.Query;
import org.example.message.query.response.GetAllUsersQueryResponse;
import org.example.message.query.response.QueryResponse;
import org.example.queue.ConsumerService;
import org.example.queue.Producer;

import java.beans.PropertyChangeEvent;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class MessagingServicePlainImpl implements MessagingService {
    private final Producer producer;
    private final ConsumerService consumerService;

    public MessagingServicePlainImpl(Producer producer, ConsumerService consumerService) {
        this.producer = producer;
        this.consumerService = consumerService;
    }

    @Override
    public void send(Command command) {
        producer.produce(command);
    }

    @Override
    public Future<QueryResponse> sendAndRecieve(Query query) {
        producer.produce(query);
        ExecutorCompletionService service =
                new ExecutorCompletionService(Executors.newWorkStealingPool());
        return service.submit(() -> {
            AtomicReference<Message> response = new AtomicReference<>();
            AtomicBoolean recievedResponse = new AtomicBoolean(false);
            consumerService.addListener((PropertyChangeEvent evt) -> {
                if (evt.getNewValue() instanceof GetAllUsersQueryResponse &&
                        ((GetAllUsersQueryResponse) evt.getNewValue()).getInitialQuery().equals(query)) {
                    response.set((GetAllUsersQueryResponse) evt.getNewValue());
                    recievedResponse.set(true);
                }
            });
            while (!recievedResponse.get()) {
                Thread.sleep(300);
            }
            return response.get();
        });
    }
}

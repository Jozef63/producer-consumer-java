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
    private static final long POLL_INTERVAL_MILIS = 300L;
    private static final int POLL_MAX_RETRIES = 10;
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
            registerResponseListener(query, response, recievedResponse);
            waitForResponse(recievedResponse);
            return response.get();
        });
    }

    private void registerResponseListener(Query query, AtomicReference<Message> response, AtomicBoolean recievedResponse) {
        consumerService.addListener((PropertyChangeEvent evt) -> {
            if (evt.getNewValue() instanceof GetAllUsersQueryResponse &&
                    ((GetAllUsersQueryResponse) evt.getNewValue()).getInitialQuery().equals(query)) {
                response.set((GetAllUsersQueryResponse) evt.getNewValue());
                recievedResponse.set(true);
            }
        });
    }

    private static void waitForResponse(AtomicBoolean recievedResponse) throws InterruptedException {
        int retries = 0;
        while (!recievedResponse.get() && retries < POLL_MAX_RETRIES) {
            Thread.sleep(POLL_INTERVAL_MILIS);
            retries++;
        }
    }
}

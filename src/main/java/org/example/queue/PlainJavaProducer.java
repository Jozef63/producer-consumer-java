package org.example.queue;

import org.example.message.Message;

public class PlainJavaProducer implements Producer {
    @Override
    public void produce(Message message) {
        Queue.queue.add(message);
    }
}

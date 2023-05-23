package org.example.queue;

import org.example.message.Message;

public class PlainJavaConsumer implements Consumer {
    @Override
    public Message consumeNewMessages() {
        return Queue.queue.poll();
    }
}

package org.example.queue;

import org.example.message.Message;

public interface Producer {
    void produce(Message message);
}

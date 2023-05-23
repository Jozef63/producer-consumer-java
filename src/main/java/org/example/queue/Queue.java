package org.example.queue;

import org.example.message.Message;

import java.util.concurrent.ArrayBlockingQueue;

class Queue {
    static final ArrayBlockingQueue<Message> queue = new ArrayBlockingQueue<>(10);
}

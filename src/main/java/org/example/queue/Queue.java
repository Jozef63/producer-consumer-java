package org.example.queue;

import org.example.message.Message;

import java.util.concurrent.ArrayBlockingQueue;

class Queue {
    static ArrayBlockingQueue <Message> queue = new ArrayBlockingQueue<>(10);
}

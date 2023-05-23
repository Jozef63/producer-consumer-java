package org.example.queue;

import org.example.message.Message;

public interface Consumer {
    Message consumeNewMessages();
}

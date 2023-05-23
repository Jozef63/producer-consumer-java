package org.example.queue;

import org.example.message.Message;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.Executors;

public class ConsumerService {

    private final Consumer consumer;
    private volatile boolean shouldRun = false;
    private Message message;
    private PropertyChangeSupport queueSupport;

    public ConsumerService(Consumer consumer) {
        this.consumer = consumer;
        this.queueSupport = new PropertyChangeSupport(this);
    }

    public void startConsumer() {
        shouldRun = true;
        Executors.defaultThreadFactory().newThread(() -> {
            while (shouldRun) {
                processMessage(consumer.consumeNewMessages());
                try {
                    Thread.sleep(200L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        System.out.println("Starting consumer.");
    }

    public void addListener(PropertyChangeListener propertyChangeListener) {
        this.queueSupport.addPropertyChangeListener(propertyChangeListener);
    }

    public void stopConsumer() {
        System.out.println("stopping consumer.");
        shouldRun = false;
    }

    private void processMessage(Message message) {
        this.queueSupport.firePropertyChange("message", this.message, message);
        this.message = message;
    }
}

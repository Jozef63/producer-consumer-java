package org.example;

import org.example.message.service.MessagingServicePlainImpl;
import org.example.queue.Consumer;
import org.example.queue.ConsumerService;
import org.example.queue.PlainJavaConsumer;
import org.example.db.connector.JdbcH2JdbcConnector;
import org.example.db.repository.UserRepository;
import org.example.domain.model.User;
import org.example.domain.service.UserService;
import org.example.domain.service.UserServiceImpl;
import org.example.queue.PlainJavaProducer;
import org.example.queue.Producer;
import org.example.message.listener.CreateUserListener;
import org.example.message.listener.DeleteAllUsersListener;
import org.example.message.listener.GetAllUsersListener;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException, SQLException, ClassNotFoundException {
        Consumer consumer = new PlainJavaConsumer();
        ConsumerService consumerService = new ConsumerService(consumer);
        consumerService.startConsumer();
        Producer producer = new PlainJavaProducer();
        UserService userService = new UserServiceImpl(
                new MessagingServicePlainImpl(producer, consumerService));
        configureListener(consumerService, producer);
        System.out.println("Configuration Done.");


        userService.add(new User("Robert", "a1", 1));
        userService.add(new User("Martin", "a2", 2));
        System.out.println(userService.getAll());
        userService.deleteAll();
        System.out.println(userService.getAll());
        consumerService.stopConsumer();
        System.exit(0);
    }

    private static void configureListener(ConsumerService consumerService, Producer producer) throws SQLException, ClassNotFoundException {
        UserRepository repository = new UserRepository(JdbcH2JdbcConnector.getInstance());
        consumerService.addListener(new CreateUserListener(repository));
        consumerService.addListener(new DeleteAllUsersListener(repository));
        consumerService.addListener(new GetAllUsersListener(repository, producer));
    }
}

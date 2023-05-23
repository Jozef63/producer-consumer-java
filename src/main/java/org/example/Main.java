package org.example;

import org.example.db.connector.JdbcH2JdbcConnector;
import org.example.db.repository.UserRepository;
import org.example.domain.model.User;
import org.example.domain.service.UserService;
import org.example.domain.service.UserServiceImpl;
import org.example.message.listener.CreateUserListener;
import org.example.message.listener.DeleteAllUsersListener;
import org.example.message.listener.GetAllUsersListener;
import org.example.message.service.MessagingServicePlainImpl;
import org.example.queue.*;

import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class Main {
    public static void main(String[] args) throws InterruptedException, TimeoutException, SQLException, ClassNotFoundException, ExecutionException {
        ConsumerService consumerService = configureConsumerService();
        Producer producer = configureProducer(consumerService);
        UserService userService = configureUserService(consumerService, producer);
        System.out.println("Application Configuration Finished.");
        applicationDummyUsage(consumerService, userService);
    }

    private static UserService configureUserService(ConsumerService consumerService, Producer producer) {
        return new UserServiceImpl(
                new MessagingServicePlainImpl(producer, consumerService));
    }

    private static Producer configureProducer(ConsumerService consumerService) throws SQLException, ClassNotFoundException {
        Producer producer = new PlainJavaProducer();
        registerListener(consumerService, producer);
        return producer;
    }

    private static ConsumerService configureConsumerService() {
        Consumer consumer = new PlainJavaConsumer();
        ConsumerService consumerService = new ConsumerService(consumer);
        consumerService.startConsumer();
        return consumerService;
    }

    private static void applicationDummyUsage(ConsumerService consumerService, UserService userService)
            throws ExecutionException, InterruptedException, TimeoutException {
        System.out.println("Application Running.");
        userService.add(new User("Robert", "a1", 1));
        userService.add(new User("Martin", "a2", 2));
        System.out.println(userService.getAll());
        userService.deleteAll();
        System.out.println(userService.getAll());
        consumerService.stopConsumer();
    }

    private static void registerListener(ConsumerService consumerService, Producer producer) throws SQLException, ClassNotFoundException {
        UserRepository repository = new UserRepository(JdbcH2JdbcConnector.getInstance());
        consumerService.addListener(new CreateUserListener(repository));
        consumerService.addListener(new DeleteAllUsersListener(repository));
        consumerService.addListener(new GetAllUsersListener(repository, producer));
    }
}

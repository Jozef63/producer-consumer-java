package org.example.message.listener;

import org.example.db.repository.UserRepository;
import org.example.mapper.UserMapper;
import org.example.queue.Producer;
import org.example.message.query.GetAllUsersQuery;
import org.example.message.query.response.GetAllUsersQueryResponse;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.stream.Collectors;

public class GetAllUsersListener implements PropertyChangeListener {
    private final UserRepository userRepository;
    private final Producer producer;

    public GetAllUsersListener(UserRepository userRepository, Producer producer) {
        this.userRepository = userRepository;
        this.producer = producer;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() instanceof GetAllUsersQuery) {
            producer.produce(
                    new GetAllUsersQueryResponse(
                            userRepository.getAll()
                                    .stream().map(UserMapper::fromEntity)
                                    .collect(Collectors.toList()), (GetAllUsersQuery) evt.getNewValue()
                    )
            );
        }
    }
}

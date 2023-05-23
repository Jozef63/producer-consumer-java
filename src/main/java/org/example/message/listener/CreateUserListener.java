package org.example.message.listener;

import org.example.db.model.UserEntity;
import org.example.db.repository.UserRepository;
import org.example.message.command.CreateUserCommand;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CreateUserListener implements PropertyChangeListener {
    private final UserRepository userRepository;

    public CreateUserListener(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() instanceof CreateUserCommand) {
            CreateUserCommand command = (CreateUserCommand) evt.getNewValue();
            userRepository.create(new UserEntity(command.getName(), command.getId(), command.getGuid()));
        }
    }
}

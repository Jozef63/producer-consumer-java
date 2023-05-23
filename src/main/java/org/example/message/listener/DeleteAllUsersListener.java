package org.example.message.listener;

import org.example.db.repository.UserRepository;
import org.example.message.command.DeleteAllUsersCommand;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DeleteAllUsersListener implements PropertyChangeListener {
    private final UserRepository userRepository;

    public DeleteAllUsersListener(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() instanceof DeleteAllUsersCommand) {
            userRepository.deleteAll();
        }
    }
}

package org.example.message.command;

public class CreateUserCommand implements Command {
    private final int id;
    private final String name;
    private final String guid;

    public CreateUserCommand(int id, String name, String guid) {
        this.id = id;
        this.name = name;
        this.guid = guid;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGuid() {
        return guid;
    }
}

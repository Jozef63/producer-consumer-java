package org.example.domain.model;

public class User {
    private final String name;
    private final String guid;
    private final int id;

    public User(String name, String guid, int id) {
        this.name = name;
        this.guid = guid;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getGuid() {
        return guid;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", guid='" + guid + '\'' +
                ", id=" + id +
                '}';
    }
}

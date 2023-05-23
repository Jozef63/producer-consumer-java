package org.example.db.model;

public class UserEntity {

    private final String name;
    private final Integer id;
    private final String guid;

    public UserEntity(String name, Integer id, String guid) {
        this.name = name;
        this.id = id;
        this.guid = guid;
    }
    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public String getGuid() {
        return guid;
    }
}

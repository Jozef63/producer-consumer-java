package org.example.db.repository;

import java.util.List;

interface Repository<T> {

    T create(T t);
    List<T> getAll();

    Boolean deleteAll();
}

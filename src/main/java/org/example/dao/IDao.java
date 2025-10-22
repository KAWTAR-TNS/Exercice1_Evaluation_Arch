package org.example.dao;

import java.util.List;

public interface IDao<T> {
    void create(T o);
    void update(T o);
    void delete(T o);
    T getById(int id);
    List<T> getAll();
}

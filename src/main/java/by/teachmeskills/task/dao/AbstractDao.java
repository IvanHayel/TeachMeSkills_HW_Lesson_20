package by.teachmeskills.task.dao;

import by.teachmeskills.task.model.entity.Entity;
import lombok.NonNull;
import lombok.extern.java.Log;

import java.sql.Connection;
import java.util.List;

@Log
public abstract class AbstractDao<K, T extends Entity> {
    protected Connection connection;

    public abstract boolean create(T item);

    public abstract List<T> findAll();

    public abstract T findEntityById(K id);

    public abstract T update(T item);

    public abstract boolean delete(T item);

    public abstract boolean deleteEntityById(K id);

    public void setConnection(@NonNull Connection connection) {
        this.connection = connection;
    }
}
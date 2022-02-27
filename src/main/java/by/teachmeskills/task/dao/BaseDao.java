package by.teachmeskills.task.dao;

import by.teachmeskills.task.model.Entity;

import java.util.List;

public interface BaseDao<K, T extends Entity> {
    int FIRST_PREPARED_STATEMENT_PARAMETER = 1;
    int SECOND_PREPARED_STATEMENT_PARAMETER = 2;
    int THIRD_PREPARED_STATEMENT_PARAMETER = 3;
    int FOURTH_PREPARED_STATEMENT_PARAMETER = 4;
    int SQL_STATEMENT_RETURN_NOTHING = 0;

    boolean create(T entity);

    List<T> findAll();

    T findEntityById(K id);

    T update(T entity);

    boolean delete(T entity);

    boolean deleteEntityById(K id);
}
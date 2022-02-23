package by.teachmeskills.task.transaction;

import by.teachmeskills.task.dao.AbstractDao;
import lombok.NonNull;
import lombok.extern.java.Log;

import java.sql.Connection;
import java.sql.SQLException;

@Log
public class EntityTransaction {
    private Connection connection;

    public EntityTransaction(@NonNull Connection connection) {
        this.connection = connection;
    }

    public void begin(AbstractDao dao, AbstractDao... daos) {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
        dao.setConnection(connection);
        for (AbstractDao daoElement : daos)
            daoElement.setConnection(connection);
    }

    public void end() {
        if (connection == null){
            log.info("Transaction already ended.");
            return;
        }
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
        connection = null;
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
    }
}
package by.teachmeskills.task.util.manager;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.Value;
import lombok.extern.java.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

@Log
@Value
public class MySqlDriverManager implements AutoCloseable {
    @NonNull Properties databaseProperties;
    @EqualsAndHashCode.Exclude
    Connection connection;

    static {
        registerDriver();
    }

    public MySqlDriverManager(@NonNull Properties databaseProperties) {
        this.databaseProperties = (Properties) databaseProperties.clone();
        connection = setConnection();
    }

    @SneakyThrows(SQLException.class)
    private static void registerDriver() {
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        log.info("JDBC MySQL Driver successfully registered.");
    }

    @SneakyThrows(SQLException.class)
    private Connection setConnection() {
        String connectionUrl = (String) databaseProperties.remove("database.connection.URL");
        Connection connection = DriverManager.getConnection(connectionUrl, databaseProperties);
        log.info(String.format("Connected to %s", connection.getMetaData().getURL()));
        return connection;
    }

    @SneakyThrows(SQLException.class)
    public PreparedStatement prepareStatement(@NonNull String query) {
        if (connection == null || connection.isClosed()) return null;
        return connection.prepareStatement(query);
    }

    @Override
    @SneakyThrows(SQLException.class)
    public void close() {
        if (connection != null && !connection.isClosed()) connection.close();
    }
}
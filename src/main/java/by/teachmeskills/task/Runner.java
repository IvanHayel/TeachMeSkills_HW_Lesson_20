package by.teachmeskills.task;

import by.teachmeskills.task.util.manager.MySqlDriverManager;
import by.teachmeskills.task.util.visitor.MySqlScriptsVisitor;
import lombok.Cleanup;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Runner {
    private static final Path DATABASE_PROPERTY_PATH = Paths.get("src/main/resources/database.properties");
    private static final Path MYSQL_SCRIPTS_PATH = Paths.get("src/main/resources/sql");
    private static final Properties databaseProperties = new Properties();
    private static final Map<String, String> queries = new HashMap<>();

    static {
        loadDatabaseProperties();
        loadMySqlQueries();
    }

    public static void main(String[] args) {
        @Cleanup MySqlDriverManager driverManager = new MySqlDriverManager(databaseProperties);
    }

    @SneakyThrows(IOException.class)
    private static void loadDatabaseProperties() {
        @Cleanup InputStream propertiesStream = Files.newInputStream(DATABASE_PROPERTY_PATH);
        databaseProperties.load(propertiesStream);
    }

    @SneakyThrows(IOException.class)
    private static void loadMySqlQueries() {
        MySqlScriptsVisitor visitor = new MySqlScriptsVisitor(queries);
        Files.walkFileTree(MYSQL_SCRIPTS_PATH, visitor);
    }
}
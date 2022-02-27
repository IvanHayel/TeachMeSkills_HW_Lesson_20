package by.teachmeskills.task;

import by.teachmeskills.task.dao.LocationDao;
import by.teachmeskills.task.dao.StudentDao;
import by.teachmeskills.task.model.Location;
import by.teachmeskills.task.model.mysql.MySqlQueries;
import by.teachmeskills.task.util.manager.MySqlDriverManager;
import by.teachmeskills.task.util.parser.MySqlQueriesXmlParser;
import by.teachmeskills.task.util.parser.XmlParser;
import lombok.Cleanup;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Runner {
    private static final Path DATABASE_PROPERTY_PATH = Paths.get("src/main/resources/database.properties");
    private static final Path MYSQL_QUERIES_PATH = Paths.get("src/main/resources/queries.xml");
    private static final Properties databaseProperties = new Properties();

    static {
        loadDatabaseProperties();
    }

    public static void main(String[] args) {
        @Cleanup MySqlDriverManager driverManager = new MySqlDriverManager(databaseProperties);
        XmlParser<MySqlQueries> parser = new MySqlQueriesXmlParser();
        MySqlQueries queries = parser.jaxbEntityFromXml(MYSQL_QUERIES_PATH);
    }

    @SneakyThrows(IOException.class)
    private static void loadDatabaseProperties() {
        @Cleanup InputStream propertiesStream = Files.newInputStream(DATABASE_PROPERTY_PATH);
        databaseProperties.load(propertiesStream);
    }
}
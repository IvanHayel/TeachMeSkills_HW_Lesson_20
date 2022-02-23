package by.teachmeskills.task;

import by.teachmeskills.task.service.LocationService;
import by.teachmeskills.task.service.StudentService;
import by.teachmeskills.task.service.impl.LocationServiceImpl;
import by.teachmeskills.task.service.impl.StudentServiceImpl;
import lombok.extern.java.Log;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Create a Java application to get a list of all students in a group.
 * Create a table with the cities where the students live.
 * Display information about each student in the group and his hometown.
 * Provide operations for adding and removing cities and students.
 */

@Log
public class Runner {
    private static final Path PROPERTY_PATH = Paths.get("src/main/resources/app.properties");
    private static final String JDBC_CONNECTION_URL = "jdbc:mysql://127.0.0.1:3306/course";

    private static Properties properties = new Properties();

    static {
        loadProperties();
    }

    public static void main(String[] args) {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }

        try (Connection connection = DriverManager.getConnection(JDBC_CONNECTION_URL, properties)) {
            StudentService studentService = new StudentServiceImpl(connection);
//            Student studentWithThirdId = studentService.getStudentById(3);
//            System.out.println(studentWithThirdId);
            /*
             * StudentService usage here ...
             */

            LocationService locationService = new LocationServiceImpl(connection);
//            locationService.addLocation(new Location(3, "Belarus", "Grodno"));
//            System.out.println(locationService.getLocationList());
            /*
             * LocationService usage here ...
             */
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
    }

    private static void loadProperties() {
        try (InputStream propertiesStream = Files.newInputStream(PROPERTY_PATH)) {
            properties.load(propertiesStream);
        } catch (IOException e) {
            log.warning(e.getMessage());
        }
    }
}
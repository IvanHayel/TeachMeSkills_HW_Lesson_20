package by.teachmeskills.task.dao;

import by.teachmeskills.task.model.location.Location;
import by.teachmeskills.task.model.student.Student;
import lombok.NonNull;
import lombok.extern.java.Log;
import org.intellij.lang.annotations.Language;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Log
public class StudentDao extends AbstractDao<Integer, Student> {
    @Language("SQL")
    private static final String INSERT_STUDENT =
            "INSERT INTO student " +
                    "(name, surname, location_id) " +
                    "VALUES (?, ?, ?) ";
    @Language("SQL")
    private static final String FIND_ALL_STUDENTS =
            "SELECT s.id AS student_id, name, surname, location_id, country, city " +
                    "FROM student s " + "JOIN location l " +
                    "ON (s.location_id = l.id) ";
    @Language("SQL")
    private static final String FIND_STUDENT_BY_ID =
            FIND_ALL_STUDENTS +
                    "WHERE s.id = ? ";
    @Language("SQL")
    private static final String UPDATE_STUDENT =
            "UPDATE student " +
                    "SET name = ?, surname = ?, location_id = ? " +
                    "WHERE id = ? ";
    @Language("SQL")
    private static final String DELETE_STUDENT =
            "DELETE from student " +
                    "WHERE name LIKE ? and surname LIKE ? and location_id = ? ";
    @Language("SQL")
    private static final String DELETE_STUDENT_BY_ID =
            "DELETE from student " +
                    "WHERE id = ? ";

    @Override
    public boolean create(Student item) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_STUDENT)) {
            statement.setString(1, item.getName());
            statement.setString(2, item.getSurname());
            statement.setInt(3, item.getLocation().getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            log.warning(e.getMessage());
            return false;
        }
    }

    @Override
    public List<Student> findAll() {
        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_STUDENTS)) {
            ResultSet resultSet = statement.executeQuery();
            List<Student> studentList = new ArrayList<>();
            while (resultSet.next()) {
                Student studentOccurrence = getStudentOccurrence(resultSet);
                studentList.add(studentOccurrence);
            }
            return studentList;
        } catch (SQLException e) {
            log.warning(e.getMessage());
            return Collections.emptyList();
        }
    }

    private Student getStudentOccurrence(@NonNull ResultSet resultSet) throws SQLException {
        int locationId = resultSet.getInt("location_id");
        String country = resultSet.getString("country");
        String city = resultSet.getString("city");
        Location studentLocation = new Location(locationId, country, city);
        int studentId = resultSet.getInt("student_id");
        String name = resultSet.getString("name");
        String surname = resultSet.getString("surname");
        return new Student(studentId, name, surname, studentLocation);
    }

    @Override
    public Student findEntityById(Integer id) {
        try (PreparedStatement statement = connection.prepareStatement(FIND_STUDENT_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return getStudentOccurrence(resultSet);
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
        return null;
    }

    @Override
    public Student update(Student item) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_STUDENT)) {
            statement.setString(1, item.getName());
            statement.setString(2, item.getSurname());
            statement.setInt(3, item.getLocation().getId());
            statement.setInt(4, item.getId());
            statement.executeUpdate();
            return item;
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean delete(Student item) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_STUDENT)) {
            statement.setString(1, item.getName());
            statement.setString(2, item.getSurname());
            statement.setInt(3, item.getLocation().getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteEntityById(Integer id) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_STUDENT_BY_ID)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
        return false;
    }
}
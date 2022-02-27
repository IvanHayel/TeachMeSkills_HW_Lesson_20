package by.teachmeskills.task.dao;

import by.teachmeskills.task.model.Location;
import by.teachmeskills.task.model.Student;
import by.teachmeskills.task.model.mysql.MySqlQueries;
import by.teachmeskills.task.util.manager.MySqlDriverManager;
import lombok.Cleanup;
import lombok.NonNull;
import lombok.Value;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Value
public class StudentDao implements BaseDao<Integer, Student> {
    @NonNull MySqlDriverManager mySqlDriverManager;
    @NonNull MySqlQueries queries;

    @Override
    public boolean create(@NonNull Student student) {
        int state = SQL_STATEMENT_RETURN_NOTHING;
        @NonNull String query = queries.getQuery("insert.student");
        try {
            @Cleanup PreparedStatement statement = mySqlDriverManager.prepareStatement(query);
            statement.setString(FIRST_PREPARED_STATEMENT_PARAMETER, student.getName());
            statement.setString(SECOND_PREPARED_STATEMENT_PARAMETER, student.getSurname());
            statement.setInt(THIRD_PREPARED_STATEMENT_PARAMETER, student.getLocation().getId());
            state = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return state != SQL_STATEMENT_RETURN_NOTHING;
    }

    @Override
    public List<Student> findAll() {
        @NonNull String query = queries.getQuery("find-all.students");
        try {
            @Cleanup PreparedStatement statement = mySqlDriverManager.prepareStatement(query);
            @Cleanup ResultSet resultSet = statement.executeQuery();
            List<Student> studentList = new ArrayList<>();
            while (resultSet.next()) {
                Student studentOccurrence = getStudentOccurrence(resultSet);
                studentList.add(studentOccurrence);
            }
            return studentList;
        } catch (SQLException e) {
            e.printStackTrace();
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
    public Student findEntityById(@NonNull Integer id) {
        @NonNull String query = queries.getQuery("find-by-id.student");
        try {
            @Cleanup PreparedStatement statement = mySqlDriverManager.prepareStatement(query);
            statement.setInt(FIRST_PREPARED_STATEMENT_PARAMETER, id);
            @Cleanup ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) return getStudentOccurrence(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Student update(@NonNull Student student) {
        int state = SQL_STATEMENT_RETURN_NOTHING;
        @NonNull String query = queries.getQuery("update.student");
        try {
            @Cleanup PreparedStatement statement = mySqlDriverManager.prepareStatement(query);
            statement.setString(FIRST_PREPARED_STATEMENT_PARAMETER, student.getName());
            statement.setString(SECOND_PREPARED_STATEMENT_PARAMETER, student.getSurname());
            statement.setInt(THIRD_PREPARED_STATEMENT_PARAMETER, student.getLocation().getId());
            statement.setInt(FOURTH_PREPARED_STATEMENT_PARAMETER, student.getId());
            state = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return state == SQL_STATEMENT_RETURN_NOTHING ? null : student;
    }

    @Override
    public boolean delete(@NonNull Student student) {
        int state = SQL_STATEMENT_RETURN_NOTHING;
        @NonNull String query = queries.getQuery("delete.student");
        try {
            @Cleanup PreparedStatement statement = mySqlDriverManager.prepareStatement(query);
            statement.setString(FIRST_PREPARED_STATEMENT_PARAMETER, student.getName());
            statement.setString(SECOND_PREPARED_STATEMENT_PARAMETER, student.getSurname());
            statement.setInt(THIRD_PREPARED_STATEMENT_PARAMETER, student.getLocation().getId());
            state = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return state != SQL_STATEMENT_RETURN_NOTHING;
    }

    @Override
    public boolean deleteEntityById(@NonNull Integer id) {
        int state = SQL_STATEMENT_RETURN_NOTHING;
        @NonNull String query = queries.getQuery("delete-by-id.student");
        try {
            @Cleanup PreparedStatement statement = mySqlDriverManager.prepareStatement(query);
            statement.setInt(FIRST_PREPARED_STATEMENT_PARAMETER, id);
            state = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return state != SQL_STATEMENT_RETURN_NOTHING;
    }
}
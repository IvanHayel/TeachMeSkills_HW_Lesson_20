package by.teachmeskills.task.dao;

import by.teachmeskills.task.model.Location;
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
public class LocationDao implements BaseDao<Integer, Location> {
    @NonNull MySqlDriverManager mySqlDriverManager;
    @NonNull MySqlQueries queries;

    @Override
    public boolean create(@NonNull Location location) {
        int state = SQL_STATEMENT_RETURN_NOTHING;
        @NonNull String query = queries.getQuery("insert.student");
        try {
            @Cleanup PreparedStatement statement = mySqlDriverManager.prepareStatement(query);
            statement.setInt(FIRST_PREPARED_STATEMENT_PARAMETER, location.getId());
            statement.setString(SECOND_PREPARED_STATEMENT_PARAMETER, location.getCountry());
            statement.setString(THIRD_PREPARED_STATEMENT_PARAMETER, location.getCity());
            state = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return state != SQL_STATEMENT_RETURN_NOTHING;
    }

    @Override
    public List<Location> findAll() {
        @NonNull String query = queries.getQuery("find-all.locations");
        try {
            @Cleanup PreparedStatement statement = mySqlDriverManager.prepareStatement(query);
            @Cleanup ResultSet resultSet = statement.executeQuery();
            List<Location> locationList = new ArrayList<>();
            while (resultSet.next()) {
                Location locationOccurrence = getLocationOccurrence(resultSet);
                locationList.add(locationOccurrence);
            }
            return locationList;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private Location getLocationOccurrence(@NonNull ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String country = resultSet.getString("country");
        String city = resultSet.getString("city");
        return new Location(id, country, city);
    }

    @Override
    public Location findEntityById(@NonNull Integer id) {
        @NonNull String query = queries.getQuery("find-by-id.location");
        try {
            @Cleanup PreparedStatement statement = mySqlDriverManager.prepareStatement(query);
            statement.setInt(FIRST_PREPARED_STATEMENT_PARAMETER, id);
            @Cleanup ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) return getLocationOccurrence(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Location update(@NonNull Location location) {
        int state = SQL_STATEMENT_RETURN_NOTHING;
        @NonNull String query = queries.getQuery("update.location");
        try {
            @Cleanup PreparedStatement statement = mySqlDriverManager.prepareStatement(query);
            statement.setString(FIRST_PREPARED_STATEMENT_PARAMETER, location.getCountry());
            statement.setString(SECOND_PREPARED_STATEMENT_PARAMETER, location.getCity());
            statement.setInt(THIRD_PREPARED_STATEMENT_PARAMETER, location.getId());
            state = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return state == SQL_STATEMENT_RETURN_NOTHING ? null : location;
    }

    @Override
    public boolean delete(@NonNull Location location) {
        int state = SQL_STATEMENT_RETURN_NOTHING;
        @NonNull String query = queries.getQuery("delete.location");
        try {
            @Cleanup PreparedStatement statement = mySqlDriverManager.prepareStatement(query);
            statement.setInt(FIRST_PREPARED_STATEMENT_PARAMETER, location.getId());
            statement.setString(SECOND_PREPARED_STATEMENT_PARAMETER, location.getCountry());
            statement.setString(THIRD_PREPARED_STATEMENT_PARAMETER, location.getCity());
            state = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return state != SQL_STATEMENT_RETURN_NOTHING;
    }

    @Override
    public boolean deleteEntityById(@NonNull Integer id) {
        int state = SQL_STATEMENT_RETURN_NOTHING;
        @NonNull String query = queries.getQuery("delete-by-id.location");
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
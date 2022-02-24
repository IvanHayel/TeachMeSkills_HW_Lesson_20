package by.teachmeskills.task.dao;

import by.teachmeskills.task.model.location.Location;
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
public class LocationDao extends AbstractDao<Integer, Location>{
    @Language("SQL")
    private static final String INSERT_LOCATION =
            "INSERT INTO location " +
                    "(id, country, city) " +
                    "VALUES (?, ?, ?) ";
    @Language("SQL")
    private static final String FIND_ALL_LOCATIONS =
            "SELECT id, country, city " +
                    "FROM location ";
    @Language("SQL")
    private static final String FIND_LOCATION_BY_ID =
            FIND_ALL_LOCATIONS +
                    "WHERE id = ? ";
    @Language("SQL")
    private static final String UPDATE_LOCATION =
            "UPDATE location " +
                    "SET country = ?, city = ? " +
                    "WHERE id = ? ";
    @Language("SQL")
    private static final String DELETE_LOCATION =
            "DELETE from location " +
                    "WHERE id = ? and country LIKE ? and city LIKE ? ";
    @Language("SQL")
    private static final String DELETE_LOCATION_BY_ID =
            "DELETE from location " +
                    "WHERE id = ? ";

    @Override
    public boolean create(Location item) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_LOCATION)) {
            statement.setInt(1, item.getId());
            statement.setString(2, item.getCountry());
            statement.setString(3, item.getCity());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            log.warning(e.getMessage());
            return false;
        }
    }

    @Override
    public List<Location> findAll() {
        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_LOCATIONS)) {
            ResultSet resultSet = statement.executeQuery();
            List<Location> locationList = new ArrayList<>();
            while (resultSet.next()) {
                Location locationOccurrence = getLocationOccurrence(resultSet);
                locationList.add(locationOccurrence);
            }
            return locationList;
        } catch (SQLException e) {
            log.warning(e.getMessage());
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
    public Location findEntityById(Integer id) {
        try (PreparedStatement statement = connection.prepareStatement(FIND_LOCATION_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return getLocationOccurrence(resultSet);
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
        return null;
    }

    @Override
    public Location update(Location item) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_LOCATION)) {
            statement.setString(1, item.getCountry());
            statement.setString(2, item.getCity());
            statement.setInt(3, item.getId());
            statement.executeUpdate();
            return item;
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean delete(Location item) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_LOCATION)) {
            statement.setInt(1, item.getId());
            statement.setString(2, item.getCountry());
            statement.setString(3, item.getCity());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteEntityById(Integer id) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_LOCATION_BY_ID)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
        return false;
    }
}
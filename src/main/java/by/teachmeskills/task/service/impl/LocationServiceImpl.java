package by.teachmeskills.task.service.impl;

import by.teachmeskills.task.dao.LocationDao;
import by.teachmeskills.task.model.location.Location;
import by.teachmeskills.task.service.LocationService;
import by.teachmeskills.task.transaction.EntityTransaction;
import lombok.NonNull;
import lombok.extern.java.Log;

import java.sql.Connection;
import java.util.List;

@Log
public record LocationServiceImpl(Connection connection) implements LocationService {
    public LocationServiceImpl(@NonNull Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean addLocation(@NonNull Location location) {
        LocationDao locationDao = new LocationDao();
        EntityTransaction transaction = new EntityTransaction(connection);
        transaction.begin(locationDao);
        boolean transactionSuccess = locationDao.create(location);
        if (transactionSuccess) {
            transaction.commit();
            log.info(String.format("%s successfully added.", location));
        } else {
            transaction.rollback();
            log.info(String.format("Adding %s denied.", location));
        }
        transaction.end();
        return transactionSuccess;
    }

    @Override
    public List<Location> getLocationList() {
        LocationDao locationDao = new LocationDao();
        EntityTransaction transaction = new EntityTransaction(connection);
        transaction.begin(locationDao);
        List<Location> locationList = locationDao.findAll();
        boolean transactionSuccess = !locationList.isEmpty();
        if (transactionSuccess) {
            transaction.commit();
            log.info("All locations found successfully.");
        } else {
            transaction.rollback();
            log.info("No locations found.");
        }
        transaction.end();
        return locationList;
    }

    @Override
    public Location getLocationById(@NonNull Integer id) {
        LocationDao locationDao = new LocationDao();
        EntityTransaction transaction = new EntityTransaction(connection);
        transaction.begin(locationDao);
        Location desiredLocation = locationDao.findEntityById(id);
        boolean transactionSuccess = desiredLocation != null;
        if (transactionSuccess) {
            transaction.commit();
            log.info(String.format("Location with id = %d found successfully.", id));
        } else {
            transaction.rollback();
            log.info(String.format("There is no locations with id = %d.", id));
        }
        transaction.end();
        return desiredLocation;
    }

    @Override
    public Location updateLocation(@NonNull Location location) {
        LocationDao locationDao = new LocationDao();
        EntityTransaction transaction = new EntityTransaction(connection);
        transaction.begin(locationDao);
        Location updatedLocation = locationDao.update(location);
        boolean transactionSuccess = updatedLocation != null;
        if (transactionSuccess) {
            transaction.commit();
            log.info(String.format("%s successfully updated.", location));
        } else {
            transaction.rollback();
            log.info(String.format("Update of %s denied.", location));
        }
        transaction.end();
        return updatedLocation;
    }

    @Override
    public boolean deleteLocation(@NonNull Location location) {
        LocationDao locationDao = new LocationDao();
        EntityTransaction transaction = new EntityTransaction(connection);
        transaction.begin(locationDao);
        boolean transactionSuccess = locationDao.delete(location);
        if (transactionSuccess) {
            transaction.commit();
            log.info(String.format("%s successfully deleted.", location));
        } else {
            transaction.rollback();
            log.info(String.format("Delete of %s denied.", location));
        }
        transaction.end();
        return transactionSuccess;
    }

    @Override
    public boolean deleteLocationById(@NonNull Integer id) {
        LocationDao locationDao = new LocationDao();
        EntityTransaction transaction = new EntityTransaction(connection);
        transaction.begin(locationDao);
        boolean transactionSuccess = locationDao.deleteEntityById(id);
        if (transactionSuccess) {
            transaction.commit();
            log.info(String.format("Delete of location with id = %d successfully ended.", id));
        } else {
            transaction.rollback();
            log.info(String.format("Delete of location with id = %d denied.", id));
        }
        transaction.end();
        return transactionSuccess;
    }
}
package by.teachmeskills.task.service;

import by.teachmeskills.task.model.location.Location;

import java.util.List;

public interface LocationService {
    boolean addLocation(Location location);

    List<Location> getLocationList();

    Location getLocationById(Integer id);

    Location updateLocation(Location location);

    boolean deleteLocation(Location location);

    boolean deleteLocationById(Integer id);
}
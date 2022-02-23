package by.teachmeskills.task.model.location;

import by.teachmeskills.task.model.entity.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location extends Entity {
    private int id;
    private String country;
    private String city;
}
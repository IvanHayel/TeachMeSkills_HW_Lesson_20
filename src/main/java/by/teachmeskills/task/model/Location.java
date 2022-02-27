package by.teachmeskills.task.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location extends Entity {
    private int id;
    private String country;
    private String city;
}
package by.teachmeskills.task.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Student extends Entity {
    private int id;
    private String name;
    private String surname;
    private Location location;
}
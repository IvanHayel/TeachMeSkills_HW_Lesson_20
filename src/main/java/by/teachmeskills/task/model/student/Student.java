package by.teachmeskills.task.model.student;

import by.teachmeskills.task.model.entity.Entity;
import by.teachmeskills.task.model.location.Location;
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
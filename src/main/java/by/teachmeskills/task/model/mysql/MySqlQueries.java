package by.teachmeskills.task.model.mysql;

import by.teachmeskills.task.model.Entity;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

import java.util.HashMap;
import java.util.Map;

@Value
@AllArgsConstructor
@XmlRootElement(name = "queries")
public class MySqlQueries extends Entity {
    @XmlElement
    Map<String, String> map = new HashMap<>();

    public void putQuery(@NonNull String name, @NonNull String query) {
        map.put(name, query);
    }

    public String getQuery(@NonNull String name) {
        if (name.isEmpty() || name.isBlank()) return null;
        String query = map.get(name);
        return query;
    }
}
package by.teachmeskills.task.util.parser;

import java.nio.file.Path;

public interface XmlParser<T> {
    boolean jaxbEntityToXml(T entity, Path xmlPath);

    T jaxbEntityFromXml(Path xmlPath);
}
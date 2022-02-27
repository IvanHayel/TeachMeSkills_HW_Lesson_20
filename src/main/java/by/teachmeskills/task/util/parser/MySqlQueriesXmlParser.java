package by.teachmeskills.task.util.parser;

import by.teachmeskills.task.model.mysql.MySqlQueries;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Log
public final class MySqlQueriesXmlParser implements XmlParser<MySqlQueries> {
    @Override
    @SneakyThrows(JAXBException.class)
    public boolean jaxbEntityToXml(@NonNull MySqlQueries queries, @NonNull Path xmlPath) {
        removeXmlPathDuplicates(xmlPath);
        File xmlFile = xmlPath.toFile();
        JAXBContext jaxbContext = JAXBContext.newInstance(MySqlQueries.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(queries, xmlFile);
        boolean parsingSuccess = Files.exists(xmlPath);
        if (parsingSuccess)
            log.info(String.format("Item MySqlQueries successfully parsed to %s", xmlPath));
        return parsingSuccess;
    }

    @SneakyThrows(IOException.class)
    private void removeXmlPathDuplicates(Path xmlPath) {
        if (Files.exists(xmlPath))
            Files.delete(xmlPath);
    }

    @Override
    @SneakyThrows(JAXBException.class)
    public MySqlQueries jaxbEntityFromXml(@NonNull Path xmlPath) {
        if (Files.notExists(xmlPath)) return null;
        File xmlFile = xmlPath.toFile();
        JAXBContext jaxbContext = JAXBContext.newInstance(MySqlQueries.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        MySqlQueries queries = (MySqlQueries) unmarshaller.unmarshal(xmlFile);
        if (queries != null)
            log.info(String.format("%s successfully parsed to item MySqlQueries", xmlFile));
        return queries;
    }
}
package by.teachmeskills.task.util.visitor;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.Value;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;

@Value
public class MySqlScriptsVisitor implements FileVisitor<Path> {
    @NonNull Map<String, String> queries;

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    @SneakyThrows(IOException.class)
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        if (file.toString().endsWith(".sql")) {
            String fileName = file.getFileName().toString().strip().replaceAll("\\.sql","");
            String content = Files.readString(file);
            queries.put(fileName, content);
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }
}
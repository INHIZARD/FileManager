package ru.file.manager.model.exception;

import java.nio.file.Path;

/**
 * Исключение, отвечающее за ошибку создания директории.
 */
public class CreateDirectoryException extends Exception {
    private final Path path;
    private final String directoryName;

    public CreateDirectoryException(Path path, String directoryName) {
        super("Ошибка создания директории");
        this.path = path;
        this.directoryName = directoryName;
    }

    public Path getPath() {
        return path;
    }

    public String getDirectoryName() {
        return directoryName;
    }
}

package ru.file.manager.model.exception;

import java.nio.file.Path;

/**
 * Исключение, отвечающее за ошибку смены директории.
 */
public class ChangeDirectoryException extends Exception {
    private final Path path;
    private final String directoryName;
    private static final String message = "Ошибка смены директории";

    public ChangeDirectoryException(Path path) {
        super(message);
        this.path = path;
        this.directoryName = "↑";
    }

    public ChangeDirectoryException(Path path, String directoryName) {
        super(message);
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

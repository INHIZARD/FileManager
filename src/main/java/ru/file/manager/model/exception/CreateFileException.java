package ru.file.manager.model.exception;

import java.nio.file.Path;

/**
 * Исключение, отвечающее за ошибку создания файла.
 */
public class CreateFileException extends Exception {
    private final Path path;
    private final String fileName;

    public CreateFileException(Path path, String fileName) {
        super("Ошибка создания файла");
        this.path = path;
        this.fileName = fileName;
    }

    public Path getPath() {
        return path;
    }

    public String getFileName() {
        return fileName;
    }
}

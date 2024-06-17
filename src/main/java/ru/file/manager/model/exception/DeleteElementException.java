package ru.file.manager.model.exception;

import java.nio.file.Path;

/**
 * Исключение, отвечающее за ошибку удаления элемента.
 */
public class DeleteElementException extends Exception {
    private final Path path;
    private final String name;

    public DeleteElementException(Path path, String name) {
        super("Ошибка удаления элемента");
        this.path = path;
        this.name = name;
    }

    public Path getPath() {
        return path;
    }

    public String getName() {
        return name;
    }
}

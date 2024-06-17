package ru.file.manager.model.exception;

import java.nio.file.Path;

/**
 * Исключение, отвечающее за ошибку создания дерева файловой системы.
 */
public class CreateFileTreeException extends Exception {
    private final Path path;

    public CreateFileTreeException(Path path) {
        super("Ошибка создания дерева файлов");
        this.path = path;
    }

    public Path getPath() {
        return path;
    }
}

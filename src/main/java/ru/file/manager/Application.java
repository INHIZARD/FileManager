package ru.file.manager;

import ru.file.manager.controller.FileManagerController;
import ru.file.manager.model.service.FileManagerService;

import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * Класс для запуска приложения. Первоначальный путь передается через аргументы приложения.
 */
public class Application {
    public static void main(String[] args) {
        Path path;
        if (args.length == 0) {
            Iterable<Path> dirs = FileSystems.getDefault().getRootDirectories();
            path = dirs.iterator().next();
        } else {
            path = Path.of(args[0]);
        }
        FileManagerService fileManagerService = new FileManagerService(path);
        FileManagerController fileManagerController = new FileManagerController(fileManagerService);
        fileManagerController.startApp();
    }
}

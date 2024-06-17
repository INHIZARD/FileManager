package ru.file.manager.model.service;

import ru.file.manager.model.DirectoryAttributes;
import ru.file.manager.model.FileNode;
import ru.file.manager.model.FileSystemElement;
import ru.file.manager.model.exception.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Класс сервис, предназначенный для работы с моделью.
 */
public class FileManagerService {

    /**
     * Файловое дерево.
     */
    private FileNode fileTree;

    public FileManagerService(Path path) {
        this.fileTree = new FileNode(path);
    }

    /**
     * Метод, предназначенный для инициализации файлов дерева.
     *
     * @throws CreateFileTreeException ошибка создания файлового дерева.
     */
    public void initializeFileStructure() throws CreateFileTreeException {
        if (!Files.exists(fileTree.getNode()) || Files.isRegularFile(fileTree.getNode())) {
            throw new CreateFileTreeException(fileTree.getNode());
        }
        scanRootDirectory();
    }

    /**
     * Метод, который возвращает дерево файлов с действующем корнем.
     *
     * @return список файлов в директории
     */
    public FileNode getFileTree() {
        return fileTree;
    }

    /**
     * Метод, создающий файл.
     *
     * @param fileName название нового файла
     * @throws CreateFileException ошибка создания файла
     */
    public void createFile(String fileName) throws CreateFileException {
        Path newPath = Path.of(String.valueOf(fileTree.getNode()), fileName);
        try {
            Files.createFile(newPath);
            fileTree.addChild(new FileSystemElement(newPath, fileName, true));
        } catch (IOException e) {
            throw new CreateFileException(fileTree.getNode(), fileName);
        }
    }

    /**
     * Метод, создающий директорию.
     *
     * @param directoryName имя директории
     * @throws CreateDirectoryException ошибка создания директории
     */
    public void createDirectory(String directoryName) throws CreateDirectoryException {
        Path newPath = Path.of(String.valueOf(fileTree.getNode()), directoryName);
        try {
            Files.createDirectory(newPath);
            fileTree.addChild(new FileSystemElement(newPath, directoryName, false));
        } catch (IOException e) {
            throw new CreateDirectoryException(fileTree.getNode(), directoryName);
        }
    }

    /**
     * Метод, предназначенный для удаления объекта по его названию.
     *
     * @param name название объекта
     * @throws DeleteElementException ошибка удаления элемента
     */
    public void delete(String name) throws DeleteElementException {
        Path newPath = Path.of(String.valueOf(fileTree.getNode()), name);
        try {
            Files.delete(newPath);
            fileTree.getChildren().removeIf(element -> Objects.equals(element.getPath(), newPath));
        } catch (IOException e) {
            throw new DeleteElementException(fileTree.getNode(), name);
        }
    }

    /**
     * Метод, предназначенный для перехода вверх по дереву.
     *
     * @throws ChangeDirectoryException ошибка смены директории
     */
    public void navigateToParentDirectory() throws ChangeDirectoryException {
        Path newPath = fileTree.getNode().getParent();
        if (newPath == null) {
            throw new ChangeDirectoryException(fileTree.getNode());
        }
        rebuildFileStructure(newPath);
    }

    /**
     * Метод, предназначенный для перехода вниз по дереву.
     *
     * @param directory целевая директория для перехода
     * @throws ChangeDirectoryException ошибка смены директории
     */
    public void navigateToChildDirectory(String directory) throws ChangeDirectoryException {
        Path newPath = Path.of(String.valueOf(fileTree.getNode()), directory);
        if (!Files.isDirectory(newPath)) {
            throw new ChangeDirectoryException(fileTree.getNode(), directory);
        }
        rebuildFileStructure(newPath);
    }

    /**
     * Метод, предназначенный для сканирования файлового дерева по новому пути.
     */
    private void rebuildFileStructure(Path path) {
        this.fileTree = new FileNode(path);
        scanRootDirectory();
    }

    /**
     * Метод, который сканирует установленную корневую директорию в {@code fileTree}.
     */
    private void scanRootDirectory() {
        for (File element : new File(fileTree.getNode().toUri()).listFiles()) {
            FileSystemElement newFileSystemElement = new FileSystemElement(
                    element.toPath(),
                    element.getName(),
                    Files.isReadable(element.toPath()),
                    Files.isWritable(element.toPath()),
                    Files.isExecutable(element.toPath()));
            if (element.isFile()) {
                newFileSystemElement.setSize(element.length());
            } else if (element.isDirectory()) {
                newFileSystemElement.setType(false);
                if (newFileSystemElement.isReadable()) {
                    CreateFileTreeTask createFileTreeTask = new CreateFileTreeTask(element);
                    DirectoryAttributes resultAttributes = createFileTreeTask.compute();
                    newFileSystemElement.setDirectoryAttributes(resultAttributes);
                }
            }
            fileTree.addChild(newFileSystemElement);
        }
    }
}

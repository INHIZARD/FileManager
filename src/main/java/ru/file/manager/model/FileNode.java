package ru.file.manager.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, реализующий узел в файловом дереве.
 */
public class FileNode {

    /**
     * Объект, на котором построен узел.
     */
    private Path node;

    /**
     * Дочерние объекты.
     */
    private List<FileSystemElement> children;

    public FileNode(Path node) {
        this.node = node;
        this.children = new ArrayList<>();
    }

    public Path getNode() {
        return node;
    }

    public void setNode(Path node) {
        this.node = node;
    }

    public List<FileSystemElement> getChildren() {
        return children;
    }

    public void setChildren(List<FileSystemElement> children) {
        this.children = children;
    }

    public void addChild(FileSystemElement child) {
        children.add(child);
    }
}

package ru.file.manager.view;

import ru.file.manager.model.FileNode;
import ru.file.manager.model.FileSystemElement;

import java.nio.file.Path;

/**
 * Класс, реализующий отображение в приложении.
 */
public class FileManagerView {

    /**
     * Константа, которая хранит единицы измерения количества информации.
     */
    private static final String[] FILE_SIZE_UNITS = {"bytes", "Kb", "Mb", "Gb", "Tb", "Pb"};

    /**
     * Разница в типах данных.
     */
    private static final double DIFFERENCE_IN_FILE_SIZE_UNITS = 1024;

    /**
     * Элемент, из которого состоит разделитель между блоками приложения.
     */
    private static final String SEPARATOR_ELEMENT = "—";

    /**
     * Количество разделителей в строке.
     */
    private static final int SEPARATOR_ELEMENT_COUNT = 100;

    /**
     * Меню приложения.
     */
    private static final String MENU = """
            1. Создать пустой файл
            2. Создать пустую директорию
            3. Удалить файл или директорию
            4. Перейти в директорию
            5. Обновить список файлов
            6. Выход
            Введите цифру, соответствующую вашему запросу:\s""";

    /**
     * Запрос названия файла.
     */
    private static final String INPUT_FILE = "Введите название файла: ";

    /**
     * Запрос названия директории.
     */
    private static final String INPUT_DIRECTORY = "Введите название директории: ";

    /**
     * Запрос названия объекта.
     */
    private static final String INPUT_FILE_OR_DIRECTORY = "Введите полное название файла или директории: ";

    /**
     * Запрос названия директории для перехода в нее.
     */
    private static final String CHANGE_DIRECTORY =
            "Введите название директории (оставьте поле пустым чтобы вернуться на директорию выше): ";

    /**
     * Неверная команда.
     */
    private static final String WRONG_COMMAND = "Неверная команда";

    /**
     * Сообщение об ошибке создания файла.
     */
    private static final String FILE_CREATION_EXCEPTION_MESSAGE = "Ошибка создания файла [%s] по пути %s\n";

    /**
     * Сообщение об ошибке создания директории.
     */
    private static final String DIRECTORY_CREATION_EXCEPTION_MESSAGE = "Ошибка создания директории [%s] по пути %s\n";

    /**
     * Сообщение об ошибке удаления элемента.
     */
    private static final String ELEMENT_DELETION_EXCEPTION_MESSAGE = "Ошибка удаления элемента [%s] по пути %s\n";

    /**
     * Сообщение об ошибке смены директории.
     */
    private static final String DIRECTORY_CHANGING_EXCEPTION_MESSAGE =
            "Ошибка смены директории по пути %s в директорию [%s]\n";

    /**
     * Сообщение об ошибке создания файлового дерева.
     */
    private static final String FILE_TREE_GENERATION_EXCEPTION_MESSAGE =
            "Ошибка генерации файлового дерева по пути %s\n";

    /**
     * Разбиение элементов информации файла/директории в строке.
     */
    private static final String ELEMENT_INFORMATION = "%-45s %-15s %-12s %-4s %-6d %-10s\n";

    /**
     * Файл.
     */
    private static final String FILE = "file %s";

    /**
     * Директория.
     */
    private static final String DIRECTORY = "dir";

    /**
     * Аттрибуты.
     */
    private static final String ATTRIBUTES = "%s%s%s";

    /**
     * Файл/директория читается.
     */
    private static final String READABLE = "r";

    /**
     * В файл/директорию можно записать.
     */
    private static final String WRITABLE = "w";

    /**
     * Файл/директорию можно выполнить.
     */
    private static final String EXECUTABLE = "x";

    /**
     * Отсутствие чего-либо.
     */
    private static final String ABSENCE_ATTRIBUTE = "-";

    /**
     * Метод, выводящий все файлы/директории на экран.
     *
     * @param fileSystemElements объекты
     */
    public void showFileList(FileNode fileSystemElements) {
        showLine();
        for (FileSystemElement element : fileSystemElements.getChildren()) {
            System.out.printf(ELEMENT_INFORMATION,
                    element.getName(),
                    element.getType() ? String.format(FILE, element.getExtensionType()) : DIRECTORY,
                    castSize(element.getSize()),
                    String.format(ATTRIBUTES,
                            element.isReadable() ? READABLE : ABSENCE_ATTRIBUTE,
                            element.isWritable() ? WRITABLE : ABSENCE_ATTRIBUTE,
                            element.isExecutable() ? EXECUTABLE : ABSENCE_ATTRIBUTE),
                    element.getTotalObjects(),
                    castSize(element.getTotalSize()));
        }
    }

    /**
     * Метод, выводящий меню приложения на экран.
     */
    public void showMenuMessage() {
        showLine();
        System.out.print(MENU);
    }

    /**
     * Метод, выводящий запрос на ввод названия директории.
     */
    public void showFileNameCreationMessage() {
        System.out.print(INPUT_FILE);
    }

    /**
     * Метод, выводящий запрос на ввод названия директории.
     */
    public void showDirectoryNameCreationMessage() {
        System.out.print(INPUT_DIRECTORY);
    }

    /**
     * Метод, выводящий запрос на ввод название объекта.
     */
    public void showFileOrDirectorySelectionMessage() {
        System.out.print(INPUT_FILE_OR_DIRECTORY);
    }

    /**
     * Метод, выводящий запрос на ввод директории для её смены.
     */
    public void showDirectoryChangeMessage() {
        System.out.print(CHANGE_DIRECTORY);
    }

    /**
     * Метод, выводящий информацию при вводе неверной команды.
     */
    public void showWrongCommandMessage() {
        showLine();
        System.out.println(WRONG_COMMAND);
    }

    /**
     * Метод, выводящий информацию при ошибке создания файла.
     *
     * @param path     путь
     * @param fileName название файла
     */
    public void showCreateFileExceptionMessage(Path path, String fileName) {
        showLine();
        System.out.printf(FILE_CREATION_EXCEPTION_MESSAGE, fileName, path);
    }

    /**
     * Метод, выводящий информацию при ошибке создания директории.
     *
     * @param path          путь
     * @param directoryName название директории
     */
    public void showCreateDirectoryExceptionMessage(Path path, String directoryName) {
        showLine();
        System.out.printf(DIRECTORY_CREATION_EXCEPTION_MESSAGE, directoryName, path);
    }

    /**
     * Метод, выводящий информацию при ошибке удаления объекта.
     *
     * @param path        путь
     * @param elementName название объекта
     */
    public void showDeleteElementExceptionMessage(Path path, String elementName) {
        showLine();
        System.out.printf(ELEMENT_DELETION_EXCEPTION_MESSAGE, elementName, path);
    }

    /**
     * Метод, выводящий информацию при ошибке смены директории.
     *
     * @param path         путь
     * @param newDirectory новая директория
     */
    public void showChangeDirectoryExceptionMessage(Path path, String newDirectory) {
        showLine();
        System.out.printf(DIRECTORY_CHANGING_EXCEPTION_MESSAGE, path, newDirectory);
    }

    /**
     * Метод, выводящий информацию при ошибке генерации файлового дерева.
     *
     * @param path путь
     */
    public void showGenerationFileTreeExceptionMessage(Path path) {
        showLine();
        System.out.printf(FILE_TREE_GENERATION_EXCEPTION_MESSAGE, path);
    }

    /**
     * Метод, выводящий на экран линию. Данный метод предназначен только для красивого визуала.
     */
    private void showLine() {
        System.out.println(SEPARATOR_ELEMENT.repeat(SEPARATOR_ELEMENT_COUNT));
    }

    /**
     * Метод, который переводит байтовый размер в максимально возможный.
     *
     * @param initialSize размер в байтах
     * @return результирующий возможный максимальный размер
     */
    private String castSize(long initialSize) {
        double resultSize = initialSize;
        if (resultSize < DIFFERENCE_IN_FILE_SIZE_UNITS) {
            return String.format("%.0f %s", resultSize, FILE_SIZE_UNITS[0]);
        }
        int unitIndex = 0;
        while (unitIndex < FILE_SIZE_UNITS.length - 1) {
            if (resultSize > DIFFERENCE_IN_FILE_SIZE_UNITS) {
                resultSize /= DIFFERENCE_IN_FILE_SIZE_UNITS;
                unitIndex++;
                continue;

            }
            break;
        }
        return String.format("%.1f %s", resultSize, FILE_SIZE_UNITS[unitIndex]);
    }
}

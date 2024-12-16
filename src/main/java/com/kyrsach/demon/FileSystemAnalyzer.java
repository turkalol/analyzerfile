package com.kyrsach.demon;

import javafx.scene.control.TreeItem;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.FileTime;
import java.util.logging.*;

public class FileSystemAnalyzer {

    private static final Logger LOGGER = Logger.getLogger(FileSystemAnalyzer.class.getName());

    // Статический блок для настройки логгера
    static {
        try {
            // Создание обработчика файла для логирования
            FileHandler fileHandler = new FileHandler("logs/filesystem_analyzer.log", true);
            fileHandler.setFormatter(new SimpleFormatter()); // Установка простого форматирования логов
            LOGGER.addHandler(fileHandler); // Добавляем обработчик к логгеру
            LOGGER.setLevel(Level.ALL); // Устанавливаем уровень логирования (ALL для всех сообщений)

            // Отключаем логирование в консоль (опционально)
            Logger rootLogger = Logger.getLogger("");
            Handler[] handlers = rootLogger.getHandlers();
            if (handlers.length > 0) {
                rootLogger.removeHandler(handlers[0]); // Убираем консольный обработчик
            }

        } catch (IOException e) {
            System.err.println("Failed to initialize log handler: " + e.getMessage());
        }
    }

    public TreeItem<FileInfo> analyzeDirectory(File directory) {
        LOGGER.log(Level.INFO, "Starting analysis of directory: {0}", directory.getAbsolutePath());

        try {
            Path path = directory.toPath();
            BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
            FileOwnerAttributeView ownerAttr = Files.getFileAttributeView(path, FileOwnerAttributeView.class);

            FileInfo fileInfo = new FileInfo(
                    directory.getName(),
                    directory.length(),
                    attrs.creationTime(),
                    ownerAttr.getOwner(),
                    directory.isDirectory()
            );

            TreeItem<FileInfo> root = new TreeItem<>(fileInfo);

            if (directory.isDirectory()) {
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        LOGGER.log(Level.FINE, "Analyzing child file: {0}", file.getAbsolutePath());
                        TreeItem<FileInfo> item = analyzeDirectory(file);
                        root.getChildren().add(item);
                    }
                } else {
                    LOGGER.log(Level.WARNING, "Could not list files in directory: {0}", directory.getAbsolutePath());
                }
            }

            return root;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error analyzing directory: {0}", directory.getAbsolutePath());
            e.printStackTrace();
            return new TreeItem<>(new FileInfo(
                    directory.getName(),
                    0,
                    FileTime.fromMillis(0),
                    null,
                    directory.isDirectory()
            ));
        }
    }
}

package com.kyrsach.demon;

import javafx.scene.control.TreeItem;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.FileTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileSystemAnalyzer {

    private static final Logger LOGGER = Logger.getLogger(FileSystemAnalyzer.class.getName());

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

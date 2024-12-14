package com.kyrsach.demon;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

import java.io.File;

public class MainView {
    private final SplitPane root; // Разделение окна на две части
    private final TreeView<FileInfo> treeView;
    private final FileSystemAnalyzer analyzer;
    private final TextArea fileDetailsArea; // Область для отображения информации о файле

    public MainView() {
        root = new SplitPane();
        root.setPadding(new Insets(10));

        treeView = new TreeView<>();
        analyzer = new FileSystemAnalyzer();
        fileDetailsArea = new TextArea();
        fileDetailsArea.setEditable(false); // Отключаем редактирование информации

        // Создаем кнопку для выбора директории
        Button selectButton = new Button("Select Directory");
        VBox leftPanel = new VBox(10, selectButton, treeView);
        leftPanel.setPadding(new Insets(10));

        // Настройка TreeView и кнопки
        setupTreeView();
        setupSelectButton(selectButton);

        // Добавляем панели в SplitPane
        root.getItems().addAll(leftPanel, fileDetailsArea);
        root.setDividerPositions(0.3); // Устанавливаем соотношение сторон (30% слева)
    }

    private void setupTreeView() {
        treeView.setCellFactory(tv -> new TreeCell<>() {
            @Override
            protected void updateItem(FileInfo item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item.getName()); // Отображаем только имя файла/папки
                }
            }
        });

        // Добавляем обработчик события выбора узла
        treeView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && newVal.getValue() != null) {
                displayFileDetails(newVal.getValue());
            }
        });
    }

    private void setupSelectButton(Button selectButton) {
        selectButton.setOnAction(e -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select Directory to Analyze");
            File selectedDirectory = directoryChooser.showDialog(null);

            if (selectedDirectory != null) {
                TreeItem<FileInfo> rootItem = analyzer.analyzeDirectory(selectedDirectory);
                treeView.setRoot(rootItem);
                treeView.getSelectionModel().select(rootItem); // Автоматически выделяем корневую папку
            }
        });
    }

    private void displayFileDetails(FileInfo fileInfo) {
        // Формируем строку с детальной информацией о файле/папке
        StringBuilder details = new StringBuilder();
        details.append("Name: ").append(fileInfo.getName()).append("\n");
        details.append("Size: ").append(fileInfo.isDirectory() ? "Directory" : fileInfo.getSize() + " bytes").append("\n");
        details.append("Created: ").append(fileInfo.getCreationTime()).append("\n");
        details.append("Owner: ").append(fileInfo.getOwner()).append("\n");
        details.append("Type: ").append(fileInfo.isDirectory() ? "Folder" : "File").append("\n");

        fileDetailsArea.setText(details.toString());
    }

    public SplitPane getView() {
        return root;
    }
}

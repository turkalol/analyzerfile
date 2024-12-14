#FileSystemAnalyzer
Описание проекта
FileSystemAnalyzer — это Java-приложение, предназначенное для анализа файловой системы. Оно рекурсивно обходит директории и файлы, собирает информацию о них, такую как:

имя файла/директории
размер
дата создания
владелец
признак типа (файл или директория)
Требования
Java 11 или новее
JavaFX (для представления результатов)
Как использовать
Клонируйте репозиторий:

bash
Копировать код
git clone https://github.com/turkalol/analyzerfile
cd FileSystemAnalyzer
Скомпилируйте проект:

bash
Копировать код
javac -cp . --module-path /path/to/javafx/lib --add-modules javafx.controls com/kyrsach/demon/FileSystemAnalyzer.java
Запустите приложение:

bash
Копировать код
java -cp . --module-path /path/to/javafx/lib --add-modules javafx.controls com.kyrsach.demon.FileSystemAnalyzer
Вывод:

Приложение создаст древовидную структуру на основе выбранной директории.

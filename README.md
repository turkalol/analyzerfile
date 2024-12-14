# **FileSystemAnalyzer**

### **Описание проекта**
**FileSystemAnalyzer** — это Java-приложение, предназначенное для анализа файловой системы. Оно рекурсивно обходит директории и файлы, собирает информацию о них, такую как:
- Имя файла/директории
- Размер
- Дата создания
- Владелец
- Признак типа (файл или директория)

### **Требования**
- **Java 11** или новее
- **JavaFX** (для представления результатов)

### **Как использовать**
1. **Клонируйте репозиторий**:
   ```bash
   git clone https://github.com/username/FileSystemAnalyzer.git
   cd FileSystemAnalyzer
   ```

2. **Скомпилируйте проект**:
   ```bash
   javac -cp . --module-path /path/to/javafx/lib --add-modules javafx.controls com/kyrsach/demon/FileSystemAnalyzer.java
   ```

3. **Запустите приложение**:
   ```bash
   java -cp . --module-path /path/to/javafx/lib --add-modules javafx.controls com.kyrsach.demon.FileSystemAnalyzer
   ```

4. **Вывод**:
    - Приложение создаст древовидную структуру на основе выбранной директории.

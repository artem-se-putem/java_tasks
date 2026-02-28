# Теория по Java для Data Engineering

Это дополнение к базовой теории Java. Здесь собраны темы, необходимые для работы с данными.

---

## 1. Maven

Maven — инструмент для сборки проекта и управления зависимостями.

### pom.xml (пример)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>data-project</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <!-- JUnit 5 для тестов -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.10.0</version>
            <scope>test</scope>
        </dependency>

        <!-- Mockito для мокирования -->
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <version>5.7.0</version>
            <scope>test</scope>
        </dependency>

        <!-- PostgreSQL driver -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.7.1</version>
        </dependency>

        <!-- Jackson для JSON -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.16.0</version>
        </dependency>

        <!-- Lombok (опционально) -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.30</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.11.0</version>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.1.2</version>
            </plugin>
        </plugins>
    </build>
</project>
```

### Команды Maven

```bash
mvn clean compile      # компиляция
mvn test               # запуск тестов
mvn package            # сборка в JAR
mvn exec:java          # запуск программы
mvn dependency:tree    # дерево зависимостей
```

---

## 2. JDBC (работа с базами данных)

JDBC — Java Database Connectivity, API для работы с БД.

### Подключение к PostgreSQL

```java
import java.sql.*;

public class DatabaseUtil {
    private static final String URL = "jdbc:postgresql://localhost:5432/mydb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
```

### Выполнение запросов

```java
// SELECT с PreparedStatement (защита от SQL injection)
public List<User> findAllUsers() {
    String sql = "SELECT id, name, email FROM users WHERE active = ?";
    List<User> users = new ArrayList<>();

    try (Connection conn = DatabaseUtil.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setBoolean(1, true);

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                users.add(user);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return users;
}

// INSERT с возвратом ID
public int insertUser(User user) {
    String sql = "INSERT INTO users (name, email) VALUES (?, ?) RETURNING id";

    try (Connection conn = DatabaseUtil.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, user.getName());
        stmt.setString(2, user.getEmail());

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1;
}

// UPDATE
public boolean updateUser(User user) {
    String sql = "UPDATE users SET name = ?, email = ? WHERE id = ?";

    try (Connection conn = DatabaseUtil.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, user.getName());
        stmt.setString(2, user.getEmail());
        stmt.setInt(3, user.getId());

        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

// DELETE
public boolean deleteUser(int id) {
    String sql = "DELETE FROM users WHERE id = ?";

    try (Connection conn = DatabaseUtil.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, id);
        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

// Транзакции
public void transferMoney(int fromId, int toId, double amount) {
    String debitSql = "UPDATE accounts SET balance = balance - ? WHERE id = ?";
    String creditSql = "UPDATE accounts SET balance = balance + ? WHERE id = ?";

    Connection conn = null;
    try {
        conn = DatabaseUtil.getConnection();
        conn.setAutoCommit(false);

        try (PreparedStatement debitStmt = conn.prepareStatement(debitSql);
             PreparedStatement creditStmt = conn.prepareStatement(creditSql)) {

            debitStmt.setDouble(1, amount);
            debitStmt.setInt(2, fromId);
            debitStmt.executeUpdate();

            creditStmt.setDouble(1, amount);
            creditStmt.setInt(2, toId);
            creditStmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
```

### Пул соединений (HikariCP)

```java
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionPool {
    private static HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/mydb");
        config.setUsername("postgres");
        config.setPassword("password");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(30000);

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void close() {
        dataSource.close();
    }
}
```

---

## 3. Java I/O (работа с файлами)

### Базовые классы

| Класс | Назначение |
|-------|------------|
| `File` | Представление файла/директории |
| `FileReader` / `FileWriter` | Чтение/запись текста |
| `BufferedReader` / `BufferedWriter` | Буферизированный ввод/вывод |
| `FileInputStream` / `FileOutputStream` | Байтовый поток |
| `Scanner` | Чтение с разбором |
| `Path` / `Paths` | Современный API (NIO) |

### Чтение файлов

```java
import java.io.*;
import java.nio.file.*;

// Чтение всего файла (для маленьких файлов)
public String readFile(String path) throws IOException {
    return Files.readString(Path.of(path));
}

// Чтение построчно (для больших файлов)
public List<String> readLines(String path) throws IOException {
    return Files.readAllLines(Path.of(path));
}

// Чтение больших файлов построчно (эффективно)
public void processLargeFile(String path) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
        String line;
        while ((line = reader.readLine()) != null) {
            // обработка строки
            System.out.println(line);
        }
    }
}

// Потоковая обработка (для огромных файлов)
public void streamLargeFile(String path) throws IOException {
    try (Stream<String> lines = Files.lines(Path.of(path))) {
        lines.filter(line -> line.contains("ERROR"))
             .forEach(System.out::println);
    }
}
```

### Запись файлов

```java
// Запись строки (перезапись)
public void writeFile(String path, String content) throws IOException {
    Files.writeString(Path.of(path), content);
}

// Запись построчно
public void writeLines(String path, List<String> lines) throws IOException {
    Files.write(Path.of(path), lines);
}

// Добавление в конец
public void appendToFile(String path, String line) throws IOException {
    Files.writeString(Path.of(path), line + System.lineSeparator(),
        StandardOpenOption.CREATE,
        StandardOpenOption.APPEND);
}
```

### CSV парсинг

```java
import java.io.*;
import java.util.*;

// Простой CSV парсер
public List<Map<String, String>> readCSV(String path) throws IOException {
    List<Map<String, String>> result = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
        // Читаем заголовки
        String headerLine = reader.readLine();
        String[] headers = headerLine.split(",");

        // Читаем данные
        String line;
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            Map<String, String> row = new LinkedHashMap<>();

            for (int i = 0; i < headers.length; i++) {
                row.put(headers[i].trim(), values[i].trim());
            }
            result.add(row);
        }
    }
    return result;
}
```

### JSON (Jackson)

```java
import com.fasterxml.jackson.databind.*;

// Сериализация объекта в JSON
public String toJson(Object obj) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(obj);
}

// Десериализация JSON в объект
public <T> T fromJson(String json, Class<T> clazz) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(json, clazz);
}

// Чтение JSON из файла
public List<User> readUsersFromJson(String path) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(Path.of(path).toFile(),
        mapper.getTypeFactory().constructCollectionType(List.class, User.class));
}
```

---

## 4. JUnit 5 (тестирование)

### Структура теста

```java
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @BeforeAll
    static void setupAll() {
        // Выполняется один раз перед всеми тестами
    }

    @BeforeEach
    void setup() {
        // Выполняется перед каждым тестом
    }

    @Test
    @DisplayName("Тест: сумма двух чисел")
    void testSum() {
        assertEquals(8, sum(5, 3));
        assertNotEquals(10, sum(5, 3));
    }

    @Test
    void testArrayOperations() {
        int[] arr = {1, 2, 3};
        assertEquals(3, arr.length);
        assertArrayEquals(new int[]{1, 2, 3}, arr);
    }

    @Test
    void testExceptions() {
        assertThrows(ArithmeticException.class, () -> {
            int x = 10 / 0;
        });
    }

    @AfterEach
    void tearDown() {
        // Очистка после каждого теста
    }

    @AfterAll
    static void tearDownAll() {
        // Очистка после всех тестов
    }
}
```

### Assertions

```java
// Основные
assertEquals(expected, actual);
assertNotEquals(expected, actual);
assertTrue(condition);
assertFalse(condition);
assertNull(object);
assertNotNull(object);
assertArrayEquals(expected, actual);

// Групповые
assertAll(() -> assertEquals(1, result.getId()),
          () -> assertEquals("John", result.getName()));

// Проверка исключений
assertThrows(IllegalArgumentException.class, () -> {
    validator.validate(null);
});

// Проверка времени выполнения
assertTimeout(Duration.ofSeconds(1), () -> {
    // операция должна выполниться менее чем за 1 секунду
});
```

---

## 5. Mockito (мокирование)

### Бавое использование

```java
import org.junit.jupiter.api.*;
import org.mockito.*;

class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        // Настройка мока
        User expectedUser = new User(1, "John", "john@test.com");
        when(userRepository.findById(1)).thenReturn(expectedUser);

        // Вызов
        User result = userService.getUserById(1);

        // Проверка
        assertNotNull(result);
        assertEquals("John", result.getName());

        // Проверка вызова мока
        verify(userRepository).findById(1);
    }

    @Test
    void testSaveUser() {
        User newUser = new User("John", "john@test.com");
        when(userRepository.save(any(User.class))).thenReturn(1);

        int id = userService.createUser(newUser);

        assertEquals(1, id);
        verify(userRepository).save(newUser);
    }
}
```

### Методы Mockito

```java
// Настройка возвращаемого значения
when(mock.method()).thenReturn(value);
when(mock.method()).thenThrow(new RuntimeException());
when(mock.method()).thenAnswer(invocation -> {
    // кастомная логика
    return null;
});

// Проверка вызовов
verify(mock).method();
verify(mock, times(2)).method();
verify(mock, never()).method();
verify(mock, atLeast(1)).method();
verify(mock, atMost(5)).method();

// Аргументы
when(mock.method(anyString())).thenReturn(true);
when(mock.method(argThat(s -> s.length() > 3))).thenReturn(true);
verify(mock).method(eq("value1"), anyInt());

// Void методы
doNothing().doThrow(new RuntimeException()).when(mock).deleteUser(anyInt());
```

---

## 6. Stream API (дополнение)

### Полезные операции для Data Engineering

```java
import java.util.*;
import java.util.stream.*;

// Группировка
public Map<String, List<User>> groupByCity(List<User> users) {
    return users.stream()
        .collect(Collectors.groupingBy(User::getCity));
}

// Группировка с агрегацией
public Map<String, Long> countByCategory(List<Product> products) {
    return products.stream()
        .collect(Collectors.groupingBy(
            Product::getCategory,
            Collectors.counting()
        ));
}

// Группировка с вложенной агрегацией
public Map<String, Double> sumPriceByCategory(List<Product> products) {
    return products.stream()
        .collect(Collectors.groupingBy(
            Product::getCategory,
            Collectors.summingDouble(Product::getPrice)
        ));
}

// Среднее значение
public double averagePrice(List<Product> products) {
    return products.stream()
        .mapToDouble(Product::getPrice)
        .average()
        .orElse(0.0);
}

// partitionBy (разделение на две группы)
public Map<Boolean, List<Integer>> partitionByEven(List<Integer> numbers) {
    return numbers.stream()
        .collect(Collectors.partitioningBy(n -> n % 2 == 0));
}

// FlatMap для вложенных коллекций
public List<String> getAllEmails(List<Department> departments) {
    return departments.stream()
        .flatMap(dept -> dept.getEmployees().stream())
        .map(Employee::getEmail)
        .distinct()
        .collect(Collectors.toList());
}

// Collectors.joining
public String joinNames(List<User> users) {
    return users.stream()
        .map(User::getName)
        .collect(Collectors.joining(", ", "[", "]"));
    // Результат: "[John, Jane, Bob]"
}
```

---

## 7. Concurrency (многопоточность)

### Thread и Runnable

```java
// Способ 1: Runnable
Thread thread = new Thread(() -> {
    System.out.println("Running in thread");
});
thread.start();

// Способ 2: Thread
Thread thread = new Thread() {
    @Override
    public void run() {
        System.out.println("Running");
    }
};
thread.start();
```

### ExecutorService

```java
import java.util.concurrent.*;

// Пул потоков
ExecutorService executor = Executors.newFixedThreadPool(4);

// Выполнение задачи
Future<String> future = executor.submit(() -> {
    return "Result";
});

// Получение результата
String result = future.get(); // блокирует до завершения

// Выключение
executor.shutdown();
executor.awaitTermination(60, TimeUnit.SECONDS);
```

### CompletableFuture (рекомендуется)

```java
import java.util.concurrent.*;

// Асинхронная операция
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
    // тяжелая операция
    return "Result";
});

// Обработка результата
future.thenAccept(result -> System.out.println(result));

// Цепочка операций
CompletableFuture<Integer> result = CompletableFuture
    .supplyAsync(() -> fetchData())
    .thenApply(data -> processData(data))
    .thenApply(result -> saveData(result));

// Комбинирование
CompletableFuture<String> combined = CompletableFuture
    .supplyAsync(() -> task1())
    .thenCombine(
        CompletableFuture.supplyAsync(() -> task2()),
        (r1, r2) -> r1 + r2
    );
```

---

## 8. Lombok (опционально)

Lombok уменьшает boilerplate код.

```java
import lombok.*;

// Генерирует: конструктор со всеми полями
// конструктор без аргументов, геттеры, сеттеры, toString, equals, hashCode
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private int id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
}

// Использование
User user = User.builder()
    .name("John")
    .email("john@test.com")
    .build();
```

---

## Полезные ссылки

- [Maven Getting Started](https://maven.apache.org/guides/getting-started/)
- [JDBC Tutorial](https://docs.oracle.com/javase/tutorial/jdbc/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://site.mockito.org/)
- [Stream API Tutorial](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html)

---

**Далее:** Перейди к файлу `data_engineering_tasks.md` для практики!

# Практика Java для Data Engineering

Выполняй задачи по порядку. Каждая следующая задача использует навыки из предыдущих.

---

## Уровень 1: Maven + JUnit

### Задача 1.1: Настрой Maven проекта
Создай структуру Maven проекта в папке `java/data-project/`:

```
data-project/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── App.java
│   │   └── resources/
│   └── test/
│       └── java/
│           └── com/
│               └── example/
│                   └── AppTest.java
```

**Требования:**
- Java 17
- Зависимости: JUnit 5.10.0, Mockito 5.7.0

**Проверка:** `mvn test` должен запускать пустой тест без ошибок.

---

### Задача 1.2: Первые тесты
Создай класс `StringUtil` с методами и напиши тесты:

```java
public class StringUtil {
    // Переворачивает строку
    public static String reverse(String s);
    
    // Проверяет, является ли строка палиндромом
    public static boolean isPalindrome(String s);
    
    // Возвращает количество слов в строке
    public static int wordCount(String s);
}
```

**Требования:**
- Напиши минимум 3 теста на каждый метод
- Используй `assertEquals`, `assertTrue`, `assertFalse`
- Проверь граничные случаи (пустая строка, null)

---

### Задача 1.3: Тестирование исключений
Создай класс `Calculator` с методом деления и протестируй исключения:

```java
public class Calculator {
    public double divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return (double) a / b;
    }
}
```

**Требования:**
- Напиши тест, проверяющий что `divide(10, 0)` выбрасывает `ArithmeticException`
- Напиши тест, проверяющий корректный результат `divide(10, 2) == 5.0`

---

## Уровень 2: JDBC + Базы данных

### Задача 2.1: Подключение к БД
Создай класс `DatabaseUtil` для работы с PostgreSQL:

```java
public class DatabaseUtil {
    // Константы для подключения (используй свои данные)
    private static final String URL = "jdbc:postgresql://localhost:5432/testdb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "password";
    
    public static Connection getConnection() throws SQLException;
    public static void close(AutoCloseable... resources);
}
```

**Требования:**
- Используй try-with-resources
- Обрабатывай SQLException

---

### Задача 2.2: CRUD операции
Создай класс `UserDAO` с методами:

```java
public class User {
    private int id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    // геттеры, сеттеры, конструктор
}

public class UserDAO {
    // Найти всех пользователей
    public List<User> findAll();
    
    // Найти по ID
    public User findById(int id);
    
    // Вставить пользователя (вернуть сгенерированный ID)
    public int insert(User user);
    
    // Обновить пользователя
    public boolean update(User user);
    
    // Удалить пользователя
    public boolean delete(int id);
}
```

**Требования:**
- Используй PreparedStatement
- Используй try-with-resources
- Защитись от SQL инъекций

---

### Задача 2.3: Транзакции
Создай метод перевода денег между счетами:

```java
public class AccountDAO {
    public boolean transfer(int fromId, int toId, double amount);
}
```

**Требования:**
- Используй транзакцию
- Откати изменения при ошибке
- Проверь что баланс не уходит в минус

---

### Задача 2.4: Интеграционные тесты с БД
Напиши тесты для `UserDAO`:

```java
class UserDAOTest {
    private static UserDAO userDAO;
    private static Connection connection;
    
    @BeforeAll
    static void setup() throws SQLException {
        // Создай таблицу перед тестами
        connection = DatabaseUtil.getConnection();
        userDAO = new UserDAO(connection);
        
        String sql = """
            CREATE TABLE IF NOT EXISTS users (
                id SERIAL PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                email VARCHAR(100) UNIQUE NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """;
        try (var stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }
    
    @AfterAll
    static void cleanup() throws SQLException {
        // Удали таблицу после тестов
        try (var stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS users");
        }
        connection.close();
    }
    
    @BeforeEach
    void beforeEach() throws SQLException {
        // Очисти таблицу перед каждым тестом
        try (var stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM users");
        }
    }
    
    @Test
    void testInsert() { /* ... */ }
    @Test
    void testFindById() { /* ... */ }
    @Test
    void testUpdate() { /* ... */ }
    @Test
    void testDelete() { /* ... */ }
}
```

---

## Уровень 3: I/O + Файлы

### Задача 3.1: CSV Reader
Создай класс `CsvReader` для чтения CSV файлов:

```java
public class CsvReader {
    public List<Map<String, String>> read(String filePath) throws IOException;
    public <T> List<T> read(String filePath, Function<Map<String, String>, T> mapper);
}
```

**Пример CSV:**
```csv
id,name,email,age
1,John,john@test.com,25
2,Jane,jane@test.com,30
```

**Требования:**
- Первая строка — заголовки
- Поддерживай разные разделители (`,`, `;`, `\t`)
- Обрабатывай пустые строки

---

### Задача 3.2: CSV Writer
Создай класс `CsvWriter`:

```java
public class CsvWriter {
    public void write(String filePath, List<Map<String, String>> data) throws IOException;
    public <T> void write(String filePath, List<T> data, Function<T, Map<String, String>> mapper);
}
```

---

### Задача 3.3: Обработка большого файла
Напиши программу, которая:
1. Читает большой текстовый файл (100k+ строк)
2. Фильтрует строки по критерию
3. Записывает результат в другой файл

```java
public class FileProcessor {
    public void processLargeFile(String inputPath, String outputPath, 
                                  Predicate<String> filter) throws IOException {
        // Используй BufferedReader и BufferedWriter
        // Не загружай весь файл в память
    }
}
```

---

### Задача 3.4: JSON парсинг
Создай класс для работы с JSON (используй Jackson):

```java
public class JsonUtil {
    public static String toJson(Object obj) throws JsonProcessingException;
    public static <T> T fromJson(String json, Class<T> clazz) throws JsonProcessingException;
    public static <T> List<T> listFromJson(String json, Class<T> clazz) throws JsonProcessingException;
}
```

**Тестируй на:**
```json
[
  {"id": 1, "name": "John", "email": "john@test.com"},
  {"id": 2, "name": "Jane", "email": "jane@test.com"}
]
```

---

## Уровень 4: Stream API + Коллекции

### Задача 4.1: Агрегация данных
Дан список транзакций:

```java
public class Transaction {
    private String category;
    private double amount;
    private LocalDate date;
    private String userId;
}

public class TransactionAnalyzer {
    // Сумма по категориям
    public Map<String, Double> sumByCategory(List<Transaction> transactions);
    
    // Средняя сумма транзакции по категориям
    public Map<String, Double> averageByCategory(List<Transaction> transactions);
    
    // Топ-N категорий по сумме
    public List<String> topCategories(List<Transaction> transactions, int n);
    
    // Транзакции за период
    public List<Transaction> filterByDateRange(List<Transaction> transactions,
                                                LocalDate start, LocalDate end);
}
```

---

### Задача 4.2: Группировка и пагинация
Создай класс для группировки пользователей:

```java
public class User {
    private String name;
    private String city;
    private int age;
}

public class UserAnalyzer {
    // Группировка по городу
    public Map<String, List<User>> groupByCity(List<User> users);
    
    // Группировка по возрастной группе
    public Map<String, List<User>> groupByAgeGroup(List<User> users);
    
    // Пагинация
    public List<User> paginate(List<User> users, int page, int pageSize);
}
```

---

## Уровень 5: Интеграция (все вместе)

### Задача 5.1: ETL пайплайн
Создай простой ETL пайплайн:

```
Extract → Transform → Load
```

```java
public class EtlPipeline {
    private final DataSource dataSource;
    
    public EtlPipeline(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    // Извлечение из CSV
    public List<Transaction> extractFromCsv(String filePath);
    
    // Трансформация: очистка и валидация
    public List<Transaction> transform(List<Transaction> transactions);
    
    // Загрузка в БД
    public int load(List<Transaction> transactions);
    
    // Запуск всего пайплайна
    public void run(String inputFile);
}
```

**Требования:**
- Валидация данных (непустые поля, положительные суммы)
- Логирование процесса
- Обработка ошибок

---

### Задача 5.2: Тестирование ETL
Напиши unit-тесты для ETL пайплайна:

```java
class EtlPipelineTest {
    @Mock
    private DataSource dataSource;
    
    @InjectMocks
    private EtlPipeline pipeline;
    
    @Test
    void testTransform_validData() {
        // Подготовка
        List<Transaction> input = List.of(
            new Transaction("food", 100.0, LocalDate.now(), "user1"),
            new Transaction("", -50.0, LocalDate.now(), "user2") // невалидная
        );
        
        // Вызов
        List<Transaction> result = pipeline.transform(input);
        
        // Проверка: должна остаться только 1 валидная транзакция
        assertEquals(1, result.size());
        assertEquals("food", result.get(0).getCategory());
    }
    
    @Test
    void testTransform_filtersNegativeAmounts() { /* ... */ }
    
    @Test
    void testTransform_filtersEmptyCategory() { /* ... */ }
}
```

---

### Задача 5.3: Асинхронная обработка
Создай асинхронный процессор файлов:

```java
public class AsyncFileProcessor {
    private final ExecutorService executor;
    
    public AsyncFileProcessor(int threads) {
        this.executor = Executors.newFixedThreadPool(threads);
    }
    
    // Обработка нескольких файлов параллельно
    public List<ProcessResult> processFiles(List<String> filePaths) 
            throws InterruptedException, ExecutionException {
        // Используй CompletableFuture
    }
    
    public void shutdown() {
        executor.shutdown();
    }
}
```

---

## Бонусные задачи

### Задача B.1: Пул соединений
Добавь HikariCP в проект и настрой пул соединений.

### Задача B.2: Lombok
Добавь Lombok и убери boilerplate из классов User, Transaction.

### Задача B.3: CSV валидатор
Создай валидатор CSV с проверкой:
- Типы данных (числа, даты)
- Обязательные поля
- Уникальность

---

## Как проверять

```bash
# Компиляция
mvn clean compile

# Запуск тестов
mvn test

# Сборка JAR
mvn package

# Запуск
java -jar target/data-project-1.0-SNAPSHOT.jar
```

---

**После выполнения всех задач ты будешь готов к:**
- Работе с базами данных
- Чтению/записи файлов различных форматов
- Написанию качественных тестов
- Созданию ETL пайплайнов

Удачи! 🚀

Файл пуст. Создаю полный файл задач для Flink:
flink_tasks.md
# Задачи по Apache Flink## Уровень 1: Базовые операции (аналоги Stream API)### 

Задача  1.1: Фильтрация четных чиселИспользуя Flink DataStream API, отфильтруйте четные числа из потока.
**Вход:**avaDataStream<Integer> numbers = env.fromElements(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);**Ожидаемый результат:**
Ожидаемый результат:

**Подсказка:** Используйте `.filter(x -> x % 2 == 0)`---### 

Задача  1.2: Преобразование в квадратыИспользуя Flink DataStream API, преобразуйте числа в их квадраты.
**Вход:**avaDataStream<Integer> numbers = env.fromElements(1, 2, 3, 4, 5);**Ожидаемый результат:**
Подсказка: Используйте .filter(x -> x % 2 == 0)


Задача  1.2: Преобразование в квадраты
Используя Flink DataStream API, преобразуйте числа в их квадраты.
Вход:

**Подсказка:** Используйте `.map(x -> x * x)`---### 

Задача  1.3: Длина строкИспользуя Flink DataStream API, преобразуйте строки в их длины.
**Вход:**vaDataStream<String> words = env.fromElements("hello", "world", "java", "flink");**Ожидаемый результат:**
Ожидаемый результат:

**Подсказка:** Используйте `.map(String::length)` или `.map(s -> s.length())`---### 

Задача  1.4: Удаление дубликатовИспользуя Flink DataStream API, удалите дубликаты из потока.
**Вход:**aDataStream<Integer> numbers = env.fromElements(1, 2, 2, 3, 3, 3, 4, 5);**Ожидаемый результат:**
Подсказка: Используйте .map(x -> x * x)


Задача  1.3: Длина строк
Используя Flink DataStream API, преобразуйте строки в их длины.
Вход:

**Подсказка:** Используйте `.keyBy(x -> x)` и `.window()` или `.distinct()`---### 

Задача  1.5: Преобразование в верхний регистрИспользуя Flink DataStream API, преобразуйте строки в верхний регистр.
**Вход:**aDataStream<String> words = env.fromElements("hello", "world", "flink");**Ожидаемый результат:**
Ожидаемый результат:

**Подсказка:** Используйте `.map(String::toUpperCase)`---## Уровень 2: Комбинации операций### 

Задача  2.1: Четные числа больше 5Используя Flink DataStream API, найдите все четные числа больше 5 и преобразуйте их в строки.
**Вход:**avaDataStream<Integer> numbers = env.fromElements(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);**Ожидаемый результат:**
Подсказка: Используйте .map(String::length) или .map(s -> s.length())


Задача  1.4: Удаление дубликатов
Используя Flink DataStream API, удалите дубликаты из потока.
Вход:

**Подсказка:** Используйте `.filter(x -> x > 5 && x % 2 == 0).map(x -> String.valueOf(x))`---### 

Задача  2.2: Слова длиннее 4 символов в верхнем регистреИспользуя Flink DataStream API, найдите слова длиннее 4 символов и преобразуйте их в верхний регистр.
**Вход:**DataStream<String> words = env.fromElements("hello", "hi", "world", "java", "flink");**Ожидаемый результат:**
Ожидаемый результат:

**Подсказка:** Используйте `.filter(w -> w.length() > 4).map(String::toUpperCase)`---### 

Задача  2.3: Сумма квадратов четных чиселИспользуя Flink DataStream API, найдите сумму квадратов всех четных чисел.
**Вход:**vaDataStream<Integer> numbers = env.fromElements(1, 2, 3, 4, 5, 6);**Ожидаемый результат:**
Подсказка: Используйте .keyBy(x -> x) и .window() или .distinct()


Задача  1.5: Преобразование в верхний регистр
Используя Flink DataStream API, преобразуйте строки в верхний регистр.
Вход:

**Подсказка:** Используйте `.filter(x -> x % 2 == 0).map(x -> x * x).keyBy(x -> 1).sum(0)`---### 

Задача  2.4: Разделение предложений на словаИспользуя Flink DataStream API, разделите предложения на отдельные слова.
**Вход:**DataStream<String> sentences = env.fromElements("hello world", "flink stream", "java api");**Ожидаемый результат:**
Ожидаемый результат:

**Подсказка:** Используйте `.flatMap()` с `Collector`---### 

Задача  2.5: Подсчет суммы по категориямИспользуя Flink DataStream API, подсчитайте сумму транзакций по каждой категории.
**Вход:**// Tuple2<категория, сумма>DataStream<Tuple2<String, Integer>> transactions = env.fromElements(    Tuple2.of("food", 100),    Tuple2.of("electronics", 500),    Tuple2.of("food", 200),    Tuple2.of("electronics", 300));**Ожидаемый результат:**
Подсказка: Используйте .map(String::toUpperCase)
Уровень 2: Комбинации операций


Задача  2.1: Четные числа больше 5
Используя Flink DataStream API, найдите все четные числа больше 5 и преобразуйте их в строки.
Вход:

**Подсказка:** Используйте `.keyBy(0)` и `.sum(1)`---## Уровень 3: Окна (Windows) - КЛЮЧЕВАЯ КОНЦЕПЦИЯ FLINK!### 

Задача  3.1: Сумма за окно времени
Используя Flink DataStream API, подсчитайте сумму чисел за каждое окно в 5 секунд.
**Вход:**aDataStream<Integer> numbers = env.fromElements(1, 2, 3, 4, 5, 6, 7, 8);
**Подсказка:** Используйте `.keyBy(x -> 1).window(TumblingProcessingTimeWindows.of(Time.seconds(5))).sum(0)`**Ожидаемый результат:** Сумма всех чисел в окне (зависит от времени выполнения)---### 

Задача  3.2: Максимальное значение за окноИспользуя Flink DataStream API, найдите максимальное значение за каждое окно в 10 секунд.
**Вход:**vaDataStream<Integer> numbers = env.fromElements(10, 5, 20, 15, 30, 25);
**Подсказка:** Используйте `.keyBy(x -> 1).window(...).max(0)`---### 

Задача  3.3: Подсчет событий по пользователю за минутуИспользуя Flink DataStream API, подсчитайте количество событий для каждого пользователя за окно в 1 минуту.
**Вход:**ava// Tuple2<userId, eventType>DataStream<Tuple2<String, String>> events = env.fromElements(    Tuple2.of("user1", "click"),    Tuple2.of("user2", "view"),    Tuple2.of("user1", "click"),    Tuple2.of("user1", "view"));
**Подсказка:** Используйте `.keyBy(0)`, `.window(TumblingProcessingTimeWindows.of(Time.minutes(1)))` и `.count()`**Ожидаемый результат:** Количество событий для каждого пользователя в окне---### 

Задача  3.4: Среднее значение за окноИспользуя Flink DataStream API, найдите среднее значение чисел за каждое окно в 5 секунд.
**Вход:**DataStream<Integer> numbers = env.fromElements(10, 20, 30, 40, 50);
**Подсказка:** Используйте `.aggregate()` или `.process()` с `WindowFunction`---### 

Задача  3.5: Скользящее окно (Sliding Window)Используя Flink DataStream API, подсчитайте сумму за скользящее окно (размер 10 секунд, сдвиг 5 секунд).
**Вход:**avaDataStream<Integer> numbers = env.fromElements(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
**Подсказка:** Используйте `SlidingProcessingTimeWindows.of(Time.seconds(10), Time.seconds(5))`---## Уровень 4: Работа с объектами### 

Задача  4.1: Класс Transactionpublic class Transaction {    private String userId;    private double amount;    private String category;    private long timestamp;        public Transaction(String userId, double amount, String category, long timestamp) {        this.userId = userId;        this.amount = amount;        this.category = category;        this.timestamp = timestamp;    }        // геттеры и сеттеры    public String getUserId() { return userId; }    public double getAmount() { return amount; }    public String getCategory() { return category; }    public long getTimestamp() { return timestamp; }}**

Задача :** Используя Flink DataStream API, отфильтруйте транзакции с суммой > 1000 и выведите userId.
**Вход:**DataStream<Transaction> transactions = env.fromElements(    new Transaction("user1", 500, "food", System.currentTimeMillis()),    new Transaction("user2", 1500, "electronics", System.currentTimeMillis()),    new Transaction("user3", 2000, "food", System.currentTimeMillis()));**Ожидаемый результат:**
Ожидаемый результат:
---### 

Задача  4.2: Группировка по категорииИспользуя Flink DataStream API, сгруппируйте транзакции по категории и подсчитайте сумму.
**Вход:** (те же данные из 4.1)**Ожидаемый результат:**
Подсказка: Используйте .filter(x -> x > 5 && x % 2 == 0).map(x -> String.valueOf(x))


Задача  2.2: Слова длиннее 4 символов в верхнем регистре
Используя Flink DataStream API, найдите слова длиннее 4 символов и преобразуйте их в верхний регистр.
Вход:

**Подсказка:** Используйте `.keyBy(Transaction::getCategory)` и `.sum("amount")` или `.aggregate()`---### 

Задача  4.3: Средняя сумма по категории за минутуИспользуя Flink DataStream API, найдите среднюю сумму транзакций по каждой категории за окно в 1 минуту.
**Вход:** (те же данные из 4.1, добавьте больше транзакций)
**Подсказка:** Используйте `.keyBy()`, `.window()` и `.aggregate()` с вычислением среднего---## Уровень 5: Продвинутые задачи### 

Задача  5.1: Объединение потоков (Union)Используя Flink DataStream API, объедините два потока данных.
**Вход:**DataStream<Integer> stream1 = env.fromElements(1, 2, 3);DataStream<Integer> stream2 = env.fromElements(4, 5, 6);**Ожидаемый результат:**
Ожидаемый результат:

**Подсказка:** Используйте `.union()`---### 

Задача  5.2: Подсчет элементов в потокеИспользуя Flink DataStream API, подсчитайте общее количество элементов в потоке.
**Вход:**vaDataStream<Integer> numbers = env.fromElements(1, 2, 3, 4, 5, 6);**Ожидаемый результат:**
Подсказка: Используйте .filter(w -> w.length() > 4).map(String::toUpperCase)


Задача  2.3: Сумма квадратов четных чисел
Используя Flink DataStream API, найдите сумму квадратов всех четных чисел.
Вход:

**Подсказка:** Используйте `.keyBy(x -> 1).count()` или `.map(x -> 1).keyBy(x -> 1).sum(0)`---### 

Задача  5.3: Группировка и агрегация с несколькими полямиИспользуя Flink DataStream API, сгруппируйте транзакции по userId и категории, затем подсчитайте сумму.
**Вход:**// Tuple3<userId, category, amount>DataStream<Tuple3<String, String, Double>> transactions = env.fromElements(    Tuple3.of("user1", "food", 100.0),    Tuple3.of("user1", "food", 200.0),    Tuple3.of("user1", "electronics", 500.0),    Tuple3.of("user2", "food", 150.0));**Ожидаемый результат:**
Ожидаемый результат:

**Подсказка:** Используйте `.keyBy(0, 1)` для группировки по двум полям---## Инструкции по решению### 1. Базовый шаблон программы:import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;import org.apache.flink.streaming.api.datastream.DataStream;import org.apache.flink.api.java.tuple.Tuple2;public class FlinkTask {    public static void main(String[] args) throws Exception {        // Создание окружения выполнения        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();                // Создание потока данных        DataStream<Integer> numbers = env.fromElements(1, 2, 3, 4, 5);                // Обработка данных        DataStream<Integer> result = numbers            .filter(x -> x > 2)            .map(x -> x * 2);                // Вывод результата        result.print();                // Запуск программы (ОБЯЗАТЕЛЬНО!)        env.execute("Flink Task");    }}### 2. Необходимые импорты:import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;import org.apache.flink.streaming.api.datastream.DataStream;import org.apache.flink.streaming.api.datastream.KeyedStream;import org.apache.flink.streaming.api.windowing.time.Time;import org.apache.flink.streaming.api.windowing.windows.TimeWindow;import org.apache.flink.api.java.tuple.Tuple2;import org.apache.flink.api.java.tuple.Tuple3;import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;import org.apache.flink.streaming.api.windowing.assigners.SlidingProcessingTimeWindows;### 3. Maven зависимости (pom.xml):<dependencies>    <dependency>        <groupId>org.apache.flink</groupId>        <artifactId>flink-streaming-java</artifactId>        <version>1.18.0</version>    </dependency>    <dependency>        <groupId>org.apache.flink</groupId>        <artifactId>flink-clients</artifactId>        <version>1.18.0</version>    </dependency></dependencies>### 4. Важные замечания:- **Всегда вызывайте `env.execute()`** - без этого программа не запустится- **Используйте `keyBy()` перед окнами** - окна работают только на keyed streams- **Для тестирования используйте `env.fromElements()`** - это создает поток из элементов- **Результат выводится в консоль** - используйте `.print()` для просмотра### 5. Структура решения:1. Создайте файл для каждой задачи (например, `FlinkTask1_1.java`)2. Используйте базовый шаблон3. Реализуйте логику обработки4. Запустите программу**Удачи в изучении Flink! 🚀**
Подсказка: Используйте .filter(x -> x % 2 == 0).map(x -> x * x).keyBy(x -> 1).sum(0)


Задача  2.4: Разделение предложений на слова
Используя Flink DataStream API, разделите предложения на отдельные слова.
Вход:
DataStream<String> sentences = env.fromElements("hello world", "flink stream", "java api");
Ожидаемый результат:
1> hello2> world3> flink4> stream5> java6> api
Подсказка: Используйте .flatMap() с Collector


Задача  2.5: Подсчет суммы по категориям
Используя Flink DataStream API, подсчитайте сумму транзакций по каждой категории.
Вход:
// Tuple2<категория, сумма>DataStream<Tuple2<String, Integer>> transactions = env.fromElements(    Tuple2.of("food", 100),    Tuple2.of("electronics", 500),    Tuple2.of("food", 200),    Tuple2.of("electronics", 300));
Ожидаемый результат:
1> (food,300)2> (electronics,800)
Подсказка: Используйте .keyBy(0) и .sum(1)
Уровень 3: Окна (Windows) - КЛЮЧЕВАЯ КОНЦЕПЦИЯ FLINK!


Задача  3.1: Сумма за окно времени
Используя Flink DataStream API, подсчитайте сумму чисел за каждое окно в 5 секунд.
Вход:
DataStream<Integer> numbers = env.fromElements(1, 2, 3, 4, 5, 6, 7, 8);
Подсказка: Используйте .keyBy(x -> 1).window(TumblingProcessingTimeWindows.of(Time.seconds(5))).sum(0)
Ожидаемый результат: Сумма всех чисел в окне (зависит от времени выполнения)


Задача  3.2: Максимальное значение за окно
Используя Flink DataStream API, найдите максимальное значение за каждое окно в 10 секунд.
Вход:
DataStream<Integer> numbers = env.fromElements(10, 5, 20, 15, 30, 25);
Подсказка: Используйте .keyBy(x -> 1).window(...).max(0)


Задача  3.3: Подсчет событий по пользователю за минуту
Используя Flink DataStream API, подсчитайте количество событий для каждого пользователя за окно в 1 минуту.
Вход:
// Tuple2<userId, eventType>DataStream<Tuple2<String, String>> events = env.fromElements(    Tuple2.of("user1", "click"),    Tuple2.of("user2", "view"),    Tuple2.of("user1", "click"),    Tuple2.of("user1", "view"));
Подсказка: Используйте .keyBy(0), .window(TumblingProcessingTimeWindows.of(Time.minutes(1))) и .count()
Ожидаемый результат: Количество событий для каждого пользователя в окне


Задача  3.4: Среднее значение за окно
Используя Flink DataStream API, найдите среднее значение чисел за каждое окно в 5 секунд.
Вход:
DataStream<Integer> numbers = env.fromElements(10, 20, 30, 40, 50);
Подсказка: Используйте .aggregate() или .process() с WindowFunction


Задача  3.5: Скользящее окно (Sliding Window)
Используя Flink DataStream API, подсчитайте сумму за скользящее окно (размер 10 секунд, сдвиг 5 секунд).
Вход:
DataStream<Integer> numbers = env.fromElements(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
Подсказка: Используйте SlidingProcessingTimeWindows.of(Time.seconds(10), Time.seconds(5))
Уровень 4: Работа с объектами


Задача  4.1: Класс Transaction
public class Transaction {    private String userId;    private double amount;    private String category;    private long timestamp;        public Transaction(String userId, double amount, String category, long timestamp) {        this.userId = userId;        this.amount = amount;        this.category = category;        this.timestamp = timestamp;    }        // геттеры и сеттеры    public String getUserId() { return userId; }    public double getAmount() { return amount; }    public String getCategory() { return category; }    public long getTimestamp() { return timestamp; }}


Задача : Используя Flink DataStream API, отфильтруйте транзакции с суммой > 1000 и выведите userId.
Вход:
DataStream<Transaction> transactions = env.fromElements(    new Transaction("user1", 500, "food", System.currentTimeMillis()),    new Transaction("user2", 1500, "electronics", System.currentTimeMillis()),    new Transaction("user3", 2000, "food", System.currentTimeMillis()));
Ожидаемый результат:
1> user22> user3


Задача  4.2: Группировка по категории
Используя Flink DataStream API, сгруппируйте транзакции по категории и подсчитайте сумму.
Вход: (те же данные из 4.1)
Ожидаемый результат:
1> (food, 2500.0)2> (electronics, 1500.0)
Подсказка: Используйте .keyBy(Transaction::getCategory) и .sum("amount") или .aggregate()


Задача  4.3: Средняя сумма по категории за минуту
Используя Flink DataStream API, найдите среднюю сумму транзакций по каждой категории за окно в 1 минуту.
Вход: (те же данные из 4.1, добавьте больше транзакций)
Подсказка: Используйте .keyBy(), .window() и .aggregate() с вычислением среднего
Уровень 5: Продвинутые задачи


Задача  5.1: Объединение потоков (Union)
Используя Flink DataStream API, объедините два потока данных.
Вход:
DataStream<Integer> stream1 = env.fromElements(1, 2, 3);DataStream<Integer> stream2 = env.fromElements(4, 5, 6);
Ожидаемый результат:
1> 12> 23> 34> 45> 56> 6
Подсказка: Используйте .union()


Задача  5.2: Подсчет элементов в потоке
Используя Flink DataStream API, подсчитайте общее количество элементов в потоке.
Вход:
DataStream<Integer> numbers = env.fromElements(1, 2, 3, 4, 5, 6);
Ожидаемый результат:
1> 6
Подсказка: Используйте .keyBy(x -> 1).count() или .map(x -> 1).keyBy(x -> 1).sum(0)


Задача  5.3: Группировка и агрегация с несколькими полями
Используя Flink DataStream API, сгруппируйте транзакции по userId и категории, затем подсчитайте сумму.
Вход:
// Tuple3<userId, category, amount>DataStream<Tuple3<String, String, Double>> transactions = env.fromElements(    Tuple3.of("user1", "food", 100.0),    Tuple3.of("user1", "food", 200.0),    Tuple3.of("user1", "electronics", 500.0),    Tuple3.of("user2", "food", 150.0));
Ожидаемый результат:
1> (user1, food, 300.0)2> (user1, electronics, 500.0)3> (user2, food, 150.0)
Подсказка: Используйте .keyBy(0, 1) для группировки по двум полям
Инструкции по решению
1. Базовый шаблон программы:
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;import org.apache.flink.streaming.api.datastream.DataStream;import org.apache.flink.api.java.tuple.Tuple2;public class FlinkTask {    public static void main(String[] args) throws Exception {        // Создание окружения выполнения        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();                // Создание потока данных        DataStream<Integer> numbers = env.fromElements(1, 2, 3, 4, 5);                // Обработка данных        DataStream<Integer> result = numbers            .filter(x -> x > 2)            .map(x -> x * 2);                // Вывод результата        result.print();                // Запуск программы (ОБЯЗАТЕЛЬНО!)        env.execute("Flink Task");    }}
2. Необходимые импорты:
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;import org.apache.flink.streaming.api.datastream.DataStream;import org.apache.flink.streaming.api.datastream.KeyedStream;import org.apache.flink.streaming.api.windowing.time.Time;import org.apache.flink.streaming.api.windowing.windows.TimeWindow;import org.apache.flink.api.java.tuple.Tuple2;import org.apache.flink.api.java.tuple.Tuple3;import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;import org.apache.flink.streaming.api.windowing.assigners.SlidingProcessingTimeWindows;
3. Maven зависимости (pom.xml):
<dependencies>    <dependency>        <groupId>org.apache.flink</groupId>        <artifactId>flink-streaming-java</artifactId>        <version>1.18.0</version>    </dependency>    <dependency>        <groupId>org.apache.flink</groupId>        <artifactId>flink-clients</artifactId>        <version>1.18.0</version>    </dependency></dependencies>
4. Важные замечания:
Всегда вызывайте env.execute() - без этого программа не запустится
Используйте keyBy() перед окнами - окна работают только на keyed streams
Для тестирования используйте env.fromElements() - это создает поток из элементов
Результат выводится в консоль - используйте .print() для просмотра
5. Структура решения:
Создайте файл для каждой задачи (например, FlinkTask1_1.java)
Используйте базовый шаблон
Реализуйте логику обработки
Запустите программу
Удачи в изучении Flink! 🚀
Файл с 

Задача ми готов. Он включает:- 5 уровней сложности (от базовых операций до продвинутых)- 23 задачи с примерами входных данных- Подсказки для каждой задачи- Инструкции по настройке и решению- Базовый шаблон программыНачните с уровня 1 — задачи похожи на Stream API, которые вы уже знаете.

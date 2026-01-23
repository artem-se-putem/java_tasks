# Теория по Apache Flink

## Что такое Apache Flink?

**Apache Flink** - это фреймворк для распределенной обработки потоков данных (stream processing) в реальном времени. Позволяет обрабатывать большие объемы данных на кластере машин.

### Сравнение с Java Stream API:

**Java Stream API** (вы уже знаете):
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
List<Integer> result = numbers.stream()
    .filter(x -> x > 2)
    .map(x -> x * 2)
    .collect(Collectors.toList());
// Обработка в памяти одной машины**Apache Flink**:
DataStream<Integer> numbers = env.fromElements(1, 2, 3, 4, 5);
DataStream<Integer> result = numbers
    .filter(x -> x > 2)
    .map(x -> x * 2);
// Распределенная обработка на кластере, может обрабатывать миллионы событий/сек---

## Основные концепции

### 1. DataStream - поток данных
- **Не хранит данные** (как Stream API)
- Данные обрабатываются **в реальном времени**
- Может быть **бесконечным** (поток событий)
- Операции бывают **преобразующие** и **терминальные**

### 2. Execution Environmenta
StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
DataStream<String> stream = env.fromElements("a", "b", "c");### 3. Цепочка операций (как в Stream API)
source
    .операция1()      // преобразование
    .операция2()      // преобразование
    .терминальная()   // вывод результата
---

## Установка и настройка

### Зависимости Maven (pom.xml):
<dependencies>
    <dependency>
        <groupId>org.apache.flink</groupId>
        <artifactId>flink-streaming-java</artifactId>
        <version>1.18.0</version>
    </dependency>
    <dependency>
        <groupId>org.apache.flink</groupId>
        <artifactId>flink-clients</artifactId>
        <version>1.18.0</version>
    </dependency>
</dependencies>### Базовый шаблон программы:
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.datastream.DataStream;

public class FlinkExample {
    public static void main(String[] args) throws Exception {
        // Создание окружения выполнения
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        
        // Создание потока данных
        DataStream<Integer> numbers = env.fromElements(1, 2, 3, 4, 5);
        
        // Обработка
        DataStream<Integer> result = numbers
            .filter(x -> x > 2)
            .map(x -> x * 2);
        
        // Вывод результата
        result.print();
        
        // Запуск программы
        env.execute("Flink Example");
    }
}---

## Создание DataStream

### 1. Из элементов (для тестирования):a
DataStream<Integer> stream = env.fromElements(1, 2, 3, 4, 5);
DataStream<String> words = env.fromElements("hello", "world", "flink");### 2. Из коллекции:
List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
DataStream<Integer> stream = env.fromCollection(list);### 3. Из файла:ava
DataStream<String> lines = env.readTextFile("path/to/file.txt");### 4. Из сокета (для тестирования):
DataStream<String> stream = env.socketTextStream("localhost", 9999);### 5. Из Kafka (реальный источник):va
// Требует дополнительные зависимости
Properties props = new Properties();
props.setProperty("bootstrap.servers", "localhost:9092");
FlinkKafkaConsumer<String> consumer = new FlinkKafkaConsumer<>("topic", 
    new SimpleStringSchema(), props);
DataStream<String> stream = env.addSource(consumer);---

## Преобразующие операции (Transformation)

### 1. `filter()` - фильтрация (как в Stream API)
DataStream<Integer> numbers = env.fromElements(1, 2, 3, 4, 5);
DataStream<Integer> evens = numbers.filter(x -> x % 2 == 0);
// Результат: 2, 4### 2. `map()` - преобразование (как в Stream API)
DataStream<String> words = env.fromElements("hello", "world");
DataStream<Integer> lengths = words.map(String::length);
// Результат: 5, 5### 3. `flatMap()` - разворачивание (как в Stream API)
DataStream<String> sentences = env.fromElements("hello world", "flink stream");
DataStream<String> words = sentences.flatMap((String sentence, Collector<String> out) -> {
    for (String word : sentence.split(" ")) {
        out.collect(word);
    }
}).returns(Types.STRING);
// Результат: "hello", "world", "flink", "stream"
### 4. `keyBy()` - группировка по ключу (НОВОЕ!)va
DataStream<Tuple2<String, Integer>> data = env.fromElements(
    Tuple2.of("A", 1),
    Tuple2.of("B", 2),
    Tuple2.of("A", 3)
);

KeyedStream<Tuple2<String, Integer>, String> keyed = data.keyBy(0);
// Группирует по первому полю (String)### 5. `sum()`, `min()`, `max()` - агрегация
keyedStream.sum(1);  // сумма по второму полю
keyedStream.min(1);  // минимум по второму полю
keyedStream.max(1);  // максимум по второму полю---

## Окна (Windows) - КЛЮЧЕВАЯ КОНЦЕПЦИЯ FLINK!

Окна позволяют группировать события по времени или количеству.

### Типы окон:

#### 1. Tumbling Windows (неперекрывающиеся)ava
stream
    .keyBy(0)
    .window(TumblingProcessingTimeWindows.of(Time.seconds(5)))
    .sum(1);
// Окна по 5 секунд: [0-5), [5-10), [10-15), ...#### 2. Sliding Windows (перекрывающиеся)
stream
    .keyBy(0)
    .window(SlidingProcessingTimeWindows.of(Time.seconds(10), Time.seconds(5)))
    .sum(1);
// Окна по 10 секунд, сдвиг на 5 секунд#### 3. Session Windows (по активности)
stream
    .keyBy(0)
    .window(ProcessingTimeSessionWindows.withGap(Time.seconds(30)))
    .sum(1);
// Окно закрывается, если нет событий 30 секунд### Пример с окнами:
DataStream<Tuple2<String, Integer>> transactions = ...;

DataStream<Tuple2<String, Integer>> result = transactions
    .keyBy(0)  // группировка по userId
    .window(TumblingProcessingTimeWindows.of(Time.minutes(1)))  // окно 1 минута
    .sum(1);  // сумма транзакций за минуту---

## Терминальные операции (Sinks)

### 1. `print()` - вывод в консоль
stream.print();
### 2. `writeAsText()` - запись в файл
stream.writeAsText("output.txt");### 3. `addSink()` - кастомный вывод
stream.addSink(new FlinkKafkaProducer<>("topic", ...));---

## Водяные знаки (Watermarks)

Водяные знаки позволяют обрабатывать события с задержками.

stream
    .assignTimestampsAndWatermarks(
        WatermarkStrategy
            .<Event>forBoundedOutOfOrderness(Duration.ofSeconds(10))
            .withTimestampAssigner((event, timestamp) -> event.getTimestamp())
    )
    .keyBy(Event::getUserId)
    .window(TumblingEventTimeWindows.of(Time.minutes(1)))
    .sum("amount");---

## Состояние (State)

Flink может хранить состояние между событиями:

public class CountFunction extends RichFlatMapFunction<String, Tuple2<String, Integer>> {
    private transient ValueState<Integer> count;
    
    @Override
    public void open(Configuration config) {
        ValueStateDescriptor<Integer> descriptor = 
            new ValueStateDescriptor<>("count", Integer.class);
        count = getRuntimeContext().getState(descriptor);
    }
    
    @Override
    public void flatMap(String value, Collector<Tuple2<String, Integer>> out) {
        Integer current = count.value();
        if (current == null) current = 0;
        current++;
        count.update(current);
        out.collect(Tuple2.of(value, current));
    }
}---

## Сравнение с Stream API

| Концепция | Java Stream API | Apache Flink |
|-----------|----------------|--------------|
| **Источник данных** | Коллекции в памяти | Файлы, Kafka, сокеты, БД |
| **Масштаб** | Одна машина | Кластер машин |
| **Окна** | ❌ Нет | ✅ Есть (время, количество) |
| **Состояние** | ❌ Нет | ✅ Есть |
| **Водяные знаки** | ❌ Нет | ✅ Есть |
| **Потоковая обработка** | ❌ Нет (только batch) | ✅ Да |
| **Восстановление** | ❌ Нет | ✅ Checkpointing |

---

## Важные замечания

1. **Всегда вызывайте `env.execute()`** - это запускает программу
2. **Используйте `keyBy()` перед окнами** - окна работают только на keyed streams
3. **Водяные знаки нужны для event time** - для обработки событий с задержками
4. **Состояние требует checkpointing** - для восстановления после сбоев

---

## Резюме

**Основные операции:**
- `filter()` - фильтрация (как в Stream API)
- `map()` - преобразование (как в Stream API)
- `flatMap()` - разворачивание (как в Stream API)
- `keyBy()` - группировка по ключу
- `window()` - окна для временной группировки
- `sum()`, `min()`, `max()` - агрегация

**Новые концепции Flink:**
- Окна (Windows) - группировка по времени
- Водяные знаки (Watermarks) - обработка задержек
- Состояние (State) - хранение данных между событиями
- Распределенность - обработка на кластере

**Удачи в изучении Flink! 🚀**
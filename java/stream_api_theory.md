# Теория по Stream API в Java

## Что такое Stream API?

**Stream API** (Java 8+) - это способ обработки коллекций данных в функциональном стиле. Позволяет писать код короче и понятнее.

### Аналогия с Python:
# Python
numbers = [1, 2, 3, 4, 5]
result = [x * 2 for x in numbers if x > 2]
# [6, 8, 10]
// Java Stream API
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
List<Integer> result = numbers.stream()
    .filter(x -> x > 2)
    .map(x -> x * 2)
    .collect(Collectors.toList());
// [6, 8, 10]---

## Основные концепции

### 1. Stream - это поток данных
- Не хранит данные (это не коллекция!)
- Данные обрабатываются "на лету"
- Можно обработать только один раз
- Операции бывают **промежуточные** (intermediate) и **терминальные** (terminal)

### 2. Цепочка операций
source.stream()
    .операция1()      // промежуточная
    .операция2()      // промежуточная
    .терминальная()   // завершает цепочку---

## Создание Stream

### Из коллекций:
List<String> list = Arrays.asList("a", "b", "c");
Stream<String> stream = list.stream();### Из массива:
int[] arr = {1, 2, 3};
IntStream stream = Arrays.stream(arr);### Из значений:
Stream<String> stream = Stream.of("a", "b", "c");### Генерация:
Stream<Integer> stream = Stream.generate(() -> 1).limit(10);
Stream<Integer> stream = Stream.iterate(0, n -> n + 1).limit(10);
---

## Промежуточные операции (Intermediate)

### 1. `filter(Predicate<T>)` - фильтрация
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
List<Integer> evens = numbers.stream()
    .filter(n -> n % 2 == 0)  // оставить только четные
    .collect(Collectors.toList());
// [2, 4]### 2. `map(Function<T, R>)` - преобразование
List<String> words = Arrays.asList("hello", "world");
List<Integer> lengths = words.stream()
    .map(String::length)  // преобразовать в длины
    .collect(Collectors.toList()); // Как использоваться collect, в спарке он собирает все данные на одной ноде, а тут что делает, какие методы еще есть у Collectors, как правильно его применять?
// [5, 5]

// Или с лямбдой:
.map(s -> s.length())### 3. `flatMap(Function<T, Stream<R>>)` - "разворачивание"
List<List<Integer>> lists = Arrays.asList(
    Arrays.asList(1, 2),
    Arrays.asList(3, 4)
);
List<Integer> flat = lists.stream()
    .flatMap(List::stream)  // развернуть вложенные списки
    .collect(Collectors.toList());
// [1, 2, 3, 4]### 4. `distinct()` - удаление дубликатов
List<Integer> numbers = Arrays.asList(1, 2, 2, 3, 3, 3);
List<Integer> unique = numbers.stream()
    .distinct()
    .collect(Collectors.toList());
// [1, 2, 3]### 5. `sorted()` - сортировка
List<Integer> numbers = Arrays.asList(3, 1, 4, 1, 5);
List<Integer> sorted = numbers.stream()
    .sorted()
    .collect(Collectors.toList());
// [1, 1, 3, 4, 5]

// С компаратором:
.sorted((a, b) -> b - a)  // обратный порядок
.sorted(Comparator.reverseOrder())### 6. `limit(long)` - ограничение количества // Че за компаратор? Для чего применяется?
Stream<Integer> stream = Stream.iterate(0, n -> n + 1) // Как работает iterate
    .limit(5);  // взять только первые 5
// 0, 1, 2, 3, 4### 7. `skip(long)` - пропуск элементов
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
List<Integer> skipped = numbers.stream()
    .skip(2)  // пропустить первые 2 //
    .collect(Collectors.toList());
// [3, 4, 5]### 8. `peek(Consumer<T>)` - выполнить действие без измененияa
List<Integer> numbers = Arrays.asList(1, 2, 3);
numbers.stream()
    .peek(n -> System.out.println("Processing: " + n)) // Как вообще правильно передавать сюда аргументы, не только в peek но и в другие методы Stream api
    .map(n -> n * 2)
    .collect(Collectors.toList());
// Выведет: Processing: 1, Processing: 2, Processing: 3---

## Терминальные операции (Terminal)

### 1. `collect(Collector)` - собрать в коллекцию
List<String> list = stream.collect(Collectors.toList());
Set<String> set = stream.collect(Collectors.toSet());### 2. `forEach(Consumer<T>)` - выполнить действие для каждого
stream.forEach(System.out::println);
stream.forEach(s -> System.out.println(s));### 3. `count()` - подсчет элементов
long count = stream.count();### 4. `anyMatch(Predicate)` - есть ли хотя бы одинa
boolean hasEven = numbers.stream()
    .anyMatch(n -> n % 2 == 0);### 5. `allMatch(Predicate)` - все ли соответствуют
boolean allPositive = numbers.stream()
    .allMatch(n -> n > 0);### 6. `noneMatch(Predicate)` - ни один не соответствует
boolean noNegatives = numbers.stream()
    .noneMatch(n -> n < 0);### 7. `findFirst()` - первый элемент
Optional<Integer> first = stream.findFirst();
if (first.isPresent()) { // что такое isPresent()
    System.out.println(first.get());
}### 8. `findAny()` - любой элемент
Optional<Integer> any = stream.findAny();### 9. `min(Comparator)` / `max(Comparator)` - минимум/максимум
Optional<Integer> min = numbers.stream()
    .min(Integer::compareTo); // Как работать с :: что такое Integer::
Optional<Integer> max = numbers.stream()
    .max((a, b) -> a - b);### 10. `reduce()` - свертка (агрегация)
// Сумма всех элементов
Optional<Integer> sum = numbers.stream()
    .reduce((a, b) -> a + b);

// С начальным значением
Integer sum = numbers.stream()
    .reduce(0, (a, b) -> a + b);

// Или проще:
int sum = numbers.stream()
    .mapToInt(Integer::intValue)
    .sum();---

## Collectors - сбор результатов

### Основные коллекторы:

import java.util.stream.Collectors;

// В список
List<String> list = stream.collect(Collectors.toList());

// В множество
Set<String> set = stream.collect(Collectors.toSet());

// В массив
String[] array = stream.toArray(String[]::new);

// В Map (ключ -> значение)
Map<String, Integer> map = stream.collect(
    Collectors.toMap(
        Person::getName,      // ключ
        Person::getAge       // значение
    )
);

// Группировка
Map<String, List<Person>> grouped = persons.stream()
    .collect(Collectors.groupingBy(Person::getCity));

// Подсчет
Map<String, Long> counts = persons.stream()
    .collect(Collectors.groupingBy(
        Person::getCity,
        Collectors.counting()
    ));

// Агрегация (сумма, среднее и т.д.)
Map<String, Double> avgAge = persons.stream()
    .collect(Collectors.groupingBy(
        Person::getCity,
        Collectors.averagingInt(Person::getAge)
    ));

// Объединение строк
String joined = stream.collect(Collectors.joining(", "));---

## Функциональные интерфейсы

### Predicate<T> - проверка условия
Predicate<Integer> isEven = n -> n % 2 == 0;
boolean result = isEven.test(4);  // true### Function<T, R> - преобразование
Function<String, Integer> length = String::length;
int len = length.apply("hello");  // 5### Consumer<T> - действие без возврата
Consumer<String> printer = System.out::println;
printer.accept("Hello");  // выведет "Hello"### Supplier<T> - генератор
Supplier<Integer> random = () -> new Random().nextInt(100);
int value = random.get();---

## Примеры комбинаций

### Пример 1: Фильтрация и преобразование
List<String> words = Arrays.asList("hello", "world", "java", "stream");
List<String> result = words.stream()
    .filter(w -> w.length() > 4)      // оставить слова длиннее 4
    .map(String::toUpperCase)        // преобразовать в верхний регистр
    .sorted()                         // отсортировать
    .collect(Collectors.toList());
// ["HELLO", "STREAM", "WORLD"]### Пример 2: Группировка и агрегация
class Person {
    String name;
    int age;
    String city;
    // конструкторы, геттеры
}

List<Person> persons = ...;

// Группировка по городу с подсчетом
Map<String, Long> cityCounts = persons.stream()
    .collect(Collectors.groupingBy(
        Person::getCity,
        Collectors.counting()
    ));

// Средний возраст по городам
Map<String, Double> avgAgeByCity = persons.stream()
    .collect(Collectors.groupingBy(
        Person::getCity,
        Collectors.averagingInt(Person::getAge)
    ));### Пример 3: Поиск и проверка
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

// Есть ли четные числа?
boolean hasEven = numbers.stream()
    .anyMatch(n -> n % 2 == 0);  // true

// Все ли положительные?
boolean allPositive = numbers.stream()
    .allMatch(n -> n > 0);  // true

// Найти первое четное
Optional<Integer> firstEven = numbers.stream()
    .filter(n -> n % 2 == 0)
    .findFirst();
// Optional[2]### Пример 4: Агрегацияva
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

// Сумма
int sum = numbers.stream()
    .mapToInt(Integer::intValue)
    .sum();  // 15

// Произведение
int product = numbers.stream()
    .reduce(1, (a, b) -> a * b);  // 120

// Максимум
Optional<Integer> max = numbers.stream()
    .max(Integer::compareTo);  // Optional[5]---

## Ленивое выполнение (Lazy Evaluation)

Stream API выполняет операции **лениво** - только когда нужен результат:

List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

Stream<Integer> stream = numbers.stream()
    .filter(n -> {
        System.out.println("Filtering: " + n);
        return n % 2 == 0;
    })
    .map(n -> {
        System.out.println("Mapping: " + n);
        return n * 2;
    });

// Пока ничего не выполнилось!
// Выполнение начнется только при вызове терминальной операции:

stream.collect(Collectors.toList());
// Теперь выполнится:
// Filtering: 1
// Filtering: 2
// Mapping: 2
// Filtering: 3
// Filtering: 4
// Mapping: 4
// Filtering: 5---

## Параллельные стримы

Можно обрабатывать данные параллельно:

List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

// Обычный стрим
int sum = numbers.stream()
    .mapToInt(Integer::intValue)
    .sum();

// Параллельный стрим
int sumParallel = numbers.parallelStream()
    .mapToInt(Integer::intValue)
    .sum();**Важно:** Параллельные стримы полезны только для больших объемов данных!

---

## Сравнение с обычными циклами

### Обычный способ:
List<Integer> result = new ArrayList<>();
for (Integer n : numbers) {
    if (n % 2 == 0) {
        result.add(n * 2);
    }
}### Stream API:
List<Integer> result = numbers.stream()
    .filter(n -> n % 2 == 0)
    .map(n -> n * 2)
    .collect(Collectors.toList());**Преимущества Stream API:**
- Код короче и читабельнее
- Легко комбинировать операции
- Функциональный стиль
- Легко распараллелить
- Ленивое выполнение

---

## Важные замечания

1. **Stream можно использовать только один раз!**
Stream<Integer> stream = numbers.stream();
stream.filter(...);  // OK
stream.map(...);     // ❌ ОШИБКА! Stream уже использован2. **Терминальная операция обязательна!**
numbers.stream().filter(...);  // ❌ Ничего не произойдет!
numbers.stream().filter(...).collect(...);  // ✅ Работает3. **Не изменяйте коллекцию во время итерации!**
// ❌ Плохо
list.stream().forEach(item -> list.remove(item));

// ✅ Хорошо
list.removeIf(item -> условие);---

## Резюме

**Основные операции:**
- `filter` - фильтрация
- `map` - преобразование
- `flatMap` - разворачивание
- `distinct` - уникальные
- `sorted` - сортировка
- `limit` / `skip` - ограничение
- `collect` - сбор результатов
- `forEach` - выполнение действия
- `reduce` - агрегация
- `findFirst` / `findAny` - поиск
- `anyMatch` / `allMatch` / `noneMatch` - проверки

**Помните:**
- Промежуточные операции возвращают Stream
- Терминальные операции завершают цепочку
- Stream выполняется лениво
- Stream можно использовать только один раз

**Удачи в изучении! 🚀**
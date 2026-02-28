# Теория по Java для Python разработчиков

## Основные отличия Java от Python

| Python | Java |
|--------|------|
| Динамическая типизация | Статическая типизация |
| Интерпретируемый | Компилируемый (JVM) |
| `x = 5` | `int x = 5;` |
| Нет классов для простых программ | Все в классах |
| Отступы для блоков | Фигурные скобки `{}` |
| `def function():` | `public static void function() {}` |

---

## 1. Основы синтаксиса

### Структура программы
// Имя файла должно совпадать с именем класса
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}### Переменные и типы данных
// Примитивные типы
int x = 10;              // 32-битное целое (-2³¹ до 2³¹-1)
long y = 100L;           // 64-битное целое
float z = 3.14f;         // 32-битное число с плавающей точкой
double w = 3.14159;      // 64-битное число с плавающей точкой
char ch = 'A';           // один символ (16-бит Unicode)
boolean flag = true;     // true или false
byte b = 127;            // 8-битное целое (-128 до 127)
short s = 32767;         // 16-битное целое

// Ссылочные типы (объекты)
String text = "Hello";   // строка (НЕ примитив!)
Integer num = 10;        // обертка над int (объект)
### Константы
final int MAX_SIZE = 100;
final double PI = 3.14159;

### Массивы
// Объявление и инициализация
int[] arr1 = new int[5];              // массив из 5 нулей
int[] arr2 = {1, 2, 3, 4, 5};         // массив с начальными значениями
int[] arr3 = new int[]{1, 2, 3};      // альтернативный синтаксис

// Доступ к элементам
int first = arr2[0];                  // первый элемент
arr2[0] = 10;                         // изменение элемента
int length = arr2.length;             // длина массива (не метод!)

// Двумерные массивы
int[][] matrix = new int[3][4];
int[][] matrix2 = {{1, 2}, {3, 4}};### Строки (String)
String s1 = "Hello";
String s2 = new String("World");

// Конкатенация
String result = s1 + " " + s2;        // "Hello World"

// Методы строк
int len = s1.length();                // длина (МЕТОД, не свойство!)
boolean isEmpty = s1.isEmpty();
String upper = s1.toUpperCase();
String lower = s1.toLowerCase();
boolean contains = s1.contains("ell");
String substr = s1.substring(1, 4);   // с 1 по 3 индекс
String[] parts = "a,b,c".split(",");
boolean equal = s1.equals(s2);        // сравнение строк (НЕ ==!)
boolean eq2 = s1.equalsIgnoreCase("HELLO");
char ch = s1.charAt(0);               // символ по индексу

// Итерация по строке
// Способ 1: по индексу
for (int i = 0; i < s1.length(); i++) {
    char c = s1.charAt(i);
    System.out.println(c);
}

// Способ 2: через toCharArray()
for (char c : s1.toCharArray()) {
    System.out.println(c);
}

// Способ 3: Stream API (Java 8+)
s1.chars().forEach(c -> System.out.println((char) c));

// Способ 4: Stream API с фильтрацией
s1.chars()
    .filter(c -> c != 'l')  // убрать все 'l'
    .mapToObj(c -> (char) c)
    .forEach(System.out::print);---

## 2. Управляющие конструкции

### Условия
if (x > 0) {
    System.out.println("positive");
} else if (x < 0) {
    System.out.println("negative");
} else {
    System.out.println("zero");
}

// Тернарный оператор
int max = (a > b) ? a : b;### Switch
switch (day) {
    case 1:
        System.out.println("Monday");
        break;
    case 2:
    case 3:
        System.out.println("Tuesday or Wednesday");
        break;
    default:
        System.out.println("Other day");
}

// Switch с выражениями (Java 14+)
String result = switch (day) {
    case 1 -> "Monday";
    case 2, 3 -> "Tuesday or Wednesday";
    default -> "Other day";
};### Циклы
// for цикл
for (int i = 0; i < 10; i++) {
    System.out.println(i);
}

// for-each цикл (улучшенный for)
int[] arr = {1, 2, 3, 4, 5};
for (int num : arr) {
    System.out.println(num);
}

// while цикл
while (x > 0) {
    x--;
}

// do-while цикл
do {
    x--;
} while (x > 0);

// break и continue
for (int i = 0; i < 10; i++) {
    if (i == 5) break;      // выход из цикла
    if (i % 2 == 0) continue; // переход к следующей итерации
}---

## 3. Функции (Методы)

// Метод в классе
public class Calculator {
    // Метод без возвращаемого значения
    public void printHello() {
        System.out.println("Hello");
    }
    
    // Метод с возвращаемым значением
    public int add(int a, int b) {
        return a + b;
    }
    
    // Метод с несколькими параметрами
    public double calculate(double a, double b, String operation) {
        switch (operation) {
            case "add": return a + b;
            case "multiply": return a * b;
            default: return 0;
        }
    }
    
    // Перегрузка методов (разные сигнатуры)
    public int multiply(int a, int b) {
        return a * b;
    }
    
    public double multiply(double a, double b) {
        return a * b;
    }
    
    // Varargs (переменное количество аргументов)
    public int sum(int... numbers) {
        int total = 0;
        for (int num : numbers) {
            total += num;
        }
        return total;
    }
}

// Использование
Calculator calc = new Calculator();
int result = calc.add(5, 3);
int sum = calc.sum(1, 2, 3, 4, 5);
### Статические методы
public class MathUtils {
    public static int add(int a, int b) {
        return a + b;
    }
}

// Вызов без создания объекта
int result = MathUtils.add(5, 3);---

## 4. Классы и объекты

### Базовый класс
public class Person {
    // Поля (атрибуты)
    private String name;      // private - доступ только внутри класса
    private int age;
    
    // Конструктор
    public Person(String name, int age) {
        this.name = name;     // this - ссылка на текущий объект
        this.age = age;
    }
    
    // Геттеры (getters)
    public String getName() {
        return name;
    }
    
    public int getAge() {
        return age;
    }
    
    // Сеттеры (setters)
    public void setName(String name) {
        this.name = name;
    }
    
    public void setAge(int age) {
        if (age > 0) {
            this.age = age;
        }
    }
    
    // Метод класса
    public void introduce() {
        System.out.println("Hi, I'm " + name + " and I'm " + age + " years old");
    }
}

// Использование
Person person = new Person("Alice", 30);
person.introduce();### Модификаторы доступа
- `private` - доступ только внутри класса
- `protected` - доступ в классе и подклассах
- `public` - доступ везде
- `package-private` (по умолчанию) - доступ в том же пакете

### Ключевое слово `this`
public class Point {
    private int x;
    private int y;
    
    public Point(int x, int y) {
        this.x = x;  // this.x - поле класса, x - параметр
        this.y = y;
    }
}---

## 5. Коллекции (Collections)

### ArrayList (динамический массив)
import java.util.ArrayList;
import java.util.List;

List<Integer> list = new ArrayList<>();
list.add(1);
list.add(2);
list.add(3);

int size = list.size();
boolean empty = list.isEmpty();
int value = list.get(0);           // получение элемента
list.set(0, 10);                   // изменение элемента
list.remove(0);                    // удаление по индексу
boolean contains = list.contains(2);
int index = list.indexOf(2);

// Итерация
for (int i = 0; i < list.size(); i++) {
    System.out.println(list.get(i));
}

for (int num : list) {
    System.out.println(num);
}

### HashMap (словарь)
import java.util.HashMap;
import java.util.Map;

Map<String, Integer> map = new HashMap<>();
map.put("apple", 5);
map.put("banana", 3);
map.put("cherry", 8);

int value = map.get("apple");      // получение значения
map.put("apple", 10);              // изменение значения
boolean contains = map.containsKey("apple");
map.remove("banana");              // удаление
int size = map.size();

// Итерация
for (String key : map.keySet()) {
    System.out.println(key + ": " + map.get(key));
}

for (Map.Entry<String, Integer> entry : map.entrySet()) {
    System.out.println(entry.getKey() + ": " + entry.getValue());
}### HashSet (множество)
import java.util.HashSet;
import java.util.Set;

Set<Integer> set = new HashSet<>();
set.add(1);
set.add(2);
set.add(3);
set.add(1);  // дубликат не добавится

boolean contains = set.contains(2);
set.remove(2);
int size = set.size();

for (int num : set) {
    System.out.println(num);
}### Инициализация коллекций
// Java 9+
List<Integer> list = List.of(1, 2, 3, 4, 5);
Map<String, Integer> map = Map.of("a", 1, "b", 2);
Set<String> set = Set.of("apple", "banana");---

## 6. Обработка исключений

// try-catch
try {
    int result = 10 / 0;
} catch (ArithmeticException e) {
    System.out.println("Division by zero: " + e.getMessage());
} catch (Exception e) {
    System.out.println("General error: " + e.getMessage());
}

// try-catch-finally
try {
    // код
} catch (Exception e) {
    // обработка ошибки
} finally {
    // выполняется всегда
}

// throws в сигнатуре метода
public int divide(int a, int b) throws ArithmeticException {
    if (b == 0) {
        throw new ArithmeticException("Division by zero");
    }
    return a / b;
}

// Создание собственных исключений
class CustomException extends Exception {
    public CustomException(String message) {
        super(message);
    }
}---

## 7. Наследование

// Базовый класс (родительский)
public class Animal {
    protected String name;
    
    public Animal(String name) {
        this.name = name;
    }
    
    public void makeSound() {
        System.out.println("Some sound");
    }
    
    public void sleep() {
        System.out.println(name + " is sleeping");
    }
}

// Производный класс (дочерний)
public class Dog extends Animal {
    private String breed;
    
    public Dog(String name, String breed) {
        super(name);  // вызов конструктора родителя
        this.breed = breed;
    }
    
    // Переопределение метода
    @Override
    public void makeSound() {
        System.out.println("Woof!");
    }
    
    public void fetch() {
        System.out.println(name + " is fetching");
    }
}

// Использование
Dog dog = new Dog("Buddy", "Labrador");
dog.makeSound();  // "Woof!"
dog.sleep();      // "Buddy is sleeping"
dog.fetch();      // "Buddy is fetching"### Полиморфизм
Animal animal = new Dog("Buddy", "Labrador");
animal.makeSound();  // вызовется переопределенный метод Dog### Абстрактные классы
public abstract class Shape {
    protected String color;
    
    public Shape(String color) {
        this.color = color;
    }
    
    // Абстрактный метод (без реализации)
    public abstract double area();
    
    // Обычный метод
    public String getColor() {
        return color;
    }
}

public class Circle extends Shape {
    private double radius;
    
    public Circle(String color, double radius) {
        super(color);
        this.radius = radius;
    }
    
    @Override
    public double area() {
        return Math.PI * radius * radius;
    }
}---

## 8. Интерфейсы

// Интерфейс
public interface Drawable {
    void draw();  // метод по умолчанию public abstract
    
    // Метод по умолчанию (Java 8+)
    default void printInfo() {
        System.out.println("This is drawable");
    }
    
    // Статический метод (Java 8+)
    static void describe() {
        System.out.println("Drawable interface");
    }
}

// Реализация интерфейса
public class Circle implements Drawable {
    @Override
    public void draw() {
        System.out.println("Drawing a circle");
    }
}

// Интерфейс с константами
public interface Constants {
    double PI = 3.14159;  // автоматически public static final
}---

## 9. Полезные классы

### Math
Math.max(5, 10);           // 10
Math.min(5, 10);           // 5
Math.abs(-5);              // 5
Math.pow(2, 3);            // 8.0
Math.sqrt(16);             // 4.0
Math.round(3.6);           // 4
Math.ceil(3.2);            // 4.0
Math.floor(3.8);           // 3.0
Math.random();             // случайное число 0.0-1.0### Arrays
import java.util.Arrays;

int[] arr = {3, 1, 4, 1, 5};
Arrays.sort(arr);                    // сортировка
int index = Arrays.binarySearch(arr, 4);  // бинарный поиск
boolean equal = Arrays.equals(arr1, arr2);  // сравнение
String str = Arrays.toString(arr);   // "[1, 1, 3, 4, 5]"
int[] copy = Arrays.copyOf(arr, 10); // копирование### StringBuilder (эффективная работа со строками)
StringBuilder sb = new StringBuilder();
sb.append("Hello");
sb.append(" ");
sb.append("World");
String result = sb.toString();  // "Hello World"

// Методы
sb.insert(5, ",");              // вставка
sb.delete(5, 6);                // удаление
sb.reverse();                   // реверс---

## 10. Входные данные (Scanner)
ava
import java.util.Scanner;

Scanner scanner = new Scanner(System.in);

System.out.print("Enter your name: ");
String name = scanner.nextLine();

System.out.print("Enter your age: ");
int age = scanner.nextInt();

System.out.print("Enter a double: ");
double num = scanner.nextDouble();

scanner.close();  // закрыть сканнер---

## 11. Различия Java и Python - шпаргалка

| Концепция | Python | Java |
|-----------|--------|------|
| Объявление переменной | `x = 5` | `int x = 5;` |
| Типы | Динамические | Статические |
| Строки | `"text"` или `'text'` | Только `"text"` |
| Массивы | `[1, 2, 3]` (list) | `{1, 2, 3}` или `new int[]{1,2,3}` |
| Длина массива | `len(arr)` | `arr.length` (не метод!) |
| Длина строки | `len(s)` | `s.length()` (метод!) |
| Цикл по массиву | `for x in arr:` | `for (int x : arr)` |
| Функция | `def func():` | `public static void func() {}` |
| Класс | `class MyClass:` | `public class MyClass {}` |
| Наследование | `class Child(Parent):` | `class Child extends Parent {}` |
| Интерфейс | Нет (есть протоколы) | `interface MyInterface {}` |
| Сравнение строк | `s1 == s2` или `s1.equals(s2)` | `s1.equals(s2)` (НЕ `==`) |
| null vs None | `None` | `null` |
| Исключения | `try/except` | `try/catch` |
| Комментарий | `#` | `//` или `/* */` |

---

## 12. Best Practices

1. **Именование**: camelCase для переменных и методов, PascalCase для классов
2. **Модификаторы доступа**: используйте `private` для полей, предоставляйте геттеры/сеттеры
3. **Сравнение строк**: всегда используйте `.equals()`, не `==`
4. **Инициализация**: инициализируйте переменные перед использованием
5. **Закрытие ресурсов**: используйте try-with-resources для файлов, сканнеров и т.д.

---

## 13. Компиляция и запуск

# Компиляция
javac Main.java

# Запуск
java Main

# С пакетами
javac -d . Main.java
java package.name.Main**Удачи в изучении Java! 🚀**
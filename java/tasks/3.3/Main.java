// ### Задача 3.3: Класс Counter
// Создайте класс `Counter` с полем `value` типа `int`. Реализуйте:
// - Конструктор `Counter()` - инициализирует `value = 0`
// - Метод `increment()` - увеличивает `value` на 1
// - Метод `decrement()` - уменьшает `value` на 1
// - Метод `getValue()` - возвращает текущее значение

// **Пример:**
// Counter c = new Counter();
// c.increment();
// c.increment();
// c.getValue() // должно вернуть 2
// c.decrement();
// c.getValue() // должно вернуть 1

public class Main {
    public static void main(String[] args){
        Counter c = new Counter();
        c.increment();
        c.increment();
        System.out.println(c.getValue()); // должно вернуть 2
        c.decrement();
        System.out.println(c.getValue()); // должно вернуть 1
    }
}

class Counter{
    public int value;

    public Counter(){
        this.value = 0;
    }

    public void increment(){
        this.value += 1;
    }
    public void decrement(){
        this.value -= 1;
    }
    public int getValue(){
        return this.value;
    }
}


// ### Задача 3.1: Класс Point
// Создайте класс `Point` с полями `x` и `y` типа `double`. Реализуйте:
// - Конструктор `Point(double x, double y)`
// - Метод `distance(Point other)`, который вычисляет расстояние до другой точки по формуле: √((x₂-x₁)² + (y₂-y₁)²)

// **Пример:**
// Point p1 = new Point(0, 0);
// Point p2 = new Point(3, 4);
// p1.distance(p2) // должно вернуть 5.0

public class Main {
    public static void main(String[] args){
        Point p1 = new Point(0,0);
        Point p2 = new Point(3,4);
        System.out.print(p1.distance(p2));
    }
}

class Point{
    public double x;
    public double y;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double distance(Point other){
        double dx = other.x - this.x;
        double dy = other.y - this.y;
        return Math.sqrt(dx*dx + dy*dy);
    }
}

// Point p1 = new Point(0, 0);
// Point p2 = new Point(3, 4);
// p1.distance(p2)


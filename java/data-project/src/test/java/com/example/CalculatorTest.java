package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    
    private final Calculator calc = new Calculator();
    
    // === Тесты для divide() ===
    
    @Test
    void testDivide_normal() {
        assertEquals(5.0, calc.divide(10, 2));
    }
    
    @Test
    void testDivide_withRemainder() {
        assertEquals(3.5, calc.divide(7, 2));
    }
    
    @Test
    void testDivide_negativeNumbers() {
        assertEquals(-2.5, calc.divide(-5, 2));
    }
    
    @Test
    void testDivide_byZeroThrowsException() {
        // Проверяем, что выбрасывается исключение
        ArithmeticException exception = assertThrows(
            ArithmeticException.class,
            () -> calc.divide(10, 0)
        );
        
        // Проверяем сообщение исключения
        assertEquals("Division by zero", exception.getMessage());
    }
    
    @Test
    void testDivide_zeroDividedByNumber() {
        assertEquals(0.0, calc.divide(0, 5));
    }
    
    // === Тесты для add() ===
    
    @Test
    void testAdd_positiveNumbers() {
        assertEquals(7, calc.add(3, 4));
    }
    
    @Test
    void testAdd_negativeNumbers() {
        assertEquals(-1, calc.add(-3, 2));
    }
    
    // === Тесты для subtract() ===
    
    @Test
    void testSubtract_normal() {
        assertEquals(5, calc.subtract(10, 5));
    }
    
    // === Тесты для multiply() ===
    
    @Test
    void testMultiply_normal() {
        assertEquals(20, calc.multiply(4, 5));
    }
    
    @Test
    void testMultiply_byZero() {
        assertEquals(0, calc.multiply(100, 0));
    }
}

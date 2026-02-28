package com.example;

import java.io.*;
import java.util.*;

public class CsvWriter {
    
    private char delimiter = ',';
    
    public CsvWriter() {}
    
    public CsvWriter(char delimiter) {
        this.delimiter = delimiter;
    }
    
    // Запись данных в CSV файл
    public void write(String filePath, List<Map<String, String>> data) throws IOException {
        if (data == null || data.isEmpty()) {
            return;
        }
        
        // Получаем заголовки из первой строки
        Set<String> headers = data.get(0).keySet();
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Записываем заголовки
            writer.write(String.join(String.valueOf(delimiter), headers));
            writer.newLine();
            
            // Записываем данные
            for (Map<String, String> row : data) {
                List<String> values = new ArrayList<>();
                for (String header : headers) {
                    values.add(escapeValue(row.get(header)));
                }
                writer.write(String.join(String.valueOf(delimiter), values));
                writer.newLine();
            }
        }
    }
    
    // Запись с маппингом объектов
    public <T> void write(String filePath, List<T> data, 
                          java.util.function.Function<T, Map<String, String>> mapper) throws IOException {
        List<Map<String, String>> mapData = new ArrayList<>();
        for (T item : data) {
            mapData.add(mapper.apply(item));
        }
        write(filePath, mapData);
    }
    
    // Экранирование значений с кавычками
    private String escapeValue(String value) {
        if (value == null) {
            return "";
        }
        
        // Если содержит разделитель, кавычки или перенос строки - экранируем
        if (value.contains(String.valueOf(delimiter)) || 
            value.contains("\"") || 
            value.contains("\n") ||
            value.contains("\r")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        
        return value;
    }
}

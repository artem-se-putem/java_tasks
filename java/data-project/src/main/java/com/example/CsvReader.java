package com.example;

import java.io.*;
import java.util.*;

public class CsvReader {
    
    private char delimiter = ',';
    
    public CsvReader() {}
    
    public CsvReader(char delimiter) {
        this.delimiter = delimiter;
    }
    
    // Чтение CSV файла
    public List<Map<String, String>> read(String filePath) throws IOException {
        List<Map<String, String>> result = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Читаем заголовки
            String headerLine = reader.readLine();
            if (headerLine == null || headerLine.isEmpty()) {
                return result;
            }
            
            String[] headers = parseLine(headerLine);
            
            // Читаем данные
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // пропускаем пустые строки
                }
                
                String[] values = parseLine(line);
                Map<String, String> row = new LinkedHashMap<>();
                
                for (int i = 0; i < headers.length; i++) {
                    String value = (i < values.length) ? values[i].trim() : "";
                    row.put(headers[i].trim(), value);
                }
                
                result.add(row);
            }
        }
        
        return result;
    }
    
    // Чтение CSV с маппингом в объекты
    public <T> List<T> read(String filePath, LineMapper<T> mapper) throws IOException {
        List<Map<String, String>> data = read(filePath);
        List<T> result = new ArrayList<>();
        
        for (Map<String, String> row : data) {
            result.add(mapper.map(row));
        }
        
        return result;
    }
    
    // Парсинг строки с учетом кавычек
    private String[] parseLine(String line) {
        List<String> tokens = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == delimiter && !inQuotes) {
                tokens.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        
        tokens.add(current.toString());
        return tokens.toArray(new String[0]);
    }
    
    // Функциональный интерфейс для маппинга
    @FunctionalInterface
    public interface LineMapper<T> {
        T map(Map<String, String> row);
    }
}

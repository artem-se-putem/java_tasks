package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

class CsvReaderTest {
    
    @Test
    void testRead_basic() throws IOException {
        CsvReader reader = new CsvReader();
        String filePath = "src/test/resources/test.csv";
        
        List<Map<String, String>> result = reader.read(filePath);
        
        assertEquals(3, result.size());
        
        // Первая строка
        assertEquals("1", result.get(0).get("id"));
        assertEquals("John", result.get(0).get("name"));
        assertEquals("john@test.com", result.get(0).get("email"));
        assertEquals("25", result.get(0).get("age"));
        
        // Вторая строка
        assertEquals("Jane", result.get(1).get("name"));
    }
    
    @Test
    void testRead_withMapper() throws IOException {
        CsvReader reader = new CsvReader();
        String filePath = "src/test/resources/test.csv";
        
        List<User> users = reader.read(filePath, row -> {
            User user = new User();
            user.setId(Integer.parseInt(row.get("id")));
            user.setName(row.get("name"));
            user.setEmail(row.get("email"));
            return user;
        });
        
        assertEquals(3, users.size());
        assertEquals("John", users.get(0).getName());
        assertEquals("jane@test.com", users.get(1).getEmail());
    }
    
    @Test
    void testRead_customDelimiter() throws IOException {
        // Создаем CSV с точкой с запятой
        String csv = "id;name;age\n1;John;25\n2;Jane;30";
        Path tempFile = Files.createTempFile("test", ".csv");
        Files.writeString(tempFile, csv);
        
        try {
            CsvReader reader = new CsvReader(';');
            List<Map<String, String>> result = reader.read(tempFile.toString());
            
            assertEquals(2, result.size());
            assertEquals("John", result.get(0).get("name"));
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }
    
    @Test
    void testRead_emptyFile() throws IOException {
        Path tempFile = Files.createTempFile("empty", ".csv");
        Files.writeString(tempFile, "");
        
        try {
            CsvReader reader = new CsvReader();
            List<Map<String, String>> result = reader.read(tempFile.toString());
            
            assertTrue(result.isEmpty());
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }
    
    @Test
    void testRead_withEmptyLines() throws IOException {
        String csv = "id,name\n1,John\n\n2,Jane\n";
        Path tempFile = Files.createTempFile("test", ".csv");
        Files.writeString(tempFile, csv);
        
        try {
            CsvReader reader = new CsvReader();
            List<Map<String, String>> result = reader.read(tempFile.toString());
            
            assertEquals(2, result.size());
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }
}

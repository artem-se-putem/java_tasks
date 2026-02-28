package com.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.util.List;

class UserDAOTest {
    
    private static Connection connection;
    private static UserDAO userDAO;
    
    @BeforeAll
    static void setup() throws Exception {
        // Создаем подключение и таблицу перед тестами
        connection = DatabaseUtil.getConnection();
        DatabaseUtil.createUsersTable();
        userDAO = new UserDAO(connection);
    }
    
    @AfterAll
    static void cleanup() throws Exception {
        // Удаляем таблицу после всех тестов
        DatabaseUtil.dropUsersTable();
        connection.close();
    }
    
    @BeforeEach
    void beforeEach() throws Exception {
        // Очищаем таблицу перед каждым тестом
        try (var stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM users");
        }
    }
    
    // === Тест insert() ===
    
    @Test
    void testInsert() throws Exception {
        User user = new User("John", "john@test.com");
        int id = userDAO.insert(user);
        
        assertTrue(id > 0);
        
        User saved = userDAO.findById(id);
        assertNotNull(saved);
        assertEquals("John", saved.getName());
        assertEquals("john@test.com", saved.getEmail());
    }
    
    @Test
    void testInsertMultiple() throws Exception {
        userDAO.insert(new User("John", "john@test.com"));
        userDAO.insert(new User("Jane", "jane@test.com"));
        
        List<User> users = userDAO.findAll();
        assertEquals(2, users.size());
    }
    
    // === Тест findById() ===
    
    @Test
    void testFindById_exists() throws Exception {
        int id = userDAO.insert(new User("John", "john@test.com"));
        
        User user = userDAO.findById(id);
        
        assertNotNull(user);
        assertEquals("John", user.getName());
    }
    
    @Test
    void testFindById_notExists() throws Exception {
        User user = userDAO.findById(999);
        assertNull(user);
    }
    
    // === Тест findAll() ===
    
    @Test
    void testFindAll() throws Exception {
        userDAO.insert(new User("John", "john@test.com"));
        userDAO.insert(new User("Jane", "jane@test.com"));
        userDAO.insert(new User("Bob", "bob@test.com"));
        
        List<User> users = userDAO.findAll();
        
        assertEquals(3, users.size());
    }
    
    @Test
    void testFindAll_empty() throws Exception {
        List<User> users = userDAO.findAll();
        assertTrue(users.isEmpty());
    }
    
    // === Тест update() ===
    
    @Test
    void testUpdate() throws Exception {
        int id = userDAO.insert(new User("John", "john@test.com"));
        
        User user = userDAO.findById(id);
        user.setName("John Updated");
        user.setEmail("updated@test.com");
        
        boolean result = userDAO.update(user);
        
        assertTrue(result);
        
        User updated = userDAO.findById(id);
        assertEquals("John Updated", updated.getName());
        assertEquals("updated@test.com", updated.getEmail());
    }
    
    @Test
    void testUpdate_notExists() throws Exception {
        User user = new User(999, "John", "john@test.com");
        
        boolean result = userDAO.update(user);
        
        assertFalse(result);
    }
    
    // === Тест delete() ===
    
    @Test
    void testDelete() throws Exception {
        int id = userDAO.insert(new User("John", "john@test.com"));
        
        boolean result = userDAO.delete(id);
        
        assertTrue(result);
        assertNull(userDAO.findById(id));
    }
    
    @Test
    void testDelete_notExists() throws Exception {
        boolean result = userDAO.delete(999);
        assertFalse(result);
    }
}

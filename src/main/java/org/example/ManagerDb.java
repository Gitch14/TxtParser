package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ManagerDb {

    private final Connection connection;

    private static ManagerDb instance;

    // Патерн проектирования Singleton
    public ManagerDb() {
        // относительный путь к проперти где сохранены настройки подключение к базе данных
        try (BufferedReader reader = Files.newBufferedReader(Path.of("src/main/resources/settings.properties"))) {
            Properties properties = new Properties();
            properties.load(reader);
            connection = DriverManager.getConnection(properties.getProperty("url"), properties);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static ManagerDb getInstance() {
        if (instance == null) {
            instance = new ManagerDb();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
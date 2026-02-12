package org.educa.settings;

import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Getter
public class DatabaseSettings {

    private DatabaseSettings() {
        throw new IllegalStateException();
    }

    private static final Properties PROPERTIES = loadProperties();

    private static Properties loadProperties() {

        Properties properties = new Properties();
        try (InputStream input = DatabaseSettings.class.getClassLoader()
                .getResourceAsStream("database.properties")) {
            properties.load(input);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    /**
     * Get URL
     *
     * @return URL from database
     */
    public static String getURL() {
        return PROPERTIES.getProperty("db.url");
    }

    /**
     * Get Database
     *
     * @return the Database name
     */
    public static String getDB() {
        return PROPERTIES.getProperty("db.name");
    }
}

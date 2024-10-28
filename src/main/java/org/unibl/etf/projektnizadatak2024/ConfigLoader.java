package org.unibl.etf.projektnizadatak2024;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    private static Properties properties;

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("rentals.properties")) {
            properties = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find rental.properties");
                return;
            }
            properties.load(input);
        } catch (IOException e) {
            System.out.println("Sorry, unable to load rental.properties");
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}

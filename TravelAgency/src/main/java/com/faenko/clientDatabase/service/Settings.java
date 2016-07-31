package com.faenko.clientDatabase.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Реализован с помощью шаблона проектирования "Singleton"
 * Класс-синглтон для считывания настроек подключения к базе данных
 */
public class Settings {

    private static final Settings INSTANCE = new Settings();

    private final Properties properties = new Properties();

    /**
     * В конструкторе задается properties-файл настроек
     */
    private Settings() {
        try {
            /** Считывание файла */
            properties.load(new FileInputStream(this.getClass().getClassLoader().getResource("db.properties").getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Получить экземпляр класса
     * @return Экземпляр
     */
    public static Settings getInstance() {
        return INSTANCE;
    }

    /**
     * Получить значение по ключу из properties-файла настроек
     * @param key Ключ
     * @return Значение
     */
    public String value(String key) {
        return this.properties.getProperty(key);
    }


}

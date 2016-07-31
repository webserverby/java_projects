package com.faenko.clientDatabase.service;

/**
 * Утилита для получения имени класса
 * e.getStackTrace()[0] - Иия текущего класса
 * e.getStackTrace()[1] - Имя класса, какой вызвал эту утилиту
 *
 */
public class ClassName {
    public static String getCurrentClassName() {
        try {
            throw new RuntimeException();
        } catch (RuntimeException e) {
            return e.getStackTrace()[1].getClassName();
        }
    }

}

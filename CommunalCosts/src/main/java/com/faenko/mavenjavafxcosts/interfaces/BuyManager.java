package com.faenko.mavenjavafxcosts.interfaces;

import com.faenko.mavenjavafxcosts.object.Record;

/**
 * Интерфейс методов для манипуляиции с объектами Record из коллекции
 *
 * @author Artem Faenko
 */
public interface BuyManager {

    // добавить запись
    void addCollection(Record record, String a);

    // внести измененные значения (подтвердить измененные данные)
    void updateCollection(Record record, String a);

    // удалить запись
    void deleteCollection(Record record, String a);

}

package com.faenko.clientDatabase.store;

import com.faenko.clientDatabase.models.Tour;

import java.util.Collection;

/**
 * Интерфейс, который должен реализовывать список туров
 *
 * @author Artem Faenko
 */
public interface StorageTour {
    /**
     * Получить список туров
     * @return Список туров
     */
    Collection<Tour> values();

    /**
     * Найти туры
     * @return Список найденных туров
     */
    Collection<Tour> valuesFound();

    /**
     * Добавить тур
     * @param tour Тур
     */
    void add(final Tour tour);

    /**
     * Редактирование тура
     * @param tour Тур
     */
    void edit(final Tour tour);

    /**
     * Удаление тура
     * @param id
     */
    void delete(final int id);

    void foldCounter();

    Tour get(final int id);

    void find(final String idTour, final String tourNameTour, final String routeCityCome, final String routeCountryCome);

    int generateId();

    /**
     * Освобождение ресурсов перед закрытием
     */
    void close();
}

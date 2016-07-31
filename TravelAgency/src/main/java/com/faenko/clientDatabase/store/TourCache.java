package com.faenko.clientDatabase.store;

import com.faenko.clientDatabase.models.Tour;

import java.util.Collection;

/**
 * Шаблон проектирования "Singleton".
 * Создание объекта этого класса, происходит только в этом классе
 * Создать можно только один объект этого класса
 * TourJdbc - список туров на основе базы данных
 */
public class TourCache implements StorageTour {
    /** Инициализация единственного экземпляра */
    private static final TourCache INSTOUR = new TourCache();
    /**
     * Выбор реализации тура
     */
    private StorageTour storageTour = new TourJdbc();

    /**
     * Получаем экземпляр(объект) класса
     * @return Экземпляр
     */
    public static TourCache getInstance() {
        return INSTOUR;
    }

    /**
     * Найти туры
     * @return Список найденных туров
     */
    @Override
    public Collection<Tour> valuesFound() {
        return this.storageTour.valuesFound();
    }

    /**
     * Получить список туров
     * @return Список туров
     */
    @Override
    public Collection<Tour> values() {
        return this.storageTour.values();
    }

    /**
     * Добавить тур
     * @param tour Тур
     */
    @Override
    public void add(final Tour tour) {
        this.storageTour.add(tour);
    }

    /**
     * Редактирование тура
     * @param tour Тур
     */
    @Override
    public void edit(final Tour tour) {
        this.storageTour.edit(tour);
    }

    @Override
    public void find(final String idTour, final String tourNameTour, final String routeCityCome, final String routeCountryCome) {
        this.storageTour.find(idTour, tourNameTour, routeCityCome, routeCountryCome);
    }

    @Override
    public int generateId() {
        return this.storageTour.generateId();
    }

    /**
     * Удаление тура по id
     * @param id
     */
    @Override
    public void delete(final int id) {
        this.storageTour.delete(id);
    }

    @Override
    public void foldCounter() {
        this.storageTour.foldCounter();
    }

    @Override
    public Tour get(final int id) {
        return this.storageTour.get(id);
    }

    /**
     * Освобождение ресурсов перед закрытием
     */
    @Override
    public void close() {
        storageTour.close();
    }

}

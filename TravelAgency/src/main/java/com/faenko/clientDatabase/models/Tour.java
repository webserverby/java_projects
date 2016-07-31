package com.faenko.clientDatabase.models;

import javax.persistence.*;

/**
 * Класс реализующий тур
 *
 * @author Artem Faenko
 */
@Entity
@Table(name = "tour")
public class Tour extends Base {
    /** Название тура */
    @Column(name = "nameTour")
    private String nameTour;

    /** Дата начала */
    @Column(name = "dateBegin")
    private String dateBegin;

    /** Дата окончания */
    @Column(name = "dateEnd")
    private String dateEnd;

    /** Кол-во дней */
    @Column(name = "dayNumber")
    private String dayNumber;

    /** Кол-во человек */
    @Column(name = "personNumber")
    private String personNumber;

    /** Тур оператор */
    @Column(name = "tourOperator")
    private String tourOperator;

    /** Отель */
    @Column(name = "hotel")
    private String hotel;

    /** Тип номера */
    @Column(name = "typeNumber")
    private String typeNumber;

    /** Питание */
    @Column(name = "food")
    private String food;

    /** Стоимость тура */
    @Column(name = "tourСost")
    private String tourСost;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "route_id")

    /** Экземпляр тура */
    private Route route;

    /**
     * Пустой конструктор для Hibernate
     * Он изначально создает нулевой объект, а после уже начинает дергать геттеры и сеттеры
     */
    public Tour() {
    }

    /**
     * Конструктор
     * @param id ID тура
     * @param nameTour Название тура
     * @param dateBegin Дата начала
     * @param dateEnd Дата окончания
     * @param dayNumber Кол-во дней
     * @param personNumber Кол-во человек
     * @param tourOperator Тур оператор
     * @param hotel Отель
     * @param typeNumber Тип номера
     * @param food Питание
     * @param tourСost Стоимость тура
     * @param route Экземпляр тура
     */
    public Tour(final int id, final String nameTour,final String dateBegin, final String dateEnd, final String dayNumber,
                final String personNumber, final String tourOperator, final String hotel, final String typeNumber,
                final String food, final String tourСost, final Route route) {
        this.id = id;
        this.nameTour = nameTour;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.dayNumber = dayNumber;
        this.personNumber = personNumber;
        this.tourOperator = tourOperator;
        this.hotel = hotel;
        this.typeNumber = typeNumber;
        this.food = food;
        this.tourСost = tourСost;
        this.route = route;
    }

    /**
     * Получить название тура
     * @return Название тура
     */
    public String getNameTour() {
        return this.nameTour;
    }

    /**
     * Получить дату начала
     * @return Дата начала
     */
    public String getDateBegin() {
        return this.dateBegin;
    }

    /**
     * Получить дату окончания
     * @return Дата окончания
     */
    public String getDateEnd() {
        return this.dateEnd;
    }

    /**
     * Получить кол-во дней
     * @return Кол-во дней
     */
    public String getDayNumber() {
        return this.dayNumber;
    }

    /**
     * Получить кол-во человек
     * @return Кол-во человек
     */
    public String getPersonNumber() {
        return this.personNumber;
    }

    /**
     * Получить тур оператор
     * @return Тур оператор
     */
    public String getTourOperator() {
        return this.tourOperator;
    }

    /**
     * Получить Отель
     * @return Отель
     */
    public String getHotel() {
        return this.hotel;
    }

    /**
     * Получить тип номера
     * @return Тип номера
     */
    public String getTypeNumber() {
        return this.typeNumber;
    }

    /**
     * Получить питание
     * @return Питание
     */
    public String getFood() {
        return this.food;
    }

    /**
     * Получить стоимость тура
     * @return Стоимость тура
     */
    public String getTourСost() {
        return this.tourСost;
    }

    /**
     * Получить маршрут тура
     * @return Маршрут
     */
    public Route getRoute() {
        return this.route;
    }

    /**
     * Установить название тура
     * @param nameTour Название тура
     */
    public void setNameTour(String nameTour) {
        this.nameTour = nameTour;
    }

    /**
     * Установить дату начала
     * @param dateBegin Дата начала
     */
    public void setDateBegin(String dateBegin) {
        this.dateBegin = dateBegin;
    }

    /**
     * Установить дату окончания
     * @param dateEnd Дата окончания
     */
    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    /**
     * Установить кол-во дней
     * @param dayNumber Кол-во дней
     */
    public void setDayNumber(String dayNumber) {
        this.dayNumber = dayNumber;
    }

    /**
     * Установить кол-во человек
     * @param personNumber Кол-во человек
     */
    public void setPersonNumber(String personNumber) {
        this.personNumber = personNumber;
    }

    /**
     * Установить тур оператор
     * @param tourOperator Тур оператор
     */
    public void setTourOperator(String tourOperator) {
        this.tourOperator = tourOperator;
    }

    /**
     * Установить отель
     * @param hotel Отель
     */
    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    /**
     * Установить тип номера
     * @param typeNumber Тип номера
     */
    public void setTypeNumber(String typeNumber) {
        this.typeNumber = typeNumber;
    }

    /**
     * Установить питание
     * @param food Питание
     */
    public void setFood(String food) {
        this.food = food;
    }

    /**
     * Установить стоимость тура
     * @param tourСost Стоимость тура
     */
    public void setTourСost(String tourСost) {
        this.tourСost = tourСost;
    }

    /**
     * Установить маршрут тура
     * @param route Маршрут
     */
    public void setRoute(Route route) {
        this.route = route;
    }
}

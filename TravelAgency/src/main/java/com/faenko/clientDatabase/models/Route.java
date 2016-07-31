package com.faenko.clientDatabase.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Класс реализующий данные о маршруте тура и датах отправления и прибытия
 *
 * @author Artem Faenko
 */
@Entity
@Table(name = "route")
public class Route extends Base {
    /**
     * Вид транспорта
     */
    @Column(name = "transportName")
    private String transportName;
    /**
     * Город отправления
     */
    @Column(name = "cityDeparture")
    private String cityDeparture;
    /**
     * Город прибытия
     */
    @Column(name = "cityCome")
    private String cityCome;
    /**
     * Страна прибытия
     */
    @Column(name = "countryCome")
    private String countryCome;
    /**
     * Дата отправления
     */
    @Column(name = "dateDeparture")
    private String dateDeparture;
    /**
     * Дата прибытия
     */
    @Column(name = "dateCome")
    private String dateCome;
    /**
     * Дата возврата
     */
    @Column(name = "dateReturn")
    private String dateReturn;

    /**
     * Конструктор
     * @param id ID маршрута
     * @param transportName Вид транспорта
     * @param cityDeparture Город отправления
     * @param cityCome Город прибытия
     * @param countryCome Страна прибытия
     * @param dateDeparture Дата отправления
     * @param dateCome Дата прибытия
     * @param dateReturn Дата возврата
     */
    public Route(final int id, final String transportName, final String cityDeparture, final String cityCome,
                 final String countryCome, final String dateDeparture, final String dateCome, final String dateReturn) {
        this.id = id;
        this.transportName = transportName;
        this.cityDeparture = cityDeparture;
        this.cityCome = cityCome;
        this.countryCome = countryCome;
        this.dateDeparture = dateDeparture;
        this.dateCome = dateCome;
        this.dateReturn = dateReturn;
    }

    /**
     * Получить вид транспорта
     * @return Вид транспорта
     */
    public String getTransportName() {
        return this.transportName;
    }

    /**
     * Получить город отправления
     * @return Город отправления
     */
    public String getCityDeparture() {
        return this.cityDeparture;
    }

    /**
     * Получить город прибытия
     * @return Город прибытия
     */
    public String getCityCome() {
        return cityCome;
    }

    /**
     * Получить страну прибытия
     * @return Страну прибытия
     */
    public String getCountryCome() {
        return this.countryCome;
    }

    /**
     * Получить дату отправления
     * @return Дата отправления
     */
    public String getDateDeparture() {
        return this.dateDeparture;
    }

    /**
     * Получить дату прибытия
     * @return Дата прибытия
     */
    public String getDateCome() {
        return this.dateCome;
    }

    /**
     * Получить дату возврата
     * @return Дата возврата
     */
    public String getDateReturn() {
        return this.dateReturn;
    }

    /**
     * Установить вид транспорта
     * @param transportName Вид транспорта
     */
    public void setTransportName(String transportName) {
        this.transportName = transportName;
    }

    /**
     * Установить город отправления
     * @param cityDeparture Город отправления
     */
    public void setCityDeparture(String cityDeparture) {
        this.cityDeparture = cityDeparture;
    }

    /**
     * Установить город прибытия
     * @param cityCome Город прибытия
     */
    public void setCityCome(String cityCome) {
        this.cityCome = cityCome;
    }

    /**
     * Установить страну прибытия
     * @param countryCome Страна прибытия
     */
    public void setCountryCome(String countryCome) {
        this.countryCome = countryCome;
    }

    /**
     * Установить дату отправления
     * @param dateDeparture Дата отправления
     */
    public void setDateDeparture(String dateDeparture) {
        this.dateDeparture = dateDeparture;
    }

    /**
     * Установить дату прибытия
     * @param dateCome Дата прибытия
     */
    public void setDateCome(String dateCome) {
        this.dateCome = dateCome;
    }

    /**
     * Установить дату возврата
     * @param dateReturn Дата возврата
     */
    public void setDateReturn(String dateReturn) {
        this.dateReturn = dateReturn;
    }

    @OneToOne(mappedBy = "route")
    private Tour tour;

    /**
     * Получить экземепляр тура
     * @return Тур
     */
    public Tour getTour() {
        return tour;
    }

    /**
     * Установить экземепляр тура
     * @param tour Тур
     */
    public void setTour(Tour tour) {
        this.tour = tour;
    }


}

package com.faenko.clientDatabase.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Класс реализующий паспортные данные
 *
 * @author Artem Faenko
 */
@Entity
@Table(name = "passport")
public class Passport extends Base {
    /**
     * Серия паспорта
     */
    @Column(name = "series")
    private String series;
    /**
     * Номер паспорта
     */
    @Column(name = "number")
    private String number;
    /**
     * Кем выдан паспорт
     */
    @Column(name = "received")
    private String received;
    /**
     * Дата выдачи паспорта
     */
    @Column(name = "issueDate")
    private String issueDate;
    /**
     * Срок действия паспорта
     */
    @Column(name = "expiryDate")
    private String expiryDate;

    /**
     * Конструктор
     * @param id ID паспорта
     * @param series Серия
     * @param number Номер
     * @param received Кем выдан
     * @param issueDate Дата выдачи
     * @param expiryDate Срок действия
     */
    public Passport(final int id, final String series, final String number, final String received, final String issueDate, final String expiryDate) {
        this.id = id;
        this.series = series;
        this.number = number;
        this.received = received;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
    }

    public Passport() {
    }

    /**
     * Получить серию паспорта
     * @return Серия паспорта
     */
    public String getSeries() {
        return this.series;
    }

    /**
     * Получить номер паспорта
     * @return Номер паспорта
     */
    public String getNumber() {
        return this.number;
    }

    /**
     * Получить кем выдан паспорт
     * @return Кем выдан паспорт
     */
    public String getReceived() {
        return received;
    }

    /**
     * Получить дату выдачи
     * @return Дата выдачи
     */
    public String getIssueDate() {
        return this.issueDate;
    }

    /**
     * Получить срок действия
     * @return Срок действия
     */
    public String getExpiryDate() {
        return this.expiryDate;
    }

    /**
     * Установить серию паспорта
     * @param series Серия паспорта
     */
    public void setSeries(String series) {
        this.series = series;
    }

    /**
     * Установить номер паспорта
     * @param number Номер паспорта
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Установить кем выдан паспорт
     * @param received Кем выдан паспорт
     */
    public void setReceived(String received) {
        this.received = received;
    }

    /**
     * Установить дату выдачи
     * @param issueDate Дата выдачи
     */
    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    /**
     * Установить срок действия
     * @param expiryDate Срок действия
     */
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }


    @OneToOne(mappedBy = "passport")
    private Client client;

    /**
     * Получить клиента
     * @return Клиент
     */
    public Client getClient() {
        return client;
    }

    /**
     * Установить клиента
     * @param client Клиент
     */
    public void setClient(Client client) {
        this.client = client;
    }


}

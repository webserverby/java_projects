package com.faenko.clientDatabase.models;

import javax.persistence.*;

/**
 * Класс реализующий клиента
 *
 * @author Artem Faenko
 */
@Entity
@Table(name = "client")
public class Client extends Base{

    /** Фамилия клиента */
    @Column(name = "surname")
    private String surname;

    /** Имя клиента */
    @Column(name = "name")
    private String name;

    /** Отчество клиента */
    @Column(name = "patronymic")
    private String patronymic;

    /** Телефон мобильный */
    @Column(name = "phoneMobile")
    private String phoneMobile;

    /** Телефон домашний */
    @Column(name = "phoneHome")
    private String phoneHome;

    /** Адрес */
    @Column(name = "address")
    private String address;

    /** Дата рождения */
    @Column(name = "birthDate")
    private String birthDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passport_id")

    /** Экземпляр паспорта */
    private Passport passport;

    /**
     * Пустой конструктор для Hibernate
     * Он изначально создает нулевой объект, а после уже начинает дергать геттеры и сеттеры
     */
    public Client() {
    }

    /**
     * Конструктор
     * @param id ID клиента
     * @param surname Фамилия
     * @param name имя
     * @param patronymic Отчество
     * @param phoneMobile Телефон мобильный
     * @param phoneHome Телефон домашний
     * @param address Адрес
     * @param birthDate Дата рождения
     * @param passport экземпляр паспорта
     */
    public Client(final int id, final String surname,final String name, final String patronymic, final String phoneMobile,final String phoneHome, final String address, final String birthDate, final Passport passport) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.phoneMobile = phoneMobile;
        this.phoneHome = phoneHome;
        this.address = address;
        this.birthDate = birthDate;
        this.passport = passport;
    }

    /**
     * Получить фамилию клиента
     * @return Фамилия клиента
     */
    public String getSurname() {
        return this.surname;
    }

    /**
     * Получить имя клиента
     * @return Имя клиента
     */
    public String getName() {
        return this.name;
    }

    /**
     * Получить отчество клиента
     * @return Отчество клиента
     */
    public String getPatronymic() {
        return this.patronymic;
    }

    /**
     * Получить мобильный телефон клиента
     * @return Мобильный телефон клиента
     */
    public String getPhoneMobile() {
        return this.phoneMobile;
    }

    /**
     * Получить домашний телефон клиента
     * @return Домашний телефон клиента
     */
    public String getPhoneHome() {
        return this.phoneHome;
    }

    /**
     * Получить адрес клиента
     * @return Адрес клиента
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * Получить дату рождения клиента
     * @return Дата рождения клиента
     */
    public String getBirthDate() {
        return this.birthDate;
    }

    /**
     * Получить паспорт клиента
     * @return Паспорт
     */
    public Passport getPassport() {
        return this.passport;
    }

    /**
     * Установить фамилию клиента
     * @param surname Фамилия
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Установить Имя клиента
     * @param name Имя
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Установить отчество клиента
     * @param patronymic Отчество
     */
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    /**
     * Установить телефон мобильный клиента
     * @param phoneMobile Телефон мобильный
     */
    public void setPhoneMobile(String phoneMobile) {
        this.phoneMobile = phoneMobile;
    }

    /**
     * Установить телефон домашний
     * @param phoneHome Телефон домашний
     */
    public void setPhoneHome(String phoneHome) {
        this.phoneHome = phoneHome;
    }

    /**
     * Установить Адрес клиента
     * @param address Адрес
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Установить дату рождения клиента
     * @param birthDate Дата рождения
     */
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Установить паспорт клиента
     * @param passport Паспорт
     */
    public void setPassport(Passport passport) {
        this.passport = passport;
    }

}

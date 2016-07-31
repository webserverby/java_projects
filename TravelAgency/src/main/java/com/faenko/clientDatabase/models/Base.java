package com.faenko.clientDatabase.models;

import javax.persistence.*;

/**
 * Абстрактный базовый класс определяющий ID
 *
 * @author Artem Faenko
 */
@MappedSuperclass
public abstract class Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    protected int id;

    public Base() {
    }

    /**
     * Конструктор
     * @param id ID
     */
    public Base(int id) {
        this.id = id;
    }

    /**
     * Получить ID
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Установить ID
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

}

package com.faenko.clientDatabase.store;

import com.faenko.clientDatabase.models.Client;

import java.util.Collection;

/**
 * Шаблон проектирования "Singleton".
 * Создание объекта этого класса, происходит только в этом классе
 * Создать можно только один объект этого класса
 * ClientJdbc - список клиентов на основе базы данных
 */
public class ClientCache implements StorageClient{
    /** Инициализация единственного экземпляра */
    private static final ClientCache INSCLIENT = new ClientCache();
    /**
     * Выбор реализации клиента
     */
    private StorageClient storage = new ClientJdbc();

    /**
     * Получаем экземпляр(объект) класса
     * @return Экземпляр
     */
    public static ClientCache getInstance() {
        return INSCLIENT;
    }

    /**
     * Найти клиентов
     * @return Список найденных клиентов
     */
    @Override
    public Collection<Client> valuesFound() {
        return this.storage.valuesFound();
    }

    /**
     * Получить список клиентов
     * @return Список клиентов
     */
    @Override
    public Collection<Client> values() {
        return this.storage.values();
    }

    /**
     * Добавить клиента
     * @param client Клиент
     */
    @Override
    public void add(final Client client) {
        this.storage.add(client);
    }

    /**
     * Редактирование клиента
     * @param client Клиент
     */
    @Override
    public void edit(final Client client) {
        this.storage.edit(client);
    }

    @Override
    public void find(final String idClient, final String clientSurname, final String clientName, final String passportNumber) {
        this.storage.find(idClient, clientSurname, clientName, passportNumber);
    }

    @Override
    public int generateId() {
        return this.storage.generateId();
    }

    /**
     * Удаление клиента по id
     * @param id
     */
    @Override
    public void delete(final int id) {
        this.storage.delete(id);
    }

    @Override
    public void foldCounter() {
        this.storage.foldCounter();
    }

    @Override
    public Client get(final int id) {
        return this.storage.get(id);
    }

    /**
     * Освобождение ресурсов перед закрытием
     */
    @Override
    public void close() {
        storage.close();
    }

}

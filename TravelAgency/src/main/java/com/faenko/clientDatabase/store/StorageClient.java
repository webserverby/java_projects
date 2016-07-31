package com.faenko.clientDatabase.store;

import com.faenko.clientDatabase.models.Client;

import java.util.Collection;

/**
 * Интерфейс, который должен реализовывать список клиентов
 *
 * @author Artem Faenko
 */
public interface StorageClient {
    /**
     * Получить список клиентов
     * @return Список клиентов
     */
    Collection<Client> values();

    /**
     * Найти клиентов
     * @return Список найденных клиентов
     */
    Collection<Client> valuesFound();

    /**
     * Добавить клиента
     * @param client Клиент
     */
    void add(final Client client);

    /**
     * Редактирование клиента
     * @param client Клиент
     */
    void edit(final Client client);

    /**
     * Удаление клиента
     * @param id
     */
    void delete(final int id);

    void foldCounter();

    Client get(final int id);

    void find(final String idClient, final String clientSurname, final String clientName, final String passportNumber);

    int generateId();

    /**
     * Освобождение ресурсов перед закрытием
     */
    void close();
}
